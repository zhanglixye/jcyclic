package com.kaiwait.service.jczh.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.Job;
import com.kaiwait.bean.jczh.entity.JobInfo;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.Pay;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.entity.SaleInfo;
import com.kaiwait.bean.jczh.entity.Skip;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.SpringUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.mappers.jczh.LendtrnMapper;
import com.kaiwait.mappers.jczh.PayMapper;
import com.kaiwait.mappers.jczh.PaytrnMapper;
import com.kaiwait.service.jczh.AccountEntriesService;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.JobService;
import com.kaiwait.utils.common.StringUtil;


@Service
public class JobServiceImpl implements JobService{

	@Resource
	private JobMapper jobMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private CostMapper costMapper;
	@Resource
	private PayMapper payMapper;
	@Resource
	private PaytrnMapper paytrnMapper;
	@Resource
	private LendtrnMapper LendtrnMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private CommonmstMapper commonmstdMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private AccountEntriesService accountEntriesService;
	
	public HashMap<String,Object> searchJobList(JobListInput jobInput)
	{   
		jobInput.setDel_flg("0");
	    //入参通配符转义
		jobInput.setJob_cd(commonMethedService.changSqlInput(jobInput.getJob_cd()));//jobcd
		jobInput.setJob_name(commonMethedService.changSqlInput(jobInput.getJob_name()));//jobname
		jobInput.setLabel_text(commonMethedService.changSqlInput(jobInput.getLabel_text()));//lable
		jobInput.setDdname(commonMethedService.changSqlInput(jobInput.getDdname()));//担当者姓名
		jobInput.setPayer_name(commonMethedService.changSqlInput(jobInput.getPayer_name()));//得意先名
		jobInput.setG_company_name(commonMethedService.changSqlInput(jobInput.getG_company_name()));//G公司
		jobInput.setCldiv_name(commonMethedService.changSqlInput(jobInput.getCldiv_name()));//得意先名
		//当营业falg被传值为1是，为本人担当的。
		if("1".equals(jobInput.getDdflag())) {
			jobInput.setDdcd(jobInput.getUserID());
		}
		List<Role> role =commonMethedMapper.searchNodeListByUser(jobInput.getUserID(), jobInput.getCompanyID());
		boolean flg = false;
		if(role.size()>0) {
			for(int i=0;i<role.size();i++){
               if(role.get(i).getNodeID()==5) {
            	   jobInput.setAll("5");
            	   flg=true;
				}
				if(role.get(i).getNodeID()==6) {
					 jobInput.setDyqx("6");
					 jobInput.setDdqxcd(jobInput.getUserID()); 
					 flg=true;
				}
				if(role.get(i).getNodeID()==7) {
					 jobInput.setDdqx("7");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
				if(role.get(i).getNodeID()==8) {
					 jobInput.setGdqx("8");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
			}
		}
	  if(!flg) {
		  jobInput.setAll("no"); 
	  }
		//计上日开始不为空
		else if(jobInput.getDlvmon_sta()!=null&&jobInput.getDlvmon_sta()!="") {
			//String Dlvmon_sta =jobInput.getDlvmon_sta()+"-"+String.valueOf(1);
			//jobInput.setDlvmon_sta(Dlvmon_sta);
			//计上月结束不为空
			if(jobInput.getDlvmon_end()!=null&&jobInput.getDlvmon_end()!=""){
			/*	int year =Integer.valueOf(jobInput.getDlvmon_end().substring(0, 4)) ;
				int month =Integer.valueOf(jobInput.getDlvmon_end().substring(5));
				Calendar c = Calendar.getInstance();
				c.set(year, month, 0); //输入类型为int类型
				int end_dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
				String Dlvmon_end =String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(end_dayOfMonth);
				jobInput.setDlvmon_end(Dlvmon_end);	*/
			}//计上日结束为空
			else {
				jobInput.setDlvmon_end("9999-12-30");	
			}
			//计上日技术不为空
		}else if(jobInput.getDlvmon_end()!=null&&jobInput.getDlvmon_end()!="") {
		/*	int year =Integer.valueOf(jobInput.getDlvmon_end().substring(0, 4)) ;
			int month =Integer.valueOf(jobInput.getDlvmon_end().substring(5));
			Calendar c = Calendar.getInstance();
			c.set(year, month, 0); //输入类型为int类型
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			String Dlvmon_end =String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth);
			jobInput.setDlvmon_end(Dlvmon_end);*/
			if(jobInput.getDlvmon_sta()==null||jobInput.getDlvmon_sta()=="") {
				jobInput.setDlvmon_sta("1900-1-1");		
			}
		}
		List<JobList> jsflaglanguage =jobMapper.selectMstNameByCD("0020", jobInput.getCompanyID());//计上状态
		List<JobList> reqflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); 	//请求状态
		List<JobList> recflglanguage =jobMapper.selectMstNameByCD("0022", jobInput.getCompanyID()) ;	//入金状态
		List<JobList> invflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); //发票状态
		List<JobList> assignflglanguage =jobMapper.selectMstNameByCD("0026", jobInput.getCompanyID()); //割当担当
		List<JobList> costfinishflglanguage =jobMapper.selectMstNameByCD("0027", jobInput.getCompanyID()); //原价完了
		List<Lable> lableall=jobLandMapper.selectLableList(jobInput.getUserID(), 0, Integer.valueOf(jobInput.getCompanyID()),1);
		if(jobInput.isCondition()) {
			int  jobListCount = jobMapper.searchJobListCount(jobInput);
			if(jobListCount>2000) {
				HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
				jobMesAll.put("TooMuchData", 0);
				return jobMesAll;
			}
		}
		List<JobList> joblist =jobMapper.searchJobList(jobInput);
		if(joblist.size()>2000) {
			HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
			jobMesAll.put("TooMuchData", 0);
			return jobMesAll;
		}
		//查询纽服权限
		List<String> cldivList=commonMethedMapper.selUsercldiv(jobInput.getUserID(), jobInput.getCompanyID());
		if(joblist.size()>0){
			for(int i =0;i<joblist.size();i++) {
				
				List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
                joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
                joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
                joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
				
				//系统时间，根据时区把数据库中取出的值，重新赋值。
				if(!"".equals(joblist.get(i).getAdddate())) {
					joblist.get(i).setAdddate(commonMethedService.getTimeByZone(joblist.get(i).getAdddate(),jobInput.getCompanyID()));	
				}
				if(!"".equals(joblist.get(i).getSaleadddate())) {
					joblist.get(i).setSaleadddate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadddate(),jobInput.getCompanyID()));
				}
				if(!"".equals(joblist.get(i).getSaleadmitdate())) {
					joblist.get(i).setSaleadmitdate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadmitdate(),jobInput.getCompanyID()));	
				}
				//job担当者idlist，用于判断menu跳转权限
				List<String> reqList=commonMethedMapper.selJobuser("2", joblist.get(i).getJobcd(), jobInput.getCompanyID());
				joblist.get(i).setReqList(reqList);
				//job割当者idlist，用于判断menu跳转权限
				List<String> mdList=commonMethedMapper.selJobuser("3", joblist.get(i).getJobcd(), jobInput.getCompanyID());
				joblist.get(i).setMdList(mdList);
				joblist.get(i).setCldivList(cldivList);
			}
		}
		HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
		jobMesAll.put("jsflaglanguage", jsflaglanguage);
		jobMesAll.put("reqflglanguage", reqflglanguage);
		jobMesAll.put("recflglanguage", recflglanguage);
		jobMesAll.put("invflglanguage", invflglanguage);
		jobMesAll.put("assignflglanguage", assignflglanguage);
		jobMesAll.put("costfinishflglanguage", costfinishflglanguage);
		jobMesAll.put("joblist", joblist);
		jobMesAll.put("lableall", lableall);
		//iniselect代表初始是否有查询操作。0:有查询。1:无查询
		jobMesAll.put("initselect", "0");
		return jobMesAll;
	}
	
	public HashMap<String,Object> searchJobListInit(JobListInput jobInput){
		
		jobInput.setDel_flg("0");
		List<Role> role =commonMethedMapper.searchNodeListByUser(jobInput.getUserID(), jobInput.getCompanyID());
		boolean flg = false;
		if(role.size()>0) {
			for(int i=0;i<role.size();i++){
               if(role.get(i).getNodeID()==5) {
            	   jobInput.setAll("5");
            	   flg=true;
				}
				if(role.get(i).getNodeID()==6) {
					 jobInput.setDyqx("6");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
				if(role.get(i).getNodeID()==7) {
					 jobInput.setDdqx("7");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
				if(role.get(i).getNodeID()==8) {
					 jobInput.setGdqx("8");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
			}
		}
		 if(!flg) {
			  jobInput.setAll("no"); 
		  }
		HashMap<String,Object> dropdown = new HashMap<String,Object>();
		//iniselect代表初始是否有查询操作。0:有查询。1:无查询
		dropdown.put("initselect", "0");
		if(!commonMethedService.validatePower(jobInput.getJob_cd(), jobInput.getUserID(), Integer.valueOf(jobInput.getCompanyID()), null, "joblist")) {
			dropdown.put("validatePower", "0");
			return dropdown;
		}else {
			dropdown.put("validatePower", "1");
		}
		//动态加载表头
		 List<List<Columns>> colList = new ArrayList<List<Columns>>();
		 colList.add(commonMethedMapper.selColumns(Integer.valueOf(jobInput.getCompanyID()),0,Integer.valueOf(jobInput.getUserID()),1,"COLUMN_SHOW_NAME"));
		 colList.add(commonMethedMapper.selColumns(Integer.valueOf(jobInput.getCompanyID()),0,Integer.valueOf(jobInput.getUserID()),1,"COLUMN_SHOW_NAME_EN"));
		 colList.add(commonMethedMapper.selColumns(Integer.valueOf(jobInput.getCompanyID()),0,Integer.valueOf(jobInput.getUserID()),1,"COLUMN_SHOW_NAME_JP"));
		 colList.add(commonMethedMapper.selColumns(Integer.valueOf(jobInput.getCompanyID()),0,Integer.valueOf(jobInput.getUserID()),1,"COLUMN_SHOW_NAME_HK"));
		 //分别查处税金2在共同表中的四国语言。赋值于list中，循环。
		 List< JobList> tax2List = new ArrayList<JobList>();
		 JobList itmname= commonMethedMapper.seltaxName("itmname","0063","002", Integer.valueOf(jobInput.getCompanyID()));
		 JobList itemname_en= commonMethedMapper.seltaxName("itemname_en","0063","002", Integer.valueOf(jobInput.getCompanyID()));
		 JobList itemname_jp= commonMethedMapper.seltaxName("itemname_jp","0063","002", Integer.valueOf(jobInput.getCompanyID()));
		 JobList itemname_hk= commonMethedMapper.seltaxName("itemname_hk","0063","002", Integer.valueOf(jobInput.getCompanyID()));
		//截取掉税金后面的（%）
		 List<String> replaceString= new ArrayList<>();
		 replaceString.add("%");
		 replaceString.add("(");
		 replaceString.add(")");
		 itmname.setItmname(replaceString(itmname.getItmname(),replaceString,""));
		 itemname_en.setItmname(replaceString(itemname_en.getItmname(),replaceString,""));
		 itemname_jp.setItmname(replaceString(itemname_jp.getItmname(),replaceString,""));
		 itemname_hk.setItmname(replaceString(itemname_hk.getItmname(),replaceString,""));
		
		 
		 tax2List.add(itmname);//税金2
		 tax2List.add(itemname_en);//税金2
		 tax2List.add(itemname_jp);//税金2
		 tax2List.add(itemname_hk);//税金2
		 for(int i =0 ;i<tax2List.size();i++) {//循环税金2的四种语言
			for(int k =0; k<colList.get(i).size();k++) {//循环查询处的表头
				if ("tax2".equals(colList.get(i).get(k).getField())) {//当表头时tax2时，把commonst中的语言插入到表头列表中
					colList.get(i).get(k).setTitle(tax2List.get(i).getItmname());	
		        }
			} 
		 }
		 
		 //税金3同上
		 List< JobList> tax3List = new ArrayList<JobList>();
		 JobList tax3itmname= commonMethedMapper.seltaxName("itmname","0063","003", Integer.valueOf(jobInput.getCompanyID()));
		 JobList tax3itemname_en= commonMethedMapper.seltaxName("itemname_en","0063","003", Integer.valueOf(jobInput.getCompanyID()));
		 JobList tax3itemname_jp= commonMethedMapper.seltaxName("itemname_jp","0063","003", Integer.valueOf(jobInput.getCompanyID()));
		 JobList tax3itemname_hk= commonMethedMapper.seltaxName("itemname_hk","0063","003", Integer.valueOf(jobInput.getCompanyID()));
		
		 //截取掉税金后面的（%）
		 tax3itmname.setItmname(replaceString(tax3itmname.getItmname(),replaceString,""));
		 tax3itemname_en.setItmname(replaceString(tax3itemname_en.getItmname(),replaceString,""));
		 tax3itemname_jp.setItmname(replaceString(tax3itemname_jp.getItmname(),replaceString,""));
		 tax3itemname_hk.setItmname(replaceString(tax3itemname_hk.getItmname(),replaceString,""));
	
		 
		 tax3List.add(tax3itmname);//税金2
		 tax3List.add(tax3itemname_en);//税金2
		 tax3List.add(tax3itemname_jp);//税金2
		 tax3List.add(tax3itemname_hk);//税金2
	
		 for(int i =0 ;i<tax3List.size();i++) {
				for(int k =0; k<colList.get(i).size();k++) {
					if ("tax3".equals(colList.get(i).get(k).getField())) {
						colList.get(i).get(k).setTitle(tax3List.get(i).getItmname());	
			        }
				} 
			 }
		 //表头复制
		 dropdown.put("columns", colList);
		List<JobList> jsflaglanguage =jobMapper.selectMstNameByCD("0020", jobInput.getCompanyID());//计上状态
		List<JobList> reqflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); 	//请求状态
		List<JobList> recflglanguage =jobMapper.selectMstNameByCD("0022", jobInput.getCompanyID()) ;	//入金状态
		List<JobList> invflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); //发票状态
		List<JobList> assignflglanguage =jobMapper.selectMstNameByCD("0026", jobInput.getCompanyID()); //割当担当
		List<JobList> costfinishflglanguage =jobMapper.selectMstNameByCD("0027", jobInput.getCompanyID()); //原价完了
		
		
		dropdown.put("jsflaglanguage", jsflaglanguage);
		dropdown.put("reqflglanguage", reqflglanguage);
		dropdown.put("recflglanguage", recflglanguage);
		dropdown.put("invflglanguage", invflglanguage);
		dropdown.put("assignflglanguage", assignflglanguage);
		dropdown.put("costfinishflglanguage", costfinishflglanguage);
		//计上状态的下拉列表
		List<JobList> dlvfalg =jobMapper.selectMstNameByCD("0010", jobInput.getCompanyID());
		List<JobList> dlvfalg_data =jobMapper.selectMstNameByCD("0020", jobInput.getCompanyID());
		dropdown.put("dlvfalg", dlvfalg);
		dropdown.put("dlvfalg_data", dlvfalg_data);
		//发票ステータス检索用
		List<JobList> sellInvoiceFlg =jobMapper.selectMstNameByCD("0018", jobInput.getCompanyID()); 
		dropdown.put("sellInvoiceflg", sellInvoiceFlg);
		//割当ステータス检索用
		List<JobList> assign_flg =jobMapper.selectMstNameByCD("0011", jobInput.getCompanyID());
		dropdown.put("assignflg", assign_flg);
		//請求・発票ステータス
		List<JobList> invpd =jobMapper.selectMstNameByCD("0013", jobInput.getCompanyID());
		List<JobList> invpd_data =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID());
		dropdown.put("invpd", invpd);
		dropdown.put("invpd_data", invpd_data);
		// 原価完了ステータス
		List<JobList> cost_finish_flg =jobMapper.selectMstNameByCD("0012", jobInput.getCompanyID());
		dropdown.put("costfinishflg", cost_finish_flg);
		 // 入金ステータス
		List<JobList> rjpd =jobMapper.selectMstNameByCD("0014", jobInput.getCompanyID());
		List<JobList> rjpd_data =jobMapper.selectMstNameByCD("0022", jobInput.getCompanyID());
		dropdown.put("rjpd", rjpd);
		dropdown.put("rjpd_data", rjpd_data);
		//売上種目
		List<JobList> sale =jobMapper.selectSaleName(jobInput.getCompanyID());
		dropdown.put("sale", sale);
		// 查询部门
		List<JobList> department = jobMapper.selectUserDPM(jobInput.getUserID(),jobInput.getCompanyID());
		dropdown.put("department", department);
		//计算税率，影业额等所用参数
		String taxFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0052",jobInput.getCompanyID(),"003");
		String foreignFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0051",jobInput.getCompanyID(),"001");
		dropdown.put("taxFormatFlg", taxFormatFlg);
		dropdown.put("foreignFormatFlg", foreignFormatFlg);
		// 查询公司跳过
		Skip skip = commonMethedMapper.selectSkipByCd( Integer.parseInt(jobInput.getCompanyID()));
		dropdown.put("skip", skip);
		List<Lable> lableall=jobLandMapper.selectLableList(jobInput.getUserID(), 0,Integer.valueOf(jobInput.getCompanyID()),1);
		dropdown.put("lableall", lableall);
		//查询纽服权限
		List<String> cldivList=commonMethedMapper.selUsercldiv(jobInput.getUserID(), jobInput.getCompanyID());
		//根据登录员工所在部门的不同，显示不同的初始化查询数据
		if(jobInput.getSearchFlg().equals(""))
		{
			if("004".equals(department.get(0).getDepartcd())){
			//トラフィック与営業部门查询，进行中的
				//计上flage进行中，担当falg被选中，（自己负责，担当，md的）	进行中代表不是终了的其他所有状态。jobend——flg=0
				jobInput.setDlvfalg("001");
				jobInput.setDdcd(jobInput.getUserID());
				List<JobList> joblist=	jobMapper.searchJobList(jobInput);
				if(joblist.size()>0){
					for(int i =0;i<joblist.size();i++) {
						//系统时间，根据时区把数据库中取出的值，重新赋值。
						if(!"".equals(joblist.get(i).getAdddate())) {
							
							List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
                            joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
                            joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
                            joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
                            
							joblist.get(i).setAdddate(commonMethedService.getTimeByZone(joblist.get(i).getAdddate(),jobInput.getCompanyID()));	
						}
						if(!"".equals(joblist.get(i).getSaleadddate())) {
							joblist.get(i).setSaleadddate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadddate(),jobInput.getCompanyID()));
						}
						if(!"".equals(joblist.get(i).getSaleadmitdate())) {
							joblist.get(i).setSaleadmitdate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadmitdate(),jobInput.getCompanyID()));	
						}
						//job担当者idlist，用于判断menu跳转权限
						List<String> reqList=commonMethedMapper.selJobuser("2", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setReqList(reqList);
						//job割当者idlist，用于判断menu跳转权限
						List<String> mdList=commonMethedMapper.selJobuser("3", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setMdList(mdList);
						joblist.get(i).setCldivList(cldivList);
					}
				}
				dropdown.put("joblist", joblist);
			}else if("001".equals(department.get(0).getDepartcd())||"002".equals(department.get(0).getDepartcd())) {
				//管理与财务部门初期没有查询
				List<JobList> joblist=new ArrayList<JobList>();
				dropdown.put("joblist", joblist);
				//iniselect代表初始是否有查询操作。0:有查询。1:无查询
				dropdown.put("initselect", "1");
				return dropdown;
			}else if("003".equals(department.get(0).getDepartcd())) {
				//トラフィックは割当未登録ＪＯＢを表示、
				jobInput.setAssign_flg("001");
				List<JobList> joblist=	jobMapper.searchJobList(jobInput);
				if(joblist.size()>0){
					for(int i =0;i<joblist.size();i++) {
						//系统时间，根据时区把数据库中取出的值，重新赋值。
						List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
                        joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
                        joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
                        joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
						if(!"".equals(joblist.get(i).getAdddate())) {
							joblist.get(i).setAdddate(commonMethedService.getTimeByZone(joblist.get(i).getAdddate(),jobInput.getCompanyID()));	
						}
						if(!"".equals(joblist.get(i).getSaleadddate())) {
							joblist.get(i).setSaleadddate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadddate(),jobInput.getCompanyID()));
						}
						if(!"".equals(joblist.get(i).getSaleadmitdate())) {
							joblist.get(i).setSaleadmitdate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadmitdate(),jobInput.getCompanyID()));	
						}
						//job担当者idlist，用于判断menu跳转权限
						List<String> reqList=commonMethedMapper.selJobuser("2", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setReqList(reqList);
						//job割当者idlist，用于判断menu跳转权限
						List<String> mdList=commonMethedMapper.selJobuser("3", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setMdList(mdList);
						joblist.get(i).setCldivList(cldivList);
					}
				}
				dropdown.put("joblist", joblist);	
			}
			//制作・ストプラ・メディアは割当担当の進行中ＪＯＢを表示、
			else {
				jobInput.setDlvfalg("001");
				jobInput.setMdcd(jobInput.getUserID());
				List<JobList> joblist=	jobMapper.searchJobList(jobInput);
				if(joblist.size()>0){
					
					for(int i =0;i<joblist.size();i++) {
						List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
                        joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
                        joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
                        joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
						//系统时间，根据时区把数据库中取出的值，重新赋值。
						if(!"".equals(joblist.get(i).getAdddate())) {
							joblist.get(i).setAdddate(commonMethedService.getTimeByZone(joblist.get(i).getAdddate(),jobInput.getCompanyID()));	
						}
						if(!"".equals(joblist.get(i).getSaleadddate())) {
							joblist.get(i).setSaleadddate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadddate(),jobInput.getCompanyID()));
						}
						if(!"".equals(joblist.get(i).getSaleadmitdate())) {
							joblist.get(i).setSaleadmitdate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadmitdate(),jobInput.getCompanyID()));	
						}
						//job担当者idlist，用于判断menu跳转权限
						List<String> reqList=commonMethedMapper.selJobuser("2", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setReqList(reqList);
						//job割当者idlist，用于判断menu跳转权限
						List<String> mdList=commonMethedMapper.selJobuser("3", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setMdList(mdList);
						joblist.get(i).setCldivList(cldivList);
					}
				}
				dropdown.put("joblist", joblist);
			}
		}else  {
			if(jobInput.getSearchFlg().equals("job")||jobInput.getSearchFlg().equals("jobdetailsearch"))
			{
				List<JobList> joblist=	jobMapper.searchJobList(jobInput);
				if(joblist.size()>0){
					for(int i =0;i<joblist.size();i++) {
						List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
	                    joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
	                    joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
	                    joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
						//系统时间，根据时区把数据库中取出的值，重新赋值。
						if(!"".equals(joblist.get(i).getAdddate())) {
							joblist.get(i).setAdddate(commonMethedService.getTimeByZone(joblist.get(i).getAdddate(),jobInput.getCompanyID()));	
						}
						if(!"".equals(joblist.get(i).getSaleadddate())) {
							joblist.get(i).setSaleadddate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadddate(),jobInput.getCompanyID()));
						}
						if(!"".equals(joblist.get(i).getSaleadmitdate())) {
							joblist.get(i).setSaleadmitdate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadmitdate(),jobInput.getCompanyID()));	
						}
						//job担当者idlist，用于判断menu跳转权限
						List<String> reqList=commonMethedMapper.selJobuser("2", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setReqList(reqList);
						//job割当者idlist，用于判断menu跳转权限
						List<String> mdList=commonMethedMapper.selJobuser("3", joblist.get(i).getJobcd(), jobInput.getCompanyID());
						joblist.get(i).setMdList(mdList);
						joblist.get(i).setCldivList(cldivList);
					}
				}
				dropdown.put("joblist", joblist);
				//iniselect代表初始是否有查询操作。0:有查询。1:无查询
				dropdown.put("initselect", "0");
			}
			if(jobInput.getSearchFlg().equals("top")){
				HashMap<String, Object> jobKeyWord = this.searchJobByKeyWord(jobInput);
				if(jobKeyWord.get("TooMuchData")!=null) {
					dropdown.put("TooMuchData", "0");
				}
				dropdown.put("initselect", "0");
				dropdown.put("joblist", jobKeyWord.get("joblist"));
				//iniselect代表初始是否有查询操作。0:有查询。1:无查询
				dropdown.put("initselect", "0");
				
			}
			if(jobInput.getSearchFlg().equals("keyword")){
				HashMap<String, Object> jobKeyWord = this.searchJobByKeyWord(jobInput);
				dropdown.put("joblist", jobKeyWord.get("joblist"));
				//iniselect代表初始是否有查询操作。0:有查询。1:无查询
				dropdown.put("initselect", "0");
			}
		}
		
		return dropdown;
	}

	public HashMap<String, Object>  searchJobByKeyWord(JobListInput jobInput) {
		jobInput.setDel_flg("0");
		//sql转移通配符
		String  oldkeyword =jobInput.getKeyword();
		jobInput.setKeyword(commonMethedService.changSqlInput(oldkeyword));
		//权限判断
		List<Role> role =commonMethedMapper.searchNodeListByUser(jobInput.getUserID(), jobInput.getCompanyID());
		boolean flg = false;
		if(role.size()>0) {
			for(int i=0;i<role.size();i++){
               if(role.get(i).getNodeID()==5) {
            	   jobInput.setAll("5");
            	   flg=true;
				}
				if(role.get(i).getNodeID()==6) {
					 jobInput.setDyqx("6");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
				if(role.get(i).getNodeID()==7) {
					 jobInput.setDdqx("7");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
				if(role.get(i).getNodeID()==8) {
					 jobInput.setGdqx("8");
					 jobInput.setDdqxcd(jobInput.getUserID());
					 flg=true;
				}
			}
		}
		 if(!flg) {
			  jobInput.setAll("no"); 
		  }
		List<Lable> lableall=jobLandMapper.selectLableList(jobInput.getUserID(), 0, Integer.valueOf(jobInput.getCompanyID()),1);
		
		if(jobInput.getKeyword()==null||jobInput.getKeyword().equals("")) {
			int  jobListCount = jobMapper.searchJobListCount(jobInput);
			if(jobListCount>2000) {
				HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
				jobMesAll.put("TooMuchData", 0);
				return jobMesAll;
			}
		}
		List<JobList> jobListCount = jobMapper.searchJobByKeyWordCount (jobInput);
		if(jobListCount.size()>2000) {
			HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
			jobMesAll.put("TooMuchData", 0);
			return jobMesAll;
		}
		List<JobList> joblist=	jobMapper.searchJobByKeyWord(jobInput);
		if(joblist.size()>2000) {
			HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
			jobMesAll.put("TooMuchData", 0);
			return jobMesAll;
		}
		List<String> cldivList=commonMethedMapper.selUsercldiv(jobInput.getUserID(), jobInput.getCompanyID());
		if(joblist.size()>0){
			for(int i =0;i<joblist.size();i++) {
				//系统时间，根据时区把数据库中取出的值，重新赋值。
				List<Cost> costInfo = commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), joblist.get(i).getJobcd());
                joblist.get(i).setCostTotalAmt(costInfo.get(0).getSumamt());
                joblist.get(i).setCostVatTotal(costInfo.get(0).getSumvat());
                joblist.get(i).setPayAmtSum(costInfo.get(0).getPaysum());
				if(!"".equals(joblist.get(i).getAdddate())) {
					joblist.get(i).setAdddate(commonMethedService.getTimeByZone(joblist.get(i).getAdddate(),jobInput.getCompanyID()));	
				}
				if(!"".equals(joblist.get(i).getSaleadddate())) {
					joblist.get(i).setSaleadddate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadddate(),jobInput.getCompanyID()));
				}
				if(!"".equals(joblist.get(i).getSaleadmitdate())) {
					joblist.get(i).setSaleadmitdate(commonMethedService.getTimeByZone(joblist.get(i).getSaleadmitdate(),jobInput.getCompanyID()));	
				}
				//job担当者idlist，用于判断menu跳转权限
				List<String> reqList=commonMethedMapper.selJobuser("2", joblist.get(i).getJobcd(), jobInput.getCompanyID());
				joblist.get(i).setReqList(reqList);
				//job割当者idlist，用于判断menu跳转权限
				List<String> mdList=commonMethedMapper.selJobuser("3", joblist.get(i).getJobcd(), jobInput.getCompanyID());
				joblist.get(i).setMdList(mdList);
				joblist.get(i).setCldivList(cldivList);
			}
		}
		
		List<JobList> jsflaglanguage =jobMapper.selectMstNameByCD("0020", jobInput.getCompanyID());//计上状态
		List<JobList> reqflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); 	//请求状态
		List<JobList> recflglanguage =jobMapper.selectMstNameByCD("0022", jobInput.getCompanyID()) ;	//入金状态
		List<JobList> invflglanguage =jobMapper.selectMstNameByCD("0021", jobInput.getCompanyID()); //发票状态
		List<JobList> assignflglanguage =jobMapper.selectMstNameByCD("0026", jobInput.getCompanyID()); //割当担当
		List<JobList> costfinishflglanguage =jobMapper.selectMstNameByCD("0027", jobInput.getCompanyID()); //原价完了
		HashMap<String, Object>  jobMesAll = new HashMap<String, Object>();
		jobMesAll.put("jsflaglanguage", jsflaglanguage);
		jobMesAll.put("reqflglanguage", reqflglanguage);
		jobMesAll.put("recflglanguage", recflglanguage);
		jobMesAll.put("invflglanguage", invflglanguage);
		jobMesAll.put("assignflglanguage", assignflglanguage);
		jobMesAll.put("costfinishflglanguage", costfinishflglanguage);
		jobMesAll.put("joblist", joblist);
		jobMesAll.put("lableall", lableall);
		jobMesAll.put("initselect", "0");
		return jobMesAll;
	}
	
	public Job searchJobInfoByJobNo(String jobNo,String companyID)
	{
		Job jobInFo = new Job();
		String costNo = "";
		String inputNo = "";
		CostInput inputParam = new CostInput();
		//job
		JobInfo jobcon =jobMapper.searchJobInfoByNo(jobNo,companyID);
		List<Cost>   newSumAmt = commonMethedMapper.getSumcost(Integer.valueOf(companyID), jobNo);
		//支付额
		jobcon.setTruePayAmtSum(newSumAmt.get(0).getPaysum());
		//成本额
		jobcon.setTrueCostTotalAmt(newSumAmt.get(0).getSumamt());
		//增值税
		jobcon.setTruecostVatTotal(newSumAmt.get(0).getSumvat());
		if(!"".equals(jobcon.getAdddate())) {
			//job登录时间
			jobcon.setAdddate(commonMethedService.getTimeByZone(jobcon.getAdddate(),companyID));		
		}
		if(!"".equals(jobcon.getUpddate())) {
			//job更新时间
			jobcon.setUpddate(commonMethedService.getTimeByZone(jobcon.getUpddate(),companyID));		
		}
		if(!"".equals(jobcon.getRecupddate())) {
			//入金登录时间
			jobcon.setRecupddate(commonMethedService.getTimeByZone(jobcon.getRecupddate(),companyID));		
		}
		
		if(jobcon.getItemvalue()!=null&&!jobcon.getItemvalue().equals("")) {
			String aaa = jobcon.getPlansaleforeignamt()+"";
			String  saleForeignAmt[]=aaa.split("\\.");
			String point = "";
			if(jobcon.getItemvalue().equals("0")) {
				String  saleForeignAmtN = saleForeignAmt[0];
				jobcon.setPlansaleamt(saleForeignAmtN);
			}else {
				String  costForeignAmtN = saleForeignAmt[1].substring(0,Integer.parseInt(jobcon.getItemvalue()));
				String costforeignamt = saleForeignAmt[0]+"."+costForeignAmtN;
				jobcon.setPlansaleamt(costforeignamt);
			}
		}
		jobInFo.setJobInfo(jobcon);
		//sellorder
		SaleInfo salecon =jobMapper.searchSellOrderByJobNo(jobNo, companyID);
		if(!"".equals(salecon.getSaleadddate())) {
			//壳上登录时间
			salecon.setSaleadddate(commonMethedService.getTimeByZone(salecon.getSaleadddate(),companyID));		
		}
		if(!"".equals(salecon.getSaleupddate())) {
			//壳上更新时间
			salecon.setSaleupddate(commonMethedService.getTimeByZone(salecon.getSaleupddate(),companyID));		
		}
		if(!"".equals(salecon.getSaleadmitdate())) {
			//壳上承认时间
			salecon.setSaleadmitdate(commonMethedService.getTimeByZone(salecon.getSaleadmitdate(),companyID));		
		}
		if(!"".equals(salecon.getSalecanceldate())) {
			//壳上承认取消时间
			salecon.setSaleadddate(commonMethedService.getTimeByZone(salecon.getSaleadddate(),companyID));		
		}
		if(!"".equals(salecon.getReqdate())) {
			//请求申请时间
			salecon.setReqdate(commonMethedService.getTimeByZone(salecon.getReqdate(),companyID));		
		}
		if(!"".equals(salecon.getInvoicedate())) {
			//发票发行
			salecon.setInvoicedate(commonMethedService.getTimeByZone(salecon.getInvoicedate(),companyID));		
		}
		if(salecon.getItmvalue()!=null&&!salecon.getItmvalue().equals("")) {
			String aaa = salecon.getSaleforeignamt()+"";
			String  saleForeignAmt[]=aaa.split("\\.");
			String point = "";
			if(salecon.getItmvalue().equals("0")) {
				String  saleForeignAmtN = saleForeignAmt[0];
				salecon.setSaleforeignamt(saleForeignAmtN);
			}else {
				String  costForeignAmtN = saleForeignAmt[1].substring(0,Integer.parseInt(salecon.getItmvalue()));
				String costforeignamt = saleForeignAmt[0]+"."+costForeignAmtN;
				salecon.setSaleforeignamt(costforeignamt);
			}
		}
		jobInFo.setSaleInfo(salecon);
		//costList,当前JOB编号下有多少条成本
		 List<Cost> CostNOList=jobMapper.searchOrderNosByJobNo(jobNo, companyID);
		//插叙成本条数
		List<Cost> costsum =commonMethedMapper.getSumcost(Integer.valueOf(companyID), jobNo);
		CostNOList.get(0).setCostsum(costsum.get(0).getCostsum());
		jobInFo.setCostList(CostNOList);
		//当前JOB编号下绑定的lableList
		jobInFo.setJobLableList(jobLandMapper.selectJobLable(jobNo,Integer.parseInt(companyID)));
		//获取第一条外发成本编号
		costNo = jobInFo.getCostList().get(0).getCost_no();
		//第一条外发成本信息
		List<Cost> costList=costMapper.selectCostInfo(jobNo,costNo,companyID);
		if(costList.size()>0) {
			jobInFo.setCostObject(costList.get(0));	
			//第一条外发成本对应的支付登陆编号
			inputNo = jobInFo.getCostObject().getInput_no();
		}
		inputParam.setJob_cd(jobNo);
		inputParam.setCompany_cd(companyID);
		inputParam.setCost_no(costNo);
		inputParam.setInput_no(inputNo);
		//第一条外发对应的支付信息
		List<Pay> paylist=payMapper.selectPayInfo(inputParam);
		if(paylist.size()>0) {
			jobInFo.setPayObject(paylist.get(0));	
		}
		//外发对应的lableList，有疑问，取支付还是取外发
		//jobInFo.setCostLableList(costLableList);
		//第一条支付的凭证list
		jobInFo.setPayProoList(paytrnMapper.rooftrnquery(companyID,inputNo));
		jobInFo.setJobUserList(jobLandMapper.selectJobUser(inputParam.getJob_cd(),Integer.parseInt(companyID) ));
		String taxFormatFlg = commonmstdMapper.getforeignVatFormatFlg("0052", companyID, "003");
		String foreignFormatFlg =commonmstdMapper.getforeignVatFormatFlg("0051",companyID,"001");
		jobInFo.getJobInfo().setTaxFormatFlg(taxFormatFlg);
		jobInFo.getJobInfo().setForeignFormatFlg(foreignFormatFlg);
		//预计与实际差额
		if(!"".equals(jobInFo.getJobInfo().getSaleno())&&!"".equals(jobNo)) {
			double saledifference=Math.abs(jobInFo.getJobInfo().getSaleamt().subtract(jobInFo.getJobInfo().getPlansale()).doubleValue());//壳上金额实际-预计壳上金额
			double vatamtdifference=Math.abs(jobInFo.getSaleInfo().getSalevatamt().subtract(jobInFo.getJobInfo().getPlanSaleVat()).doubleValue());//壳上增值税实际-预计壳上增值税
			double reqdifference=Math.abs(jobInFo.getSaleInfo().getReqamt().subtract(jobInFo.getJobInfo().getPlanreqamt()).doubleValue());//壳上增值税实际-预计壳上增值税
			jobInFo.getJobInfo().setSaledifference(saledifference);
			jobInFo.getJobInfo().setVatamtdifference(vatamtdifference);
			jobInFo.getJobInfo().setReqdifference(reqdifference);	
		}
		// 查询公司跳过
		Skip skip = commonMethedMapper.selectSkipByCd( Integer.parseInt(companyID));
		jobInFo.setSkip(skip);
		List<Cost> Foreign= costMapper.selCostForeign(Integer.valueOf(companyID),null,"0050");
		jobInFo.setForeign(Foreign);
		return jobInFo;
	}
	
	//查詢job詳細中的，cost信息
		public Map<String, Object> searchCostInfo(CostInput inputParam) {
			HashMap<String, Object> outSource = new HashMap<String, Object>();
			inputParam.setCompany_cd(inputParam.getCompanyID());
			String inputno = null;
			List<Cost> costList=costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID());
			if(costList.size()>0){
				if(!"".equals(costList.get(0).getCostadddate())) {
					//外发登录时间
					costList.get(0).setCostadddate(commonMethedService.getTimeByZone(costList.get(0).getCostadddate(),inputParam.getCompanyID()));		
				}
				if(!"".equals(costList.get(0).getCostupdate())) {
					//外发更新时间
					costList.get(0).setCostupdate(commonMethedService.getTimeByZone(costList.get(0).getCostupdate(),inputParam.getCompanyID()));		
				}
				if(!"".equals(costList.get(0).getCostpdfdate())) {
					//外发更新时间
					costList.get(0).setCostpdfdate(commonMethedService.getTimeByZone(costList.get(0).getCostpdfdate(),inputParam.getCompanyID()));		
				}
			}
			outSource.put("costList", costList);
			//cost与lable关系
			if(costList.size()>0) {
				//第一条外发成本对应的支付登陆编号
				 inputno = costList.get(0).getInputno();
				 inputParam.setInput_no(inputno); 
			}
			List<JobLableTrn> costlable= costMapper.selectCostLable(inputParam.getCost_no(),inputParam.getCompanyID(),0);
			outSource.put("costlable", costlable);
		/*	List<JobLableTrn> paylable= costMapper.selectCostLable(inputno,inputParam.getCompanyID());
			outSource.put("paylable", paylable);*/
			List<Pay> pay  =payMapper.selectPayInfo(inputParam);
		  if(pay.size()>0&&costList.size()>0){
			//预计与实际差额
				double payamtdifference=Math.abs(costList.get(0).getCostrmb().subtract(pay.get(0).getPayamt()).doubleValue());//发注原价（税拔）- 支付原价（税拔）
				double payvatamttdifference=Math.abs(costList.get(0).getCostvatamt().subtract(pay.get(0).getPayvatamt()).doubleValue());//壳上增值税实际-预计壳上增值税
				double payrmbdifference=Math.abs(costList.get(0).getCostpayamt().subtract(pay.get(0).getPayrmb()).doubleValue());//壳上增值税实际-预计壳上增值税
				pay.get(0).setPayamtdifference(payamtdifference);
				pay.get(0).setPayvatamttdifference(payvatamttdifference);
				pay.get(0).setPayrmbdifference(payrmbdifference);
				
		  }
		  if(pay.size()>0){
			  if(!"".equals(pay.get(0).getAdddate())) {
					//支付登录时间
				   pay.get(0).setAdddate(commonMethedService.getTimeByZone(pay.get(0).getAdddate(),inputParam.getCompanyID()));		
			  }
			  if(!"".equals(pay.get(0).getUpdate())) {
					//支付更新时间
				   pay.get(0).setUpdate(commonMethedService.getTimeByZone(pay.get(0).getUpdate(),inputParam.getCompanyID()));		
			  }
			  if(!"".equals(pay.get(0).getPayreqdate())) {
					//支付申请时间
				   pay.get(0).setPayreqdate(commonMethedService.getTimeByZone(pay.get(0).getPayreqdate(),inputParam.getCompanyID()));		
			  }
			  if(!"".equals(pay.get(0).getConfirmdate())) {
					//支付承认时间
				   pay.get(0).setConfirmdate(commonMethedService.getTimeByZone(pay.get(0).getConfirmdate(),inputParam.getCompanyID()));		
			  }
			  if(!"".equals(pay.get(0).getPayconfirmcanceldate())) {
					//支付承认取消时间
				   pay.get(0).setPayconfirmcanceldate(commonMethedService.getTimeByZone(pay.get(0).getPayconfirmcanceldate(),inputParam.getCompanyID()));		
			  }
			  if(!"".equals(pay.get(0).getPaycanceldate())) {
					//支付处理取消时间
				   pay.get(0).setPaycanceldate(commonMethedService.getTimeByZone(pay.get(0).getPaycanceldate(),inputParam.getCompanyID()));		
			  }
			  if(!"".equals(pay.get(0).getPayupdate())) {
					//支付处理更新时间
				   pay.get(0).setPayupdate(commonMethedService.getTimeByZone(pay.get(0).getPayupdate(),inputParam.getCompanyID()));		
			  }
		  }
		  
		  outSource.put("pay", pay);
			//第一条支付的凭证list
			List<Prooftrn> prooftrn = paytrnMapper.rooftrnquery(inputParam.getCompanyID(),inputno);
			outSource.put("prooftrn", prooftrn);
			List<Cost> Foreign= costMapper.selCostForeign(Integer.valueOf(inputParam.getCompanyID()),null,"0050");
			outSource.put("Foreign", Foreign);
			return outSource;
		}

/*	public int saleCancelTx(JobListInput inputParam) {
		int num=0;
		String viewLockflg=inputParam.getLockflg();
		String DBlockflg =jobMapper.selectLOCKFLG(inputParam);
		if(viewLockflg.equals(DBlockflg)) {
			num =jobMapper.saleCancel(inputParam);
			jobMapper.deleteratehistory(inputParam.getJob_cd(),inputParam.getCompanyID());	
		}else {
			num=2;
		}
		return num;
	}*/

	public int saleAdmitCancelTx(JobListInput inputParam) {
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		inputParam.setSale_cancel_date(dateformat.format(date));
		return jobMapper.saleAdmitCancel(inputParam);
	}
	
	//原价完了
	public int costFinish(JobListInput inputParam) {
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		inputParam.setCost_finish_date(dateformat.format(date));
		List<JobList> input=inputParam.getJoblistinput();
		for(int i=0 ;i<input.size();i++) {
			inputParam.setJob_cd(input.get(i).getJobcd());
			jobMapper.costFinish(inputParam);
		}
		return 0;
	}
	
	//原价完了取消
	public int costFinishCancel(JobListInput inputParam) {
		List<JobList> input=inputParam.getJoblistinput();
		for(int i=0 ;i<input.size();i++) {
			inputParam.setJob_cd(input.get(i).getJobcd());
			jobMapper.costFinishCancel(inputParam);
		}
		return 0;
	}

	
	public Map<String,Object> getJobDetailNoList(JobListInput jobInput){
		 String costsum="";
		 HashMap< String, Object>  costNolist = new HashMap<String, Object>();
		 List<Cost> NOList=jobMapper.searchOrderNosByJobNo(jobInput.getJob_cd(), jobInput.getCompanyID());
		 List<Cost> sellplansalelist=jobMapper.searchOrderNosBysellplan(jobInput.getJob_cd(), jobInput.getCompanyID());
		 List<Cost> sumcost=commonMethedMapper.getSumcost(Integer.valueOf(jobInput.getCompanyID()), jobInput.getJob_cd());
		 if(sumcost!=null&&sumcost.size()>0) {//成本条数，数据大于0时，取出第一条的的原价条数字段
			  costsum= sumcost.get(0).getCostnum(); 
		 }else {
			 costsum="0";//等于空或者小于0时。
		 }
		 List<Cost> costlist = jobMapper.searchOederNosByCostIsHave(jobInput.getJob_cd(), jobInput.getCompanyID());
		 NOList.get(0).setCost_ishave(costlist.get(0).getCost_ishave());
		 if(sellplansalelist.get(0)!=null) {
			 NOList.get(0).setPlancostishave(sellplansalelist.get(0).getPlancostishave());
		 }else {
			 NOList.get(0).setPlancostishave(null);
		 }
		 
		 costNolist.put("payStatus", jobLandMapper.selectSaleCode(Integer.valueOf(jobInput.getCompanyID()), null, "0015"));
		 costNolist.put("NOList", NOList);
		 costNolist.put("sumcost", costsum);
		return costNolist;
	}
	
	
		/**   
		 * <p>Title: selColumns</p>   
		 * <p>Description:查询动态列 </p>   
		 * @param company_cd
		 * @param level
		 * @param usercd
		 * @param ison
		 * @return   
		 * @see com.kaiwait.service.jczh.JobService#selColumns(int, int, int, int)
		 * @author "马有翼"
		 * @date:   2018年9月25日 下午3:23:40 
		 */ 
		public  List<List<Columns>> selColumns(int company_cd,int level,int usercd,int ison){
			 List<List<Columns>> colList = new ArrayList<List<Columns>>();
			 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME"));
			 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME_EN"));
			 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME_JP"));
			 colList.add(commonMethedMapper.selColumns(company_cd,level,usercd,ison,"COLUMN_SHOW_NAME_HK"));
			 //分别查处税金2在共同表中的四国语言。赋值于list中，循环。
			 List< JobList> tax2List = new ArrayList<JobList>();
			 tax2List.add(commonMethedMapper.seltaxName("itmname","0063","002", company_cd));//税金2
			 tax2List.add(commonMethedMapper.seltaxName("itemname_en","0063","002", company_cd));//税金2
			 tax2List.add(commonMethedMapper.seltaxName("itemname_jp","0063","002", company_cd));//税金2
			 tax2List.add(commonMethedMapper.seltaxName("itemname_hk","0063","002", company_cd));//税金2
			 for(int i =0 ;i<tax2List.size();i++) {//循环税金2的四种语言
				for(int k =0; k<colList.get(i).size();k++) {//循环查询处的表头
					if ("tax2".equals(colList.get(i).get(k).getField())) {//当表头时tax2时，把commonst中的语言插入到表头列表中
						String tax2 [] = tax2List.get(i).getItmname().split("\\(");
						colList.get(i).get(k).setTitle(tax2[0]);	
			        }
				} 
			 }
			 
			 //税金3同上
			 List< JobList> tax3List = new ArrayList<JobList>();
			 tax3List.add(commonMethedMapper.seltaxName("itmname","0063","003", company_cd));//税金2
			 tax3List.add(commonMethedMapper.seltaxName("itemname_en","0063","003", company_cd));//税金2
			 tax3List.add(commonMethedMapper.seltaxName("itemname_jp","0063","003", company_cd));//税金2
			 tax3List.add(commonMethedMapper.seltaxName("itemname_hk","0063","003", company_cd));//税金2
			 for(int i =0 ;i<tax3List.size();i++) {
					for(int k =0; k<colList.get(i).size();k++) {
						if ("tax3".equals(colList.get(i).get(k).getField())) {
							String tax3 [] = tax3List.get(i).getItmname().split("\\(");
							colList.get(i).get(k).setTitle(tax3[0]);	
				        }
					} 
				 }
			return colList;
		}
		/**   
		 * <p>Title: replaceString</p>   
		 * <p>Description:替换字符串中某字符为指定字符 </p>   
		 * @param oldString 旧字符串
		 * @param replaceString 被替换的字符串
		 * @param toString 替换成的字符
		 * @return  newString 
		 * @author "马有翼"
		 * @date:   2018年9月25日 下午3:23:40 
		 */ 
		public  String replaceString(String oldString,List<String> replaceString,String toString){
			String newString=oldString;
			for(int i=0 ;i<replaceString.size();i++){
			 newString=	newString.replace(replaceString.get(i),toString);
			}
			return newString;
			
		}
		public String createInvoiceNo(JobListInput inputParam)
		{
			int jobInvoiceFlg = commonmstdMapper.getJobInvoiceNoFlg(inputParam.getCompanyID());
			if(inputParam.getSearchFlg().equals("checkInvoiceStatus"))
			{
				if(jobInvoiceFlg == 1)
				{
					String invoStatus = jobMapper.checkInvoiceIsShow(inputParam.getCompanyID(),inputParam.getJob_cd());
					if(StringUtil.isNotBlank(invoStatus))
					{
						return "notShow";
					}else {
						return "isShow";
					}
				}else {
					return "isShow";
				}
			}
			jobMapper.delInvoiceNosWithJob(inputParam.getCompanyID(),inputParam.getJob_cd());
			jobMapper.deljournalDataWithJob(inputParam.getCompanyID(),inputParam.getJob_cd());
			boolean flg = true;
			//String regEx = "(\\d{1,15})";
			String regEx = "[0-9A-Za-z]{1,20}$";
			Pattern pattern = Pattern.compile(regEx);
			//sale_no在这里表达前台填写的发票编号
			if(!inputParam.getSearchFlg().equals("cancelInvoice"))
			{
				if(!StringUtil.isEmpty(inputParam.getSale_no()))
				{
					String[] ins = inputParam.getSale_no().split(",");
					if(ins.length < 1)
					{
						return "VALIDATE_FORMAT_ERROR";
					}else {
						for(int i = 0;i < ins.length;i++)
						{
							Matcher matcher = pattern.matcher(ins[i]);
							if(!matcher.matches() || StringUtil.isEmpty(ins[i]))
							{
								flg = false;
								break;
							}else {
								jobMapper.addInvoiceWithJob(inputParam.getCompanyID(),inputParam.getJob_cd(),ins[i],inputParam.getUserID(),DateUtil.getDateForNow(DateUtil.dateTimeFormat));
							}
						}
						if(flg)
						{
							accountEntriesService.createSaleAccountsData(inputParam.getCompanyID(),inputParam.getJob_cd(),inputParam.getUserID(),"createInvoiceNo");
						}else {
							return "VALIDATE_FORMAT_ERROR";
						}
					}
				}else {
					
					if(jobInvoiceFlg == 0)
					{
						String InvoiceNo = jobMapper.selectInvoiceNo(inputParam.getCompanyID(),inputParam.getJob_cd());
						if(!InvoiceNo.equals("")) {
							return InvoiceNo;
						}
						else {
							return "";
						}
					}else {
						return "VALIDATE_FORMAT_ERROR";
					}
				}
			}
			String InvoiceNo = jobMapper.selectInvoiceNo(inputParam.getCompanyID(),inputParam.getJob_cd());
			if(!InvoiceNo.equals("")) {
				return InvoiceNo;
			}
			else {
				return "";
			}
			
		}
}
