package com.kaiwait.service.jczh.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.CostPDF;
import com.kaiwait.bean.jczh.entity.JobInfo;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.PayDeal;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.entity.TitleMsg;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.bean.jczh.vo.CostListVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.core.utils.PdfToZipUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
import com.kaiwait.mappers.jczh.CostListMapper;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.mappers.jczh.PayMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.CostService;
import com.kaiwait.utils.common.StringUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@Service
public class CostServiceImpl implements CostService {
	@Resource
	private CostMapper costMapper;
	@Resource
	private JobLandMapper joblandmapper;
	@Resource
	private JobMapper jobmapper;
	@Resource
	private CostListMapper costListMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private PayMapper payMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private CommonmstMapper commonmstdMapper;
	@Resource
	private CommonMethedService commonMethedService;
	
	
	public Map<String,Object> insertCostOutSoureTx(CostInput inputParam) {
		//状态验证.如果job状态为原件完了,则外发不可以插入
		HashMap<String, Object> output= new HashMap<>();
		JobListInput jobInput = new JobListInput();
		jobInput.setJob_cd(inputParam.getJob_cd());
		jobInput.setCompanyID(inputParam.getCompanyID());
		//根据job编号查询是否存在此条job
		List<JobList> joblist=jobmapper.selectjobStatus(jobInput);
		/*int orderInvoiceFlg = commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID());
		if(orderInvoiceFlg == 1 && StringUtil.isEmpty(inputParam.getInvoice_no()))
		{
			output.put("messge", "VALIDATE_FORMAT_ERROR");
			output.put("num", 0);
			return  output;
		}*/
		//没有job不允许登录支付信息
		if(joblist.size()<1) {
			output.put("messge", "STATUS_VALIDATEPOWER_ERROR");
			output.put("num", 0);
			return  output;
		}else {
			JobInfo va =jobmapper.searchJobInfoByNo(inputParam.getJob_cd(), inputParam.getCompanyID());
			int costfinshiflg=va.getCostFinshFlg();
			if(costfinshiflg==1) {
				output.put("messge", "ORDER_CREATE_ERROR");
				output.put("num", 0);
				return output;
			}else {
				output.put("messge", "SYS_VALIDATEPOWER_OK");
			}
		}
		//严重发注先是否有效
		int payeedelflg =commonMethedMapper.validateClientFlg(inputParam.getPayee_cd(),Integer.valueOf(inputParam.getCompanyID()));
		if(payeedelflg==0) {
			output.put("messge", "CLIENT_DELETE_ERR");
			output.put("num", 0);
			return output;
		}
		//验证发驻日与预定纳品日规则
		if(inputParam.getOrder_date().compareTo(inputParam.getPlan_dlvday())>0){
			output.put("messge", "PLAN_DATEMORE");
			output.put("num", 0);
			return output;
		}
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		inputParam.setUpdate(dateformat.format(date));
		inputParam.setUpusercd(inputParam.getUserID());
		inputParam.setAdddate(dateformat.format(date));
		inputParam.setAddusercd(inputParam.getUserID());
		List<JobLableTrn> jltrn = inputParam.getLableList();
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		    }else {
		for(int i=0;i<jltrn.size();i++) {
			jltrn.get(i).setCostno(inputParam.getCost_no());
			jltrn.get(i).setLablelevel(0);//外发，支付。
			jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
			jltrn.get(i).setUpdDate(dateformat.format(date));
			jltrn.get(i).setUpUsercd(inputParam.getUserID());
			jltrn.get(i).setAddDate(dateformat.format(date));
			jltrn.get(i).setAddUsercd(inputParam.getUserID());
		}
		costMapper.insertCostLable(jltrn);
		    }
		//插入外发
		inputParam.setCompany_cd(inputParam.getCompanyID());
		inputParam.setDel_flg("0");
		costMapper.insertCostOutSoureTx(inputParam);
		//插入发票数据
		int a = costMapper.insertInvoiceintrnTx(inputParam);
		output.put("num", a);
		output.put("jobcd", inputParam.getJob_cd());
		output.put("costno", inputParam.getCost_no());
		return output;
	}
	public Map<String, Object>  updateCostOutSoureTx(CostInput inputParam){
		
		//原件没登录不可以更新
		HashMap<String, Object> output= new HashMap<>();
		List<Cost> cost= costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID()); 
		
//		int orderInvoiceFlg = commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID());
//		if(orderInvoiceFlg == 1 && StringUtil.isEmpty(inputParam.getInvoice_no()))
//		{
//			output.put("messge", "VALIDATE_FORMAT_ERROR");
//			output.put("num", 0);
//			return  output;
//		}
		
		if(cost.size()>0) {
				//支付情报登录，则不可更新外发
				if(cost.get(0).getInput_no()==null||"".equals(cost.get(0).getInput_no())) {
					//更新锁
					if(cost.get(0).getLockflg()==inputParam.getLockflg()) {
						output.put("messge", "SYS_VALIDATEPOWER_OK");	
					}else {
						output.put("messge", "SYS_VALIDATEPOWER_ERROR");
						output.put("num", 0);
						return  output;
					}
				}else {
					output.put("messge", "SYS_VALIDATEPOWER_ERROR");
					output.put("num", 0);
					return  output;
				}
		}else {
			output.put("messge", "SYS_VALIDATEPOWER_ERROR");
			output.put("num", 0);
			return output;
		}
		//严重发注先是否有效
		int payeedelflg =commonMethedMapper.validateClientFlg(inputParam.getPayee_cd(),Integer.valueOf(inputParam.getCompanyID()));
		if(payeedelflg==0) {
			output.put("messge", "CLIENT_DELETE_ERR");
			output.put("num", 0);
			return output;
		}
		//验证发驻日与预定纳品日规则
		if(inputParam.getOrder_date().compareTo(inputParam.getPlan_dlvday())>0){
			output.put("messge", "PLAN_DATEMORE");
			output.put("num", 0);
			return output;
		}
		//更新时间
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		inputParam.setUpdate(dateformat.format(date));
		inputParam.setUpusercd(inputParam.getUserID());
		
		List<JobLableTrn> jltrn = inputParam.getLableList();
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		    }else {
		for(int i=0;i<jltrn.size();i++) {
			jltrn.get(i).setCostno(inputParam.getCost_no());
			jltrn.get(i).setLablelevel(0);//外发，支付。
			jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
			jltrn.get(i).setUpdDate(dateformat.format(date));
			jltrn.get(i).setUpUsercd(inputParam.getUserID());
			jltrn.get(i).setAddDate(dateformat.format(date));
			jltrn.get(i).setAddUsercd(inputParam.getUserID());
		}
		joblandmapper.deleteByJobCd(null, inputParam.getCost_no(), Integer.valueOf(inputParam.getCompanyID()), "orderlabeltrn");
		costMapper.insertCostLable(jltrn);
		    }
		//插入外发
		//外发更新锁
		inputParam.setLockflg(inputParam.getLockflg()+1);
		inputParam.setCompany_cd(inputParam.getCompanyID());
		costMapper.updateCostOutSoureTx(inputParam);
		//插入发票数据
		int a = costMapper.updateInvoiceintrnTx(inputParam);
		output.put("num", a);
		return output;
	};
	
	public Map<String,Object> initOutSoure(CostInput inputParam){
		HashMap<String, Object> outsoure = new HashMap<String, Object>();
		if(!commonMethedService.validatePower(inputParam.getJob_cd(), inputParam.getUserID(), Integer.valueOf(inputParam.getCompanyID()), null, "insertAndUpdCost")) {
			outsoure.put("validatePower", "0");
			return outsoure;
		}else {
			outsoure.put("validatePower", "1");
		}
	
		//初始化外货下拉列表
		List<Cost> Foreign= costMapper.selCostForeign(Integer.valueOf(inputParam.getCompanyID()),null,"0050");
		outsoure.put("Foreign", Foreign);
		//查询得意先
		List<Cost> CLDIV=	costMapper.selectCLDIV(inputParam.getJob_cd(),inputParam.getCompanyID());
		if(CLDIV.size()<1) {
			outsoure.put("messge", "DATA_IS_NOT_EXIST");
		}else {
			if(!"".equals(CLDIV.get(0).getUpddate())) {
				CLDIV.get(0).setUpddate(commonMethedService.getTimeByZone(CLDIV.get(0).getUpddate(),inputParam.getCompanyID()));		
			}
		}
		outsoure.put("CLDIV", CLDIV);
		List<Lable> lable= costMapper.selectLableList(inputParam.getUserID(),inputParam.getCompanyID());
		outsoure.put("lable", lable);
		String foreignFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0051",inputParam.getCompanyID(),"001");
		String saleVatFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0052",inputParam.getCompanyID(),"002");
		outsoure.put("foreignFormatFlg", foreignFormatFlg);
		outsoure.put("saleVatFormatFlg", saleVatFormatFlg);
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String systemDate = commonMethedService.getTimeByZone(dateformat.format(date),inputParam.getCompanyID()).substring(0,10);
		outsoure.put("systemDate", systemDate);
		
		outsoure.put("orderNoIsMustFlg", commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID()));
	return outsoure;
	}
	
	public Map<String,Object> initCostupdate(CostInput inputParam){
		HashMap<String, Object> outsoure = new HashMap<String, Object>();
		if(!commonMethedService.validatePower(inputParam.getJob_cd(), inputParam.getUserID(), Integer.valueOf(inputParam.getCompanyID()), null, "insertAndUpdCost")) {
			outsoure.put("validatePower", "0");
			return outsoure;
		}else {
			outsoure.put("validatePower", "1");
		}
		//初始化外货下拉列表
		List<Cost> Foreign= costMapper.selCostForeign(Integer.valueOf(inputParam.getCompanyID()),null,"0050");
		outsoure.put("Foreign", Foreign);
		//查询得意先
		List<Cost> CLDIV=	costMapper.selectCLDIV(inputParam.getJob_cd(),inputParam.getCompanyID());
		if(CLDIV.size()<1) {
			outsoure.put("messge", "DATA_IS_NOT_EXIST");
			return outsoure;
		}else {
			if(!"".equals(CLDIV.get(0).getUpddate())) {
				CLDIV.get(0).setUpddate(commonMethedService.getTimeByZone(CLDIV.get(0).getUpddate(),inputParam.getCompanyID()));		
			}
		}
		
		outsoure.put("CLDIV", CLDIV);
		List<Lable> lable= costMapper.selectLableList(inputParam.getUserID(),inputParam.getCompanyID());
		outsoure.put("lable", lable);
		//cost与lable关系
		List<JobLableTrn> costlable= costMapper.selectCostLable(inputParam.getCost_no(),inputParam.getCompanyID(),0);
		outsoure.put("costlable", costlable);
		//查询外发数据
		List<Cost> cost= costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID());
		
		if(cost.size()<1) {
			outsoure.put("messge", "DATA_IS_NOT_EXIST");
			return outsoure;
		}else {
			//登录时间添加时区
			if(!"".equals(cost.get(0).getCostadddate())) {
				cost.get(0).setCostadddate(commonMethedService.getTimeByZone(cost.get(0).getCostadddate(),inputParam.getCompanyID()));		
			}
			//更新时间
			if(!"".equals(cost.get(0).getCostupdate())) {
				cost.get(0).setCostupdate(commonMethedService.getTimeByZone(cost.get(0).getCostupdate(),inputParam.getCompanyID()));		
			}
			outsoure.put("cost", cost);
		}
		CostInput cost1 = new CostInput();
		cost1.setPayee_cd(cost.get(0).getPayeecd());
		/*SimpleDateFormat simpleDateFormat=new SimpleDateFormat(cost.get(0).getPlandlvday());
		Date date;
		try {
			date = simpleDateFormat.parse(cost.get(0).getPlandlvday());
			cost1.setPlan_dlvday(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		cost1.setPlan_dlvdays(cost.get(0).getPlandlvday());
		cost1.setCompanyID(inputParam.getCompanyID());
		cost1.setInvoice_text(cost.get(0).getInvoicetext());
		cost1.setInvoice_type(cost.get(0).getInvoicetype());
		
		cost.get(0).setCostVatRate(cost.get(0).getCostrate().toString());
		List<Cost> list = costMapper.selectTax(cost1);
		if(list.size()!=0) {
			cost.get(0).setCostrate(list.get(0).getVatrate());
		}
		List<Cost> Invoiceintrn= costMapper.selInvoiceintrn(inputParam.getCost_no(),inputParam.getCompanyID());
		outsoure.put("Invoiceintrn", Invoiceintrn);
		String foreignFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0051",inputParam.getCompanyID(),"001");
		String saleVatFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0052",inputParam.getCompanyID(),"002");
		outsoure.put("foreignFormatFlg", foreignFormatFlg);
		outsoure.put("saleVatFormatFlg", saleVatFormatFlg);
		JobListInput jobInput = new JobListInput();
		jobInput.setDlvfalg("005");//计上济
		jobInput.setJob_cd(inputParam.getJob_cd());
		jobInput.setCompanyID(inputParam.getCompanyID());
		List<JobList> joblist=jobmapper.selectjobStatus(jobInput);
		outsoure.put("joblist", joblist);
		outsoure.put("orderNoIsMustFlg", commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID()));
		return outsoure;
	}
	
	public Map<String,Object> initInvoice(CostInput inputParam){
		HashMap<String, Object> outsoure = new HashMap<String, Object>();
		
		//发票种类
		List<Cost> PayeeType  =	costMapper.selectPayeeType("0061",inputParam.getCompanyID());
		//发票内容
		List<Cost> Payeetext  =	costMapper.selectPayeeType("0062",inputParam.getCompanyID());
		outsoure.put("invoice_type", PayeeType);
		outsoure.put("invoice_text", Payeetext);
		return outsoure; 
	}
	 public Cost selectTax(CostInput CostInput) {
		 Cost tax= new Cost();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		 CostInput.setPlan_dlvdays( format.format(CostInput.getPlan_dlvday()));
		 List<Cost> taxlist  =	costMapper.selectTax(CostInput);
		 if(taxlist.size()>0) {
			 tax =taxlist.get(0);	 
		 }
		return tax;  
	 }


	public Object getCostListVo(CostListVo clvo,int companycd,String usercd) {
		// TODO Auto-generated method stub
		List<CostListVo> costListVo;
		List<Role> role =commonMethedMapper.searchNodeListByUser(usercd, String.valueOf(companycd));
		boolean flg = false;
		if(role.size()>0) {
			for(int i=0;i<role.size();i++){
               if(role.get(i).getNodeID()==40) {
            	   clvo.setAll("40");
            	   flg = true;
				}
				if(role.get(i).getNodeID()==41) {
				   clvo.setCldivUser(usercd);
				   flg = true;
				}
				if(role.get(i).getNodeID()==42) {
				   clvo.setAdUser(usercd);
				   flg = true;
				}
				if(role.get(i).getNodeID()==43) {
				   clvo.setMdUser(usercd);
				   flg = true;
				}
			}
		}
		if(!flg) {
			 clvo.setAll("no");
		}
		if(clvo.getKeyword()!=null&&clvo.getKeyword()!=""){
			String aa = commonMethedService.changSqlInput(clvo.getKeyword());
			clvo.setStr(aa);
			clvo.setCompanycd(companycd);
			 costListVo=costListMapper.getCostListVogue(clvo);
		}else {
			//详细检索获取 检索类型0 ：其他 
			if(clvo.getSearchType()==1) {
				clvo.setCompanycd(companycd);
				if(clvo.isConditionFlg()) {
					int costListCount = costListMapper.getcostCount(clvo);
					if(costListCount>3000) {
						 Map<String,Object> map = new HashMap<String,Object>();
						 map.put("TooMuchData", 0);
						 return map;
					}
				}
				clvo.setInputno(commonMethedService.changSqlInput(clvo.getInputno()));
				clvo.setCostno(commonMethedService.changSqlInput(clvo.getCostno()));
				clvo.setName(commonMethedService.changSqlInput(clvo.getName()));
				clvo.setLable(commonMethedService.changSqlInput(clvo.getLable()));
				 costListVo=costListMapper.getCostList(clvo);
			}else if(clvo.getSearchType()==2) {
				clvo.setCompanycd(companycd);
				int costListCount = costListMapper.getcostCount(clvo);
				if(costListCount>3000) {
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("TooMuchData", 0);
					 return map;
				}
				 costListVo=costListMapper.getCostListByOther(clvo);
				 }
			else{
				 clvo.setCompanycd(companycd);
					if(clvo.isConditionFlg()) {
						int costListCount = costListMapper.getcostCount(clvo);
						if(costListCount>3000) {
							 Map<String,Object> map = new HashMap<String,Object>();
							 map.put("TooMuchData", 0);
							 return map;
						}
					}
					 costListVo=costListMapper.getCostListByOther(clvo);
				}
			
			}
		if(costListVo.size()>3000) {
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("TooMuchData", 0);
			 return map;
		}
		 List<String> cldivList = commonMethedMapper.selUsercldiv(usercd, String.valueOf(companycd));
		 if(costListVo.size()>0) {
			 for(CostListVo cVo : costListVo) {
			    	cVo.setReqList(commonMethedMapper.selJobuser("2", cVo.getJobcd(), String.valueOf(companycd)));
			    	cVo.setMdList(commonMethedMapper.selJobuser("3", cVo.getJobcd(), String.valueOf(companycd)));
			    	cVo.setCldivList(cldivList);
			    	//系统时间，根据时区把数据库中取出的值，重新赋值。
					if(!"".equals(cVo.getAdddate())) {
						cVo.setAdddate(commonMethedService.getTimeByZone(cVo.getAdddate(),String.valueOf(companycd)));	
					}
					if(!"".equals(cVo.getPayreqdate())) {
						cVo.setPayreqdate(commonMethedService.getTimeByZone(cVo.getPayreqdate(),String.valueOf(companycd)));
					}
					if(!"".equals(cVo.getConfirmdate())) {
						cVo.setConfirmdate(commonMethedService.getTimeByZone(cVo.getConfirmdate(),String.valueOf(companycd)));
					}
			    }
			    costListVo.get(0).setPayStatsList(jobLandMapper.selectSaleCode(companycd, null, "0015"));
			    costListVo.get(0).setCoststatusList(jobLandMapper.selectSaleCode(companycd, null, "0023"));
		 }
		 
		 /*********************************默认查询**********************************/
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("costListVo", costListVo);
		 
		 List<Role> rolist =commonMethedMapper.searchNodeListByUser(usercd,String.valueOf(companycd));
//		 boolean flgLable = false;
//		    if(rolist.size()>0) {
//		    	for(Role roleN:rolist){
//		    	    if(roleN.getNodeID()==71) {
//		    	    	flgLable=true;
//		    	    	 break;
//		    	    }
//		    	}
//		    }
//		    if(flgLable) {
		    	map.put("lableList", jobLandMapper.selectLableList(usercd, 1, companycd,1));	
//		    }else {
//		    	map.put("lableList", jobLandMapper.selectLableList(usercd, 1, companycd,0));	
//		    }
		    List<List<Columns>> colList = new ArrayList<List<Columns>>();
		    map.put("payStatus", jobLandMapper.selectSaleCode(companycd, null, "0017"));
		    map.put("payStatusShow", jobLandMapper.selectSaleCode(companycd, null, "0024"));
		    map.put("costType", jobLandMapper.selectSaleCode(companycd, null, "9002"));
		    map.put("baseStatus", jobLandMapper.selectSaleCode(companycd, null, "0015"));
		    colList.add(commonMethedMapper.selColumns(companycd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME"));
		    colList.add(commonMethedMapper.selColumns(companycd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME_EN"));
		    colList.add(commonMethedMapper.selColumns(companycd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME_JP"));
		    colList.add(commonMethedMapper.selColumns(companycd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME_HK"));
		    map.put("colmst",colList);
		    map.put("skip", commonMethedMapper.selectSkipByCd(companycd));
		    map.put("orgPriceList", jobLandMapper.selectSaleCode(companycd,null,"0003"));
		    map.put("costFlg",jobLandMapper.selectSaleCode(companycd,null,"0023"));
		    map.put("tranCode",jobLandMapper.selectSaleCode(companycd,null,"9002"));
		    //发注书出力flg
		    map.put("output",jobLandMapper.selectSaleCode(companycd,null,"0028"));
 		return map;
	}


	public Map<String, Object> pageLoad(CostListVo clvo,int company_cd,String usercd,String ad) {
		// TODO Auto-generated method stub
		List<Role> roleList =commonMethedMapper.searchNodeListByUser(usercd, String.valueOf(company_cd));
		boolean flg = false;
		if(roleList.size()>0) {
			for(int i=0;i<roleList.size();i++){
               if(roleList.get(i).getNodeID()==40) {
            	   clvo.setAll("40");
            	   flg = true;
				}
				if(roleList.get(i).getNodeID()==41) {
				   clvo.setCldivUser(usercd);
				   flg = true;
				}
				if(roleList.get(i).getNodeID()==42) {
				   clvo.setAdUser(usercd);
				   flg = true;
				}
				if(roleList.get(i).getNodeID()==43) {
				   clvo.setMdUser(usercd);
				   flg = true;
				}
			}
		}
		if(!flg) {
			 clvo.setAll("no");
		}
	    Map<String,Object> map = new HashMap<String,Object>();
	    List<List<Columns>> colList = new ArrayList<List<Columns>>();
	    //查询会计年月
	    String sysDate = commonMethedMapper.getSystemDate(company_cd);
	     //获取上月的结账时间
	    String stt= DateUtil.makeDate(sysDate);
	    String checkDate = costListMapper.selectCheckDate(stt,company_cd);
	    map.put("payStatus", jobLandMapper.selectSaleCode(company_cd, null, "0017"));
	    map.put("payStatusShow", jobLandMapper.selectSaleCode(company_cd, null, "0024"));
	    map.put("costType", jobLandMapper.selectSaleCode(company_cd, null, "9002"));
	    map.put("baseStatus", jobLandMapper.selectSaleCode(company_cd, null, "0015"));
	    colList.add(commonMethedMapper.selColumns(company_cd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME"));
	    colList.add(commonMethedMapper.selColumns(company_cd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME_EN"));
	    colList.add(commonMethedMapper.selColumns(company_cd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME_JP"));
	    colList.add(commonMethedMapper.selColumns(company_cd, 1, Integer.valueOf(usercd), 1,"COLUMN_SHOW_NAME_HK"));
	    map.put("colmst",colList);
	   // clvo.setAd(ad);
	    clvo.setCompanycd(company_cd);
	    clvo.setCheckDate(checkDate);
	    //未承认件数  承认日是null 支付申请济的
	 //   clvo.setDiscd("001");
        //初始化如果是从导航栏处处跳转过来 ad参数为6   查询此员工的部门cd 设置对应标识  用于初始化查询
	    List<CostListVo> ss = new ArrayList<CostListVo>();
	    List<JobList> jobList = jobmapper.selectUserDPM(usercd, String.valueOf(company_cd));
	    String departCD = jobList.get(0).getDepartcd();
	    //检索flg
	    boolean searchFlg = false;
        if("6".equals(clvo.getAd())) {
        	//财务部门
        	if("002".equals(departCD)) {
        		clvo.setAd("Finance");
        		ss = costListMapper.getCostListFromTop(clvo);
        		searchFlg = true;
        	}
        	//企业推广部
        	if("003".equals(departCD)) {
        		clvo.setAd("Traffic");
        		ss = costListMapper.getCostListFromTop(clvo);
        		searchFlg = true;
        	}
        	
	    }else {
	    	if(clvo.getAd().equals("2")&&clvo.getDalday1()!=null&&!clvo.getDalday1().equals("")) {
	    		int year = Integer.parseInt(sysDate.split("-")[0]);  
	    		int month = Integer.parseInt(sysDate.split("-")[1]); 
	    		Calendar cal = Calendar.getInstance();
	    		cal.set(Calendar.YEAR, year);
	    		cal.set(Calendar.MONTH, month);
	    		int lastDay = cal.getMinimum(Calendar.DATE); 
	    		cal.set(Calendar.DAY_OF_MONTH, lastDay - 1);
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    		String dalDay = sdf.format(cal.getTime());
	    		clvo.setDalday1(dalDay);
	    	}
	    	ss = costListMapper.getCostListFromTop(clvo);
	    	searchFlg = true;
	    }
        if(ss!=null) {
    	    List<String> cldivList = commonMethedMapper.selUsercldiv(usercd, String.valueOf(company_cd));
    	    for(CostListVo cVo : ss) {
    	    	cVo.setReqList(commonMethedMapper.selJobuser("2", cVo.getJobcd(), String.valueOf(company_cd)));
    	    	cVo.setMdList(commonMethedMapper.selJobuser("3", cVo.getJobcd(), String.valueOf(company_cd)));
    	    	cVo.setCldivList(cldivList);
    	    	
    	    	//系统时间，根据时区把数据库中取出的值，重新赋值。
				if(!"".equals(cVo.getAdddate())) {
					cVo.setAdddate(commonMethedService.getTimeByZone(cVo.getAdddate(),String.valueOf(company_cd)));	
				}
				if(!"".equals(cVo.getPayreqdate())) {
					cVo.setPayreqdate(commonMethedService.getTimeByZone(cVo.getPayreqdate(),String.valueOf(company_cd)));
				}
				if(!"".equals(cVo.getConfirmdate())) {
					cVo.setConfirmdate(commonMethedService.getTimeByZone(cVo.getConfirmdate(),String.valueOf(company_cd)));
				}
    	    	
    	    }	
        }

	    map.put("costListByTop",ss);
	    map.put("skip", commonMethedMapper.selectSkipByCd(company_cd));
	    map.put("orgPriceList", jobLandMapper.selectSaleCode(company_cd,null,"0003"));
	    map.put("costFlg",jobLandMapper.selectSaleCode(company_cd,null,"0023"));
	    map.put("tranCode",jobLandMapper.selectSaleCode(company_cd,null,"9002"));
	    //发注书出力flg
	    map.put("output",jobLandMapper.selectSaleCode(company_cd,null,"0028"));
	    //判断权限 拥有管理权限 查询自己与管理者的标签  
	    List<Role> rolist =commonMethedMapper.searchNodeListByUser(usercd,String.valueOf(company_cd));
	    if(rolist.size()>0) {
	    	for(Role role:rolist){
	    	    if(role.getNodeID()==71) {
	    	    	flg=true;
	    	    	 break;
	    	    }
	    	}
	    }
	    if(flg) {
	    	map.put("lableList", jobLandMapper.selectLableList(usercd, 1, company_cd,1));	
	    }else {
	    	map.put("lableList", jobLandMapper.selectLableList(usercd, 1, company_cd,0));	
	    }
	    
	    map.put("isSelect", searchFlg);
		return map;
	}
	public List<Cost> selectCLDIV(String jobcd,String company_cd){
		return costMapper.selectCLDIV(jobcd,company_cd);
	}
	public int payDealTx(PayInput pay) {
		// TODO Auto-generated method stub
		List<PayDeal> pd = pay.getPayInput();
		int companycd = Integer.valueOf(pay.getCompanyID());
		PayDeal payDeal;
		int num = 0;
		int payDBLockFlg = 0;
		int payPreLockFlg = 0;
	    for(int i=0;i<pd.size();i++) {
	    	
	    	payDeal = pd.get(i);
	    	payDBLockFlg =payMapper.getPayLockFlg(payDeal.getInputno(),companycd) ;
	    	payPreLockFlg = payDeal.getPaylockflg()+1;
	    	if(!(payPreLockFlg>payDBLockFlg)) {
	    		return 540;
	    	}else {
	    		payDeal.setCompanycd(Integer.valueOf(pay.getCompanyID()));
		    	payDeal.setUpDateDate(DateUtil.getNowDate());
		    	payDeal.setUpUsercd(pay.getUserID());
		    	payDeal.setPaylockflg(payPreLockFlg);
		    	 num+=costListMapper.insertPaytrnByInum(payDeal);
	    	}
	    	
	    }
		return num;
	}
	
	/* (非 Javadoc) 
	* <p>Title: deleteOutSoure</p> 
	* <p>Description:删除外发信息 </p> 
	* @return List<CommonmstVo>
	* @see com.kaiwait.service.mst.CommonmstService#selectAll() 
	*/
	public int deleteOutSoure(CostInput inputParam) {
		int num;
		//查询外发数据
		List<Cost> cost= costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID());
		String input_no = cost.get(0).getInputno();
		JobListInput jobInput = new JobListInput();
		jobInput.setDlvfalg("005");//计上济
		jobInput.setJob_cd(inputParam.getJob_cd());
		jobInput.setCompanyID(inputParam.getCompanyID());
		List<JobList> joblist=jobmapper.selectjobStatus(jobInput);
		if(cost.size()<1) {
			return 0;
		}
			if(joblist.size()<1) {
				if(input_no!=null&&!"".equals(input_no)) {
					//当存在input_no时代表存在，支付情报信息。需要先将支付情报信息删除之后，才可以删除外发信息
					//所以返回0，代表更新0条数据
					num=0;
				}else {
					//更新锁
					if(cost.get(0).getLockflg()==inputParam.getLockflg()) {
						/*//発注书出力了，不能删除
						if("".equals(cost.get(0).getCostpdfdate())) {*/
							num=1;
							costMapper.deleteOutSoure(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID());
						/*}else {
							num=0;
						}*/
					}else {
						num=0;
					}
					}
			}else {
				num=2;
			}
		return num;
	}
	public int updateTitleUpTx(List<TitleMsg> titleMsg) {
		// TODO Auto-generated method stub
		int num = 0;
		for(TitleMsg title:titleMsg) {
			num+=commonMethedMapper.updateTitleMsg(title);
		}
		
		return num;
	}
	
	
	@Override
	public String OrderPDFTx(OutPutInput inputParam) throws JRException {
		CostPDF payoutPDF= costMapper.OrderPDF(inputParam);
		if(payoutPDF.getItmvalue()!=null&&!payoutPDF.getItmvalue().equals("")) {
			//如果有外货用外货的小数点位数
			if(payoutPDF.getForeignitemvalue()!=null&&!payoutPDF.getForeignitemvalue().equals("")) {
				payoutPDF.setItmvalue(payoutPDF.getForeignitemvalue());
			}
			String costvatamt = payoutPDF.getCostvatamt();
			String costForeignamt= payoutPDF.getCostforeignamt();
			String costVatAmt []=costvatamt.split("\\.");
			String  costForeignAmt[]=costForeignamt.split("\\.");
			String point = "";
			if(payoutPDF.getItmvalue().equals("0")) {
				String  costForeignAmtN = costForeignAmt[0];
				String costamtvatN = costVatAmt[0];
				payoutPDF.setCostvatamt(costamtvatN);
				payoutPDF.setCostforeignamt(costForeignAmtN);
			}else {
				String  costForeignAmtN = costForeignAmt[1].substring(0,Integer.parseInt(payoutPDF.getItmvalue()));
				String costVatAmtN = costVatAmt[1].substring(0,Integer.parseInt(payoutPDF.getItmvalue()));
				String costforeignamt = costForeignAmt[0]+"."+costForeignAmtN;
				String costVatamt = costVatAmt[0]+"."+costVatAmtN;
				payoutPDF.setCostforeignamt(costforeignamt);
				payoutPDF.setCostvatamt(costVatamt);
			}
		}
		if(payoutPDF==null) {
	     return "STATUS_VALIDATEPOWER_ERROR";
		}
		//发注书PDF的【担当者】应该显示登陆原价的人  
		String ddname = costMapper.SelectLogName(inputParam.getCompanyID(),payoutPDF.getCostno(),payoutPDF.getJobcd());
		payoutPDF.setDdname(ddname);
		File yin = new File(JasperOutPdfUtil.JRXMLPATH+"yin"+inputParam.getCompanyID()+".png");
		File logo = new File(JasperOutPdfUtil.JRXMLPATH+"logo"+inputParam.getCompanyID()+".png");
		if (yin.exists()) {
			payoutPDF.setImgYin(JasperOutPdfUtil.JRXMLPATH+"yin"+inputParam.getCompanyID()+".png");
	        }
		if (logo.exists()) {
			payoutPDF.setImgLogo(JasperOutPdfUtil.JRXMLPATH+"logo"+inputParam.getCompanyID()+".png");
	        }
		//发注先地址取值判断
		//中文简体
		if(!"".equals(payoutPDF.getContraselfcompanyname())||!"".equals(payoutPDF.getContraaddress())||!"".equals(payoutPDF.getContraaddress1())||!"".equals(payoutPDF.getContraaddress2())||!"".equals(payoutPDF.getContratel())) {
			payoutPDF.setOrdercompanyname(payoutPDF.getContraselfcompanyname());
			payoutPDF.setOrderlocation(payoutPDF.getContraaddress());
			payoutPDF.setOrderlocation1(payoutPDF.getContraaddress1());
			payoutPDF.setOrderlocation2(payoutPDF.getContraaddress2());
			payoutPDF.setTelnumber(payoutPDF.getContratel());
			
		 }
		//中文繁体
		if(!"".equals(payoutPDF.getContraselfcompanynamehk())||!"".equals(payoutPDF.getContraaddresshk())||!"".equals(payoutPDF.getContraaddresshk1())||!"".equals(payoutPDF.getContraaddresshk2())||!"".equals(payoutPDF.getContratelhk())) {
			payoutPDF.setOrdercompanynamehk(payoutPDF.getContraselfcompanynamehk());
			payoutPDF.setOrderlocationhk(payoutPDF.getContraaddresshk());
			payoutPDF.setOrderlocation1hk(payoutPDF.getContraaddresshk1());
			payoutPDF.setOrderlocation2hk(payoutPDF.getContraaddresshk2());
			payoutPDF.setTelnumberhk(payoutPDF.getContratelhk());
		 }
		//日语
		if(!"".equals(payoutPDF.getContraselfcompanynamejp())||!"".equals(payoutPDF.getContraaddressjp())||!"".equals(payoutPDF.getContraaddressjp1())||!"".equals(payoutPDF.getContraaddressjp2())||!"".equals(payoutPDF.getContrateljp())) {
			payoutPDF.setOrdercompanynamejp(payoutPDF.getContraselfcompanynamejp());
			payoutPDF.setOrderlocationjp(payoutPDF.getContraaddressjp());
			payoutPDF.setOrderlocation1jp(payoutPDF.getContraaddressjp1());
			payoutPDF.setOrderlocation2jp(payoutPDF.getContraaddressjp2());
			payoutPDF.setTelnumberjp(payoutPDF.getContrateljp());
		 }
		//英语
		if(!"".equals(payoutPDF.getContraselfcompanynameen())||!"".equals(payoutPDF.getContraaddressen())||!"".equals(payoutPDF.getContraaddressen1())||!"".equals(payoutPDF.getContraaddressen2())||!"".equals(payoutPDF.getContratelen())) {
			payoutPDF.setOrdercompanynameen(payoutPDF.getContraselfcompanynameen());
			payoutPDF.setOrderlocationen(payoutPDF.getContraaddressen());
			payoutPDF.setOrderlocation1en(payoutPDF.getContraaddressen1());
			payoutPDF.setOrderlocation2en(payoutPDF.getContraaddressen2());
			payoutPDF.setTelnumberen(payoutPDF.getContratelen());
		 }
		//取引先判断（発注书，左侧公司名）
		if(!"".equals(payoutPDF.getContracontactsname())) {
			payoutPDF.setDivnamefull(payoutPDF.getContracontactsname());
		}
		if(!"".equals(payoutPDF.getContracontactsnameen())) {
			payoutPDF.setDivnamefullen(payoutPDF.getContracontactsnameen());
		}
		if(!"".equals(payoutPDF.getContracontactsnamejp())) {
			payoutPDF.setDivnamefulljp(payoutPDF.getContracontactsnamejp());
		}
		if(!"".equals(payoutPDF.getContracontactsnamehk())) {
			payoutPDF.setDivnamefullhk(payoutPDF.getContracontactsnamehk());
		}
		
		//payoutPDF.setCompanycd(inputParam.getCompanyID());
		Locale local = JasperOutPdfUtil.getLocalByLangT(inputParam.getLangTyp());
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
	   //税入税拔
		if("1".equals(payoutPDF.getCostishave())) {
			payoutPDF.setCostishave(bundle.getString("haveTax"));
			//税入
		}else {
			payoutPDF.setCostishave(bundle.getString("notHaveTax"));
			//税拔
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate =   dateFormat.format(new Date());
		payoutPDF.setFzorderdate(commonMethedService.getTimeByZone(currentDate,inputParam.getCompanyID()));
		List<CostPDF> jbs = new ArrayList<CostPDF>();
		jbs.add(payoutPDF);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, inputParam.getFileName(), inputParam.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		if(!"jobDetails".equals(inputParam.getJobDtFlg())) {
			//更新pdf出力时间
			CostInput pdfinput = new CostInput();
			pdfinput.setJob_cd(inputParam.getJobNo());
			pdfinput.setCost_no(inputParam.getCostNo());
			pdfinput.setCompanyID(inputParam.getCompanyID());
			pdfinput.setCost_pdf_addusercd(Integer.valueOf(inputParam.getUserID()));
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			pdfinput.setCost_pdf_adddate(dateformat.format(date));
			updPdftimeTx(pdfinput);
		}
		return fileBaseStr;
	}
	//更新pdf次数
	public int updPdftimeTx(CostInput inputParam) {
		int num =0;
		num=costMapper.updPdftime(inputParam);
		return num;
	}
	@Override
	public Object outOrderPDFTx(OutPutInput inputParam) throws JRException {
		String jobcd = inputParam.getJobNo();
		Object  pdf = null;
		String jobNo="";
		String costNo="";
			if(jobcd==null||"".equals(jobcd)) {
					List<byte[]> pdfList = new ArrayList<byte[]>();
					for(int i=0;i<inputParam.getJobList().size();i++) {
						jobNo = inputParam.getJobList().get(i);
						costNo = inputParam.getCostList().get(i);
						pdfList.add(Base64.getDecoder().decode(this.OrderPDFTxs(inputParam,jobNo,costNo)));
					}
					try {
						pdf = PdfToZipUtil.pdfToZip(pdfList,inputParam.getPdfName(),inputParam.getCostList(),"","");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return pdf;
			}else{
				return this.OrderPDFTx(inputParam);
			}
	}
	private String OrderPDFTxs(OutPutInput inputParam,String jobNo,String costNo) throws JRException {
		
		CostPDF payoutPDF= costMapper.OrderPDF(inputParam.getCompanyID(),jobNo,costNo);
		if(payoutPDF.getItmvalue()!=null&&!payoutPDF.getItmvalue().equals("")) {
			//如果有外货用外货的小数点位数
			if(payoutPDF.getForeignitemvalue()!=null&&!payoutPDF.getForeignitemvalue().equals("")) {
				payoutPDF.setItmvalue(payoutPDF.getForeignitemvalue());
			}
			String costvatamt = payoutPDF.getCostvatamt();
			String costForeignamt= payoutPDF.getCostforeignamt();
			String costVatAmt []=costvatamt.split("\\.");
			String  costForeignAmt[]=costForeignamt.split("\\.");
			String point = "";
			if(payoutPDF.getItmvalue().equals("0")) {
				String  costForeignAmtN = costForeignAmt[0];
				String costamtvatN = costVatAmt[0];
				payoutPDF.setCostvatamt(costamtvatN);
				payoutPDF.setCostforeignamt(costForeignAmtN);
			}else {
				String  costForeignAmtN = costForeignAmt[1].substring(0,Integer.parseInt(payoutPDF.getItmvalue()));
				String costVatAmtN = costVatAmt[1].substring(0,Integer.parseInt(payoutPDF.getItmvalue()));
				String costforeignamt = costForeignAmt[0]+"."+costForeignAmtN;
				String costVatamt = costVatAmt[0]+"."+costVatAmtN;
				payoutPDF.setCostforeignamt(costforeignamt);
				payoutPDF.setCostvatamt(costVatamt);
			}
		}
		if(payoutPDF==null) {
	     return "STATUS_VALIDATEPOWER_ERROR";
		}
		//发注书PDF的【担当者】应该显示登陆原价的人  
		String ddname = costMapper.SelectLogName(inputParam.getCompanyID(),payoutPDF.getCostno(),payoutPDF.getJobcd());
		payoutPDF.setDdname(ddname);
		File yin = new File(JasperOutPdfUtil.JRXMLPATH+"yin"+inputParam.getCompanyID()+".png");
		File logo = new File(JasperOutPdfUtil.JRXMLPATH+"logo"+inputParam.getCompanyID()+".png");
		if (yin.exists()) {
			payoutPDF.setImgYin(JasperOutPdfUtil.JRXMLPATH+"yin"+inputParam.getCompanyID()+".png");
	        }
		if (logo.exists()) {
			payoutPDF.setImgLogo(JasperOutPdfUtil.JRXMLPATH+"logo"+inputParam.getCompanyID()+".png");
	        }
		//发注先地址取值判断
		//中文简体
		if(!"".equals(payoutPDF.getContraselfcompanyname())||!"".equals(payoutPDF.getContraaddress())||!"".equals(payoutPDF.getContraaddress1())||!"".equals(payoutPDF.getContraaddress2())||!"".equals(payoutPDF.getContratel())) {
			payoutPDF.setOrdercompanyname(payoutPDF.getContraselfcompanyname());
			payoutPDF.setOrderlocation(payoutPDF.getContraaddress());
			payoutPDF.setOrderlocation1(payoutPDF.getContraaddress1());
			payoutPDF.setOrderlocation2(payoutPDF.getContraaddress2());
			payoutPDF.setTelnumber(payoutPDF.getContratel());
			
		 }
		//中文繁体
		if(!"".equals(payoutPDF.getContraselfcompanynamehk())||!"".equals(payoutPDF.getContraaddresshk())||!"".equals(payoutPDF.getContraaddresshk1())||!"".equals(payoutPDF.getContraaddresshk2())||!"".equals(payoutPDF.getContratelhk())) {
			payoutPDF.setOrdercompanynamehk(payoutPDF.getContraselfcompanynamehk());
			payoutPDF.setOrderlocationhk(payoutPDF.getContraaddresshk());
			payoutPDF.setOrderlocation1hk(payoutPDF.getContraaddresshk1());
			payoutPDF.setOrderlocation2hk(payoutPDF.getContraaddresshk2());
			payoutPDF.setTelnumberhk(payoutPDF.getContratelhk());
		 }
		//日语
		if(!"".equals(payoutPDF.getContraselfcompanynamejp())||!"".equals(payoutPDF.getContraaddressjp())||!"".equals(payoutPDF.getContraaddressjp1())||!"".equals(payoutPDF.getContraaddressjp2())||!"".equals(payoutPDF.getContrateljp())) {
			payoutPDF.setOrdercompanynamejp(payoutPDF.getContraselfcompanynamejp());
			payoutPDF.setOrderlocationjp(payoutPDF.getContraaddressjp());
			payoutPDF.setOrderlocation1jp(payoutPDF.getContraaddressjp1());
			payoutPDF.setOrderlocation2jp(payoutPDF.getContraaddressjp2());
			payoutPDF.setTelnumberjp(payoutPDF.getContrateljp());
		 }
		//英语
		if(!"".equals(payoutPDF.getContraselfcompanynameen())||!"".equals(payoutPDF.getContraaddressen())||!"".equals(payoutPDF.getContraaddressen1())||!"".equals(payoutPDF.getContraaddressen2())||!"".equals(payoutPDF.getContratelen())) {
			payoutPDF.setOrdercompanynameen(payoutPDF.getContraselfcompanynameen());
			payoutPDF.setOrderlocationen(payoutPDF.getContraaddressen());
			payoutPDF.setOrderlocation1en(payoutPDF.getContraaddressen1());
			payoutPDF.setOrderlocation2en(payoutPDF.getContraaddressen2());
			payoutPDF.setTelnumberen(payoutPDF.getContratelen());
		 }
		//取引先判断（発注书，左侧公司名）
		if(!"".equals(payoutPDF.getContracontactsname())) {
			payoutPDF.setDivnamefull(payoutPDF.getContracontactsname());
		}
		if(!"".equals(payoutPDF.getContracontactsnameen())) {
			payoutPDF.setDivnamefullen(payoutPDF.getContracontactsnameen());
		}
		if(!"".equals(payoutPDF.getContracontactsnamejp())) {
			payoutPDF.setDivnamefulljp(payoutPDF.getContracontactsnamejp());
		}
		if(!"".equals(payoutPDF.getContracontactsnamehk())) {
			payoutPDF.setDivnamefullhk(payoutPDF.getContracontactsnamehk());
		}
		
		//payoutPDF.setCompanycd(inputParam.getCompanyID());
		Locale local = JasperOutPdfUtil.getLocalByLangT(inputParam.getLangTyp());
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
	   //税入税拔
		if("1".equals(payoutPDF.getCostishave())) {
			payoutPDF.setCostishave(bundle.getString("haveTax"));
			//税入
		}else {
			payoutPDF.setCostishave(bundle.getString("notHaveTax"));
			//税拔
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate =   dateFormat.format(new Date());
		payoutPDF.setFzorderdate(commonMethedService.getTimeByZone(currentDate,inputParam.getCompanyID()));
		List<CostPDF> jbs = new ArrayList<CostPDF>();
		jbs.add(payoutPDF);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, inputParam.getFileName(), inputParam.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		if(!"jobDetails".equals(inputParam.getJobDtFlg())) {
			//更新pdf出力时间
			CostInput pdfinput = new CostInput();
			pdfinput.setJob_cd(inputParam.getJobNo());
			pdfinput.setCost_no(inputParam.getCostNo());
			pdfinput.setCompanyID(inputParam.getCompanyID());
			pdfinput.setCost_pdf_addusercd(Integer.valueOf(inputParam.getUserID()));
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			pdfinput.setCost_pdf_adddate(dateformat.format(date));
			updPdftimeTx(pdfinput);
		}
		return fileBaseStr;
	}
}
