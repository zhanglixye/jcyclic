package com.kaiwait.service.jczh.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobInfo;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Lendtrn;
import com.kaiwait.bean.jczh.entity.LendtrnApprovalBeen;
import com.kaiwait.bean.jczh.entity.LendtrnConfirmBeen;
import com.kaiwait.bean.jczh.entity.OrderHistorytrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.LendtrnInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.LendtrnMapper;
import com.kaiwait.mappers.jczh.TimesheetMapper;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.service.jczh.AccountEntriesService;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.LendtrnService;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LendtrnServiceImpl implements LendtrnService{

	@Resource
	private LendtrnMapper LendtrnMapper;
	@Resource
	private CommonmstMapper commonMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private JobLandMapper joblandmapper;
	@Resource
	private CostMapper costMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private TimesheetMapper TimesheetMapper;
	@Resource
	private JobMapper jobmapper;
	@Resource
	private AccountEntriesService accountEntriesService;
	
	public List<Lendtrn> searchLendtrnList(LendtrnInput inputParam)
	{
		@SuppressWarnings("unused")
		LendtrnInput Lendtrn = new LendtrnInput();
		return LendtrnMapper.searchLendtrnList(inputParam.getUserID(),inputParam.getCompanyID());
	}
	
	public LendtrnInput LendtrnQueryList(LendtrnInput inputParam)
	{
		LendtrnInput Lendtrn = new LendtrnInput();
		String companyID = inputParam.getCompanyID();
		Lendtrn len =LendtrnMapper.LendtrnDate(inputParam.getLendtrn());
		if(len==null) {
			return null;
		}
			if(!"".equals(len.getAdddate())) {
				len.setAdddate(commonMethedService.getTimeByZone(len.getAdddate(),companyID));		
			}
			if(!"".equals(len.getUpdate())) {
				len.setUpdate(commonMethedService.getTimeByZone(len.getUpdate(),companyID));		
			}
			if(!"".equals(len.getConfirmdate())) {
				len.setConfirmdate(commonMethedService.getTimeByZone(len.getConfirmdate(),companyID));		
			}
			if(!"".equals(len.getCancelconfirmdate())) {
				len.setCancelconfirmdate(commonMethedService.getTimeByZone(len.getCancelconfirmdate(),companyID));		
			}
			
		Lendtrn.setLendtrnDate(len);
		List<Lendtrn> lendtrnlist = LendtrnMapper.Lendtrnquery(inputParam.getLendtrn());
		if(lendtrnlist.size()<1) {
			return null;
		}
		if(!"".equals(lendtrnlist.get(0).getAdddate())) {
			lendtrnlist.get(0).setAdddate(commonMethedService.getTimeByZone(lendtrnlist.get(0).getAdddate(),companyID));		
		}
		if(!"".equals(lendtrnlist.get(0).getUpdate())) {
			lendtrnlist.get(0).setUpdate(commonMethedService.getTimeByZone(lendtrnlist.get(0).getUpdate(),companyID));		
		}
		Lendtrn.setLendtrnList(lendtrnlist);
		Lendtrn.setList_rooftrn(LendtrnMapper.rooftrnquery(companyID,inputParam.getLendtrn().getInput_no()));
		Lendtrn.setItemList(commonMapper.selectMstNameByCD("9001",Integer.valueOf(companyID)));
		Lendtrn.setForeignFormatFlg(commonMapper.getforeignVatFormatFlg("0051",companyID,"001"));
		Lendtrn.setSaleVatFormatFlg(commonMapper.getforeignVatFormatFlg("0052",companyID,"002"));
		//Lendtrn.setCost_foreign_type(costMapper.selCostForeignInfo("0050",inputParam.getCompanyID()));
		List<Cost> ss = costMapper.selCostForeigntwo(Integer.valueOf(companyID),null,"0050");
		Lendtrn.setCost_foreign_type(ss);
		Lendtrn.setPlan_cost_foreign_code(costMapper.selCostForeignInfo("0050",inputParam.getCompanyID()));
		Lendtrn.setList_lable(joblandmapper.selectLableList(inputParam.getUserID(),1,Integer.valueOf(companyID),1));
		Lendtrn.setList_comst(joblandmapper.selectSaleCode(Integer.valueOf(companyID),null,"0050"));
		List<JobLableTrn> costlable = costMapper.selectCostLable(lendtrnlist.get(0).getInput_no(),companyID,2);
		Lendtrn.setLableList(costlable);
		Lendtrn.setTimesheetuser(TimesheetMapper.timesheetuser(companyID, inputParam.getUserID()));
		Lendtrn.setCommonmst(commonMapper.getforeignLen(companyID));
		return Lendtrn;
	}
	
	public List<Prooftrn> getProoftrnList(String companycd,String inputno)
	{
		return LendtrnMapper.rooftrnquery(companycd,inputno);

	}
	public String QueryJobstatus(String job_cd,String companyid)
	{
		return LendtrnMapper.QueryJobstatus(job_cd,companyid);

	}
	public int LendtrnApproval(LendtrnInput inputParam)
	{
		inputParam.getLendtrn().setUpdate(DateUtil.getNowTime());
		String jobcd = inputParam.getLendtrn().getJob_cd();
		String inputno = inputParam.getLendtrn().getInput_no();
		String companyID = inputParam.getCompanyID();
		String status = LendtrnMapper.getstatus(jobcd,inputno,companyID);
		if(!status.equals("0")&&!status.equals("2")){
			return 0;
		}
		int locknum = inputParam.getLendtrn().getLock_flg();
		int locknumnow=LendtrnMapper.queryLock(jobcd,companyID,inputno);
		if(locknumnow>locknum){
			 return -1; 
		 }
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getLendtrn().getOld_input_no(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getLendtrn().getOld_input_no());
					jltrn.get(i).setLablelevel(2);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
					
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		//王哥报表用
			OrderHistorytrn orderhistorytrn = new OrderHistorytrn();
			String month = commonMethedService.getSystemDate(Integer.valueOf(companyID));
			month =month.substring(0, 4)+month.substring(5, 7);
			orderhistorytrn.setCheckMonth(month);
			orderhistorytrn.setCompanycd(inputParam.getCompanyID());
			orderhistorytrn.setCostAmt(inputParam.getLendtrn().getLend_amt());
			orderhistorytrn.setCostVat(inputParam.getLendtrn().getLend_vat_amt());
			orderhistorytrn.setJobNo(jobcd);
			String newInputNo = LendtrnMapper.getnewInputNo(jobcd,inputno,companyID);
			orderhistorytrn.setOrderNo(newInputNo);
			orderhistorytrn.setOrderStatus("1");
			orderhistorytrn.setOrderType("1");
			orderhistorytrn.setPayAmt(inputParam.getLendtrn().getLend_pay_amt());
			orderhistorytrn.setCostStatus(status);
            orderhistorytrn.setAdddate(DateUtil.getNowTime());
            orderhistorytrn.setAddusercd(inputParam.getUserID());
			LendtrnMapper.addOrder(orderhistorytrn);
			LendtrnMapper.updateLock(jobcd,companyID,inputno);
			
			accountEntriesService.createLendAccountsData(jobcd, newInputNo, companyID, inputParam.getUserID(), month);
			return LendtrnMapper.lendtrnapproval(inputParam.getLendtrn());
	}
	public String LendtrnAddTx(LendtrnInput inputParam)
	{
		@SuppressWarnings("unused")
		Calendar rightNow = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt23 = sdf.format(date);
		String nowDate = commonMethedService.getTimeByZone(dt23,inputParam.getCompanyID()).substring(0,10);
		try {
			SimpleDateFormat Time=new SimpleDateFormat("yyyy-MM-dd");
		     Date dt1=Time.parse(nowDate);//现在时间
	   	     String lenddate= inputParam.getLendtrn().getLend_date();
	   	     if(!isDate(lenddate)) {
		    	 return "-2"; 
		     }
	   	    Date dt2 = Time.parse(lenddate);//使用日
		   	//使用日必须早于现在时间
	        if (dt1.getTime() < dt2.getTime()) {
	            return "-2";
	        }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
		inputParam.getLendtrn().setAdddate(DateUtil.getNowTime());
		inputParam.getLendtrn().setUpdate(DateUtil.getNowTime());
		JobInfo va =jobmapper.searchJobInfoByNo(inputParam.getJob_cd(), inputParam.getCompanyID());
		int costfinshiflg=va.getCostFinshFlg();
		if(costfinshiflg==1) {
			return "0";
		}
		//根据job编号查询是否存在此条job
		JobListInput jobInput = new JobListInput();
		jobInput.setJob_cd(inputParam.getJob_cd());
		jobInput.setCompanyID(inputParam.getCompanyID());
		List<JobList> joblist=jobmapper.selectjobStatus(jobInput);
		//没有job不允许登录支付信息
		if(joblist.size()<1) {
			return "0";
		}
		int roof = inputParam.getList_rooftrn().size();
		String inputno = inputParam.getLendtrn().getInput_no();
		if(roof>0) {
			LendtrnMapper.insertProoftrn(inputParam.getList_rooftrn(),inputParam.getUserID(),inputParam.getCompanyID(),inputParam.getLendtrn().getInput_no(),DateUtil.getNowTime());
		}
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getLendtrn().getInput_no(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getLendtrn().getInput_no());
					jltrn.get(i).setLablelevel(2);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 LendtrnMapper.insertlendtrn(inputParam.getLendtrn());
		 return inputno;

	}
	public int LendtrnDeleteTx(LendtrnInput inputParam)
	{
		String companyID = inputParam.getCompanyID();
		String jobcd = inputParam.getLendtrn().getJob_cd();
		String inputno = inputParam.getLendtrn().getInput_no();
		String usercd = inputParam.getUserID();
		int locknum = inputParam.getLendtrn().getLock_flg();
		int locknumnow=LendtrnMapper.queryLock(jobcd,companyID,inputno);
		if(locknumnow>locknum){
			 return -1; 
		 }
		//判断是否计上，未计上不允许删除
		String status = LendtrnMapper.QueryJobstatus(jobcd,companyID);
		if(status!=null) {
			 return -2; 
		}
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getLendtrn().getInput_no(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getLendtrn().getInput_no());
					jltrn.get(i).setLablelevel(2);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 LendtrnMapper.updateLock(jobcd,companyID,inputno);
		return LendtrnMapper.lendtrndelete(jobcd,inputno,companyID,DateUtil.getNowTime(),usercd);

	}
	public String LendtrnUpdateTx(LendtrnInput inputParam)
	{
		 @SuppressWarnings("unused")
		 Calendar rightNow = Calendar.getInstance();
		 Date date = new Date();
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String dt23 = sdf.format(date);
		 String nowDate = commonMethedService.getTimeByZone(dt23,inputParam.getCompanyID()).substring(0,10);
			try {
				 SimpleDateFormat Time=new SimpleDateFormat("yyyy-MM-dd");
				 Date dt1=Time.parse(nowDate);//现在时间
	    	     String lenddate= inputParam.getLendtrn().getLend_date();
    	     if(!isDate(lenddate)) {
    	    	 return "-2"; 
    	     }
    	     Date dt2 = Time.parse(lenddate);//使用日
    	     //使用日必须早于现在时间
             if (dt1.getTime() < dt2.getTime()) {
                 return "-2";
             }
         } catch (Exception exception) {
             exception.printStackTrace();
         }
		inputParam.getLendtrn().setUpdate(DateUtil.getNowTime());
		LendtrnMapper.deleteProoftrn(inputParam.getCompanyID(),inputParam.getLendtrn().getOld_input_no());
		int locknum = inputParam.getLendtrn().getLock_flg();
		int locknumnow=LendtrnMapper.queryLock(inputParam.getLendtrn().getJob_cd(),inputParam.getCompanyID(),inputParam.getLendtrn().getInput_no());
		if(locknumnow>locknum){
			 return "-1"; 
		 }
		int roof = inputParam.getList_rooftrn().size();
		if(roof>0) {
			LendtrnMapper.insertProoftrn(inputParam.getList_rooftrn(),inputParam.getUserID(),inputParam.getCompanyID(),inputParam.getLendtrn().getNew_input_no(),DateUtil.getNowTime());
		}
		List<JobLableTrn> jltrn = inputParam.getLableList();
		String oldInputNo = jobLandMapper.selectOldInputNo(inputParam.getCompanyID(),inputParam.getInput_no());
		jobLandMapper.deleteByJobCd(null, oldInputNo, Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(oldInputNo);
					jltrn.get(i).setLablelevel(2);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 String lendusedate = inputParam.getLendtrn().getLendusedate();
		 if("".equals(lendusedate)||lendusedate==null) {
			 lendusedate=null;
		 }
		 if(inputParam.getLendtrn().getLend_foreign_type()==null) {
             inputParam.getLendtrn().setLend_foreign_type("");
		 }
		 inputParam.getLendtrn().setLendusedate(lendusedate);
		 LendtrnMapper.updateLock(inputParam.getLendtrn().getJob_cd(),inputParam.getCompanyID(),inputParam.getLendtrn().getInput_no());
		 LendtrnMapper.updateLendtrn(inputParam.getLendtrn());
		 return inputParam.getLendtrn().getNew_input_no();

	}
	//@Override
	public LendtrnInput initLendtrnList(LendtrnInput Lendtrn){
		LendtrnInput Lendtrninput = new LendtrnInput();
		String companyID = Lendtrn.getCompanyID();
		//经费科目，下拉选框用
		Lendtrninput.setItemList(commonMapper.selectMstNameByCD("9001",Integer.valueOf(companyID)));
		Lendtrninput.setList_lable(joblandmapper.selectLableList(Lendtrn.getUserID(),1,Integer.valueOf(companyID),1));
		Lendtrninput.setCost_foreign_type(costMapper.selCostForeigntwo(Integer.valueOf(companyID),null,"0050"));
		Lendtrn.setList_comst(joblandmapper.selectSaleCode(Integer.valueOf(companyID),null,"0050"));
		return Lendtrninput;
	}

	public int cancelLendTrnTx(LendtrnInput Lendtrn) {
		// TODO Auto-generated method stub
		String jobcd = Lendtrn.getLendtrn().getJob_cd();
		String inputno = Lendtrn.getLendtrn().getInput_no();
		String companyID = Lendtrn.getCompanyID();
		String status = LendtrnMapper.getstatus(jobcd,inputno,companyID);
		if(!status.equals("1")){
			return 0;
		}
		int locknum = Lendtrn.getLendtrn().getLock_flg();
		int locknumnow=LendtrnMapper.queryLock(jobcd,companyID,inputno);
		if(locknumnow>locknum){
			 return -1; 
		 }
		Lendtrn lend = Lendtrn.getLendtrn();
		lend.setCompany_cd(Lendtrn.getCompanyID());
		lend.setCancelconfirmdate(DateUtil.getNowTime());
		lend.setCancelconfirmemp(Lendtrn.getUserID());
		List<JobLableTrn> jltrn = Lendtrn.getLableList();
		jobLandMapper.deleteByJobCd(null, Lendtrn.getLendtrn().getOld_input_no(), Integer.valueOf(companyID),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(Lendtrn.getLendtrn().getOld_input_no());
					jltrn.get(i).setLablelevel(2);
					jltrn.get(i).setCompanycd(Integer.valueOf(Lendtrn.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(Lendtrn.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(Lendtrn.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		//王哥报表用
		OrderHistorytrn orderhistorytrn = new OrderHistorytrn();
		String month = commonMethedService.getSystemDate(Integer.valueOf(companyID));
		month =month.substring(0, 4)+month.substring(5, 7);
		orderhistorytrn.setCheckMonth(month);
		orderhistorytrn.setCompanycd(companyID);
		orderhistorytrn.setJobNo(jobcd);
		String newInputNo = LendtrnMapper.getnewInputNo(jobcd,inputno,companyID);
		orderhistorytrn.setOrderNo(newInputNo);
		LendtrnMapper.DeleteOrder(orderhistorytrn);
		LendtrnMapper.updateLock(jobcd,companyID,inputno);
		//承认取消时，删除会计分录数据
		accountEntriesService.dropAccountDatasByCancel(jobcd, newInputNo, companyID);
		int num = LendtrnMapper.cancelLendTrn(lend);
		return num;
	}
	
	@Override
	//インプット確認票（立替）
	public String LendtrnConfirmPDF(OutPutInput jobLand) throws JRException {
		LendtrnConfirmBeen jobLandOut = LendtrnMapper.LendtrnConfirmPDF(jobLand.getJobNo(),Integer.valueOf(jobLand.getCompanyID()),jobLand.getInputNo());
		Map<Object,Object>  hbcode =null;
		if(jobLandOut.getLendforeigntype()!=null&&!jobLandOut.getLendforeigntype().equals("")) {
			hbcode= commonMethedMapper.selectcodetype(jobLandOut.getLendforeigntype(),jobLand.getCompanyID());
		}
		//登陆日加时区
		String addDate = commonMethedService.getTimeByZone(jobLandOut.getAddDate(),jobLand.getCompanyID()).substring(0,10);
		jobLandOut.setAddDate(addDate);
		jobLandOut.setInputNo(jobLandOut.getNewNo());
		String language = jobLand.getLangTyp();
		String moneyType = jobLandOut.getMoneyType();
		String currencycode = jobLandOut.getCurrencycode();
		Locale local = JasperOutPdfUtil.getLocalByLangT(language);
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		if("1".equals(jobLandOut.getIsHave())) {
			jobLandOut.setIsHave(bundle.getString("haveTax"));//税入
		}else {
			jobLandOut.setIsHave(bundle.getString("notHaveTax"));//税拔
		}
		currencycode = jobLandOut.getLocalMoney();
		if(hbcode!=null) {
			jobLandOut.setForeigntype(hbcode.get("ITMNAME").toString());
		}
		switch(language){
			case "jp":
				//currencycode=jobLandOut.getCurrencycodejp();
				moneyType = jobLandOut.getMoneyTypejp();
				currencycode = jobLandOut.getLocalMoneyJp();
				if(hbcode!=null) {
					jobLandOut.setForeigntype(hbcode.get("ITEMNAME_JP").toString());
				}
			 
				break;
			case "en":
				//currencycode=jobLandOut.getCurrencycodeen();
				moneyType = jobLandOut.getMoneyTypeen();
				currencycode = jobLandOut.getLocalMoneyEn();
				if(hbcode!=null) {
					jobLandOut.setForeigntype(hbcode.get("ITEMNAME_EN").toString());
				}
				break;
			case "hk":
				//currencycode=jobLandOut.getCurrencycodehk();
				moneyType = jobLandOut.getMoneyTypehk();
				currencycode = jobLandOut.getLocalMoneyHk();
				if(hbcode!=null) {
					jobLandOut.setForeigntype(hbcode.get("ITEMNAME_HK").toString());
				}
				break;	
		}
		String changeutin = jobLandOut.getChangeutin();
		String foreigntype = jobLandOut.getForeigntype();
		//换算CODE
		String curecode = jobLandOut.getCureCode();
//		NumberFormat nf = NumberFormat.getInstance();
//
//        String ss = nf.format(curecode); 
		if(curecode.indexOf(".") > 0){
			  //正则表达
			curecode = curecode.replaceAll("0+?$", "");//去掉后面无用的零
			curecode = curecode.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
			}
		System.out.println("curecode="+curecode);
		//外货
		String currencycodejp = currencycode;
		String forenlen = jobLandOut.getMylen();
		if(curecode!=null && !curecode.equals("") && changeutin!=null && !changeutin.equals("")){
			curecode=curecode+" ( 1"+currencycode+" : "+changeutin+foreigntype+" )";//X.XXXX（1XXX : 100XXX)
			currencycodejp = foreigntype;
			//如果小数点位数 字段为空  则默认没有小数点
			if(jobLandOut.getForeignlen()==null||jobLandOut.getForeignlen().equals("")) {
				forenlen = "0";
			}
			else {
				forenlen=jobLandOut.getForeignlen();
			}
			
		}
		jobLandOut.setCureCode(curecode);
		jobLandOut.setCurrencycode(currencycode);
		jobLandOut.setCurrencycodejp(currencycodejp);
		jobLandOut.setMoneyType(moneyType);
		//String ss = commonMethedService.getNewNum("123456789012.34");
		jobLandOut.setLendtrnNum(commonMethedService.getNewNum(jobLandOut.getLendtrnNum(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setLendtrnCost(commonMethedService.getNewNum(jobLandOut.getLendtrnCost(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setCostVatAmt(commonMethedService.getNewNum(jobLandOut.getCostVatAmt(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setInputAmt(commonMethedService.getNewNum(jobLandOut.getInputAmt(),Integer.parseInt(forenlen)));
		List<LendtrnConfirmBeen> jbs = new ArrayList<LendtrnConfirmBeen>();
		jbs.add(jobLandOut);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	@Override
	//立替原価承認票
	public String LendtrnApprovalPDF(OutPutInput jobLand) throws JRException {
		LendtrnApprovalBeen jobLandOut = LendtrnMapper.LendtrnApprovalPDF(jobLand.getJobNo(),Integer.valueOf(jobLand.getCompanyID()),jobLand.getInputNo(),jobLand.getLangTyp());
		Map<Object,Object>  hbcode =null;
		if(jobLandOut.getLendforeigntype()!=null&&!jobLandOut.getLendforeigntype().equals("")) {
			hbcode= commonMethedMapper.selectcodetype(jobLandOut.getLendforeigntype(),jobLand.getCompanyID());
		}
		if(hbcode!=null) {
			jobLandOut.setForeigntype(hbcode.get("ITMNAME").toString());
		}
		//登陆日加时区
		String addDate = commonMethedService.getTimeByZone(jobLandOut.getApprovalDate(),jobLand.getCompanyID()).substring(0,10);
		jobLandOut.setApprovalDate(addDate);
		String language = jobLand.getLangTyp();
		String currencycode = jobLandOut.getCurrencycode();
		String moneyType = jobLandOut.getMoneyType();
		Locale local = JasperOutPdfUtil.getLocalByLangT(language);
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		currencycode = jobLandOut.getLocalMoney();
		if("1".equals(jobLandOut.getIsHave())) {
			jobLandOut.setIsHave(bundle.getString("haveTax"));//税入
		}else {
			jobLandOut.setIsHave(bundle.getString("notHaveTax"));//税拔
		}
		switch(language){
			case "jp":
				
				currencycode = jobLandOut.getLocalMoneyJp();
				if(hbcode!=null) {
					jobLandOut.setForeigntype(hbcode.get("ITEMNAME_JP").toString());
				}
				break;
			case "en":
				currencycode = jobLandOut.getLocalMoneyEn();
				moneyType = jobLandOut.getMoneyTypeen();
				if(hbcode!=null) {
					jobLandOut.setForeigntype(hbcode.get("ITEMNAME_EN").toString());
				}
				break;
			case "hk":
				currencycode = jobLandOut.getLocalMoneyHk();
				moneyType = jobLandOut.getMoneyTypehk();
				if(hbcode!=null) {
					jobLandOut.setForeigntype(hbcode.get("ITEMNAME_HK").toString());
				}
				break;	
		}
		String changeutin = jobLandOut.getChangeutin();
		String foreigntype = jobLandOut.getForeigntype();
		//换算CODE
		String curecode = jobLandOut.getCureCode();
		if(curecode.indexOf(".") > 0){
			  //正则表达
			curecode = curecode.replaceAll("0+?$", "");//去掉后面无用的零
			curecode = curecode.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
			}
		//外货
		String currencycodejp = currencycode;
		String forenlen = jobLandOut.getMylen();
		if(curecode!=null && !curecode.equals("") && changeutin!=null && !changeutin.equals("")){
			curecode=curecode+" ( 1"+currencycode+" : "+changeutin+foreigntype+" )";//X.XXXX（1XXX : 100XXX)
			currencycodejp = foreigntype;
			forenlen=jobLandOut.getForeignlen();
		}
		jobLandOut.setCureCode(curecode);
		jobLandOut.setCurrencycode(currencycode);
		jobLandOut.setCurrencycodejp(currencycodejp);
		jobLandOut.setMoneyType(moneyType);
		jobLandOut.setLendtrnNum(commonMethedService.getNewNum(jobLandOut.getLendtrnNum(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setLendtrnCost(commonMethedService.getNewNum(jobLandOut.getLendtrnCost(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setCostVatAmt(commonMethedService.getNewNum(jobLandOut.getCostVatAmt(),Integer.parseInt(jobLandOut.getMylen())));
		if(jobLandOut.getDebtorAmt()!=null && jobLandOut.getDebtorAmt1()!=null) {
			String sumDebtorAmt = String.valueOf(MathController.add(Double.valueOf(jobLandOut.getDebtorAmt()), Double.valueOf(jobLandOut.getDebtorAmt1())));
			jobLandOut.setCreditAmt(commonMethedService.getNewNum(sumDebtorAmt,Integer.parseInt(jobLandOut.getMylen())));
			if(jobLandOut.getCreditAmt().equals("")||jobLandOut.getCreditAmt()==null) {
				jobLandOut.setCreditAmt("0");
			}
		}
		jobLandOut.setDebtorAmt(commonMethedService.getNewNum(jobLandOut.getDebtorAmt(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setDebtorAmt1(commonMethedService.getNewNum(jobLandOut.getDebtorAmt1(),Integer.parseInt(jobLandOut.getMylen())));
		jobLandOut.setCreditAmt1(commonMethedService.getNewNum(jobLandOut.getCreditAmt1(),Integer.parseInt(jobLandOut.getMylen())));
		
		if(jobLandOut.getTypeName()==null) {
			jobLandOut.setTypeName("");
			jobLandOut.setDebtorAmt("");
		}
		if(jobLandOut.getTypeName1()==null) {
			jobLandOut.setTypeName1("");
			jobLandOut.setDebtorAmt1("");
				}
		if(jobLandOut.getTypeName2()==null) {
			jobLandOut.setTypeName2("");
			jobLandOut.setCreditAmt("");
		}
		//入力金额可能存在外货的情况，因此小数点位数需要判断
		
		jobLandOut.setInputAmt(commonMethedService.getNewNum(jobLandOut.getInputAmt(),Integer.parseInt(forenlen)));
		List<LendtrnApprovalBeen> jbs = new ArrayList<LendtrnApprovalBeen>();
		jbs.add(jobLandOut);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	public boolean isDate(String date)  
    {  
        /** 
         * 判断日期格式和范围 
         */  
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";  
          
        Pattern pat = Pattern.compile(rexp);    
          
        Matcher mat = pat.matcher(date);    
          
        boolean dateType = mat.matches();  

        return dateType;  
    }  

}
