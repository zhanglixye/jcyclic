package com.kaiwait.service.jczh.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobInfo;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Trantrn;
import com.kaiwait.bean.jczh.entity.TrantrnApprovalBeen;
import com.kaiwait.bean.jczh.entity.TrantrnConfirmBeen;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.TrantrnInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.mappers.jczh.TrantrnMapper;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.TrantrnService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class TrantrnServiceImpl implements TrantrnService{

	@Resource
	private TrantrnMapper TrantrnMapper;
	@Resource
	private CommonmstMapper commonMapper;
	@Resource
	private JobLandMapper joblandmapper;
	@Resource
	private CostMapper costMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private JobMapper jobmapper;
	
	public List<Trantrn> searchTrantrnList(TrantrnInput inputParam)
	{
		return TrantrnMapper.searchTrantrnList(inputParam.getUserID(),inputParam.getCompanyID());
	}
	
	public TrantrnInput TrantrnQueryList(TrantrnInput inputParam)
	{
		TrantrnInput trantrn = new TrantrnInput();
		String companyID = inputParam.getCompanyID();
		inputParam.getTrantrn().setCompany_cd(companyID);
		Trantrn tran=TrantrnMapper.trantrnDate(inputParam.getTrantrn());
		if(tran==null) {
			return null;
		}
			if(!"".equals(tran.getAdddate())) {
				tran.setAdddate(commonMethedService.getTimeByZone(tran.getAdddate(),companyID));		
			}
			if(!"".equals(tran.getUpddate())) {
				tran.setUpddate(commonMethedService.getTimeByZone(tran.getUpddate(),companyID));		
			}
			if(!"".equals(tran.getConfirmdate())) {
				tran.setConfirmdate(commonMethedService.getTimeByZone(tran.getConfirmdate(),companyID));
			}
			if(!"".equals(tran.getCancelconfirmdate())) {
				tran.setCancelconfirmdate(commonMethedService.getTimeByZone(tran.getCancelconfirmdate(),companyID));
			}
	    trantrn.setTrantrnDate(tran);
		List<Trantrn> trantrnlist=TrantrnMapper.trantrnquery(inputParam.getTrantrn());
		if(trantrnlist==null) {
			return null;
		}
		if(!"".equals(trantrnlist.get(0).getAdddate())) {
			trantrnlist.get(0).setAdddate(commonMethedService.getTimeByZone(trantrnlist.get(0).getAdddate(),companyID));		
		}
		if(!"".equals(trantrnlist.get(0).getUpddate())) {
			trantrnlist.get(0).setUpddate(commonMethedService.getTimeByZone(trantrnlist.get(0).getUpddate(),companyID));		
		}
		trantrn.setTrantrnList(trantrnlist);
		trantrn.setItemList(commonMapper.selectMstNameByCD("9002",Integer.valueOf(companyID)));
		trantrn.setForeignFormatFlg(commonMapper.getforeignVatFormatFlg("0051",companyID,"001"));
		trantrn.setSaleVatFormatFlg(commonMapper.getforeignVatFormatFlg("0052",companyID,"002"));
		trantrn.setList_lable(joblandmapper.selectLableList(inputParam.getUserID(),1,Integer.valueOf(companyID),1));
		List<JobLableTrn> costlable = costMapper.selectCostLable(inputParam.getTrantrn().getInput_no(),companyID,3);
		trantrn.setLableList(costlable);
		trantrn.setPdfflagcri(commonMethedMapper.selPdfflg("0070","008",Integer.valueOf(companyID)));
		trantrn.setPdfflagpro(commonMethedMapper.selPdfflg("0070","010",Integer.valueOf(companyID)));
		return trantrn;
	}
	
	public int TrantrnApproval(TrantrnInput inputParam)
	{
		inputParam.getTrantrn().setUp_date(DateUtil.getNowTime());
		String companyID = inputParam.getCompanyID();
		String jobcd = inputParam.getTrantrn().getJob_cd();
		String inputno = inputParam.getTrantrn().getInput_no();
		String status = TrantrnMapper.getstatus(jobcd,inputno,companyID);
		if(!status.equals("0")){
			return 0;
		}
		int locknum = inputParam.getTrantrn().getLock_flg();
		int locknumnow=TrantrnMapper.queryLock(jobcd,companyID,inputno);
		if(locknumnow>locknum){
			 return -1; 
		 }
		inputParam.getTrantrn().setCompany_cd(companyID);
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getTrantrn().getInput_no(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getTrantrn().getInput_no());
					jltrn.get(i).setLablelevel(3);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 TrantrnMapper.updateLock(jobcd,companyID,inputno);
		return TrantrnMapper.trantrnapproval(inputParam.getTrantrn());
	}
	public String TrantrnAddTx(TrantrnInput inputParam)
	{
//		Calendar rightNow = Calendar.getInstance();
//		Date date = new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String dt23 = sdf.format(date);
//		try {
//	       	 Date dt1=sdf.parse(dt23);//现在时间
//	   	     String lenddate= inputParam.getTrantrn().getTran_date();
//	   	     Date dt2 = sdf.parse(lenddate);//使用日
//	   	     //使用日必须小于现在时间
//	            if (dt1.getTime() > dt2.getTime()) {
//	                return "-2";
//	            }
//	        } catch (Exception exception) {
//	            exception.printStackTrace();
//	        }
		inputParam.getTrantrn().setAdd_date(DateUtil.getNowTime());
		inputParam.getTrantrn().setUp_date(DateUtil.getNowTime());
		String companyID = inputParam.getCompanyID();
		inputParam.getTrantrn().setCompany_cd(companyID);
		String inputno = inputParam.getTrantrn().getInput_no();
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
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getTrantrn().getInput_no(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getTrantrn().getInput_no());
					jltrn.get(i).setLablelevel(3);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 TrantrnMapper.insertusersettingtrn(inputParam.getTrantrn());
		 
		 return inputno;
		

	}
	public String TrantrnUpdateTx(TrantrnInput inputParam)
	{
//		Calendar rightNow = Calendar.getInstance();
//		Date date = new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String dt23 = sdf.format(date);
//		try {
//	       	 Date dt1=sdf.parse(dt23);//现在时间
//	   	     String lenddate= inputParam.getTrantrn().getTran_date();
//	   	     Date dt2 = sdf.parse(lenddate);//使用日
//	   	     //使用日必须小于现在时间
//	            if (dt1.getTime() > dt2.getTime()) {
//	                return -2;
//	            }
//	        } catch (Exception exception) {
//	            exception.printStackTrace();
//	        }
		inputParam.getTrantrn().setUp_date(DateUtil.getNowTime());
		String companyID = inputParam.getCompanyID();
		int locknum = inputParam.getTrantrn().getLock_flg();
		int locknumnow=TrantrnMapper.queryLock(inputParam.getTrantrn().getJob_cd(),inputParam.getCompanyID(),inputParam.getTrantrn().getInput_no());
		if(locknumnow>locknum){
			 return "-1"; 
		 }
		inputParam.getTrantrn().setCompany_cd(companyID);
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getTrantrn().getInput_no(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getTrantrn().getInput_no());
					jltrn.get(i).setLablelevel(3);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 TrantrnMapper.updateLock(inputParam.getTrantrn().getJob_cd(),inputParam.getCompanyID(),inputParam.getTrantrn().getInput_no());
		 TrantrnMapper.updateTrantrn(inputParam.getTrantrn());
		 return inputParam.getTrantrn().getNew_input_no();
		

	}
	public int TrantrnDeleteTx(TrantrnInput inputParam)
	{
		String companyID = inputParam.getCompanyID();
		String jobcd = inputParam.getTrantrn().getJob_cd();
		String inputno = inputParam.getTrantrn().getInput_no();
		String usercd = inputParam.getUserID();
		int locknum = inputParam.getTrantrn().getLock_flg();
		int locknumnow=TrantrnMapper.queryLock(jobcd,companyID,inputno);
		if(locknumnow>locknum){
			 return -1; 
		 }
		TrantrnMapper.updateLock(jobcd,companyID,inputno);
		return TrantrnMapper.updateDelete(jobcd,inputno,companyID,DateUtil.getNowTime(),usercd);

	}
	//振替初始化
	public TrantrnInput initTrantrnList(TrantrnInput trantrn){
		TrantrnInput trantrninput = new TrantrnInput();
		String companyID = trantrn.getCompanyID();
		//经费科目，下拉选框用
		trantrninput.setForeignFormatFlg(commonMapper.getforeignVatFormatFlg("0051",companyID,"001"));
		trantrninput.setSaleVatFormatFlg(commonMapper.getforeignVatFormatFlg("0052",companyID,"002"));
		trantrninput.setItemList(commonMapper.selectMstNameByCD("9002",Integer.valueOf(companyID)));
		trantrninput.setList_lable(joblandmapper.selectLableList(trantrn.getUserID(),1,Integer.valueOf(companyID),1));
		List<Cost> ss = costMapper.selCostForeigntwo(Integer.valueOf(companyID),null,"0050");
		trantrninput.setCost_foreign_type(ss);
		List<JobLableTrn> costlable = costMapper.selectCostLable(trantrn.getTrantrn().getInput_no(),companyID,3);
		trantrninput.setLableList(costlable);
		trantrninput.setPdfflagcri(commonMethedMapper.selPdfflg("0070","008",Integer.valueOf(companyID)));
		trantrninput.setPdfflagpro(commonMethedMapper.selPdfflg("0070","010",Integer.valueOf(companyID)));
		return trantrninput;
	}
	//立替初始化
	public TrantrnInput initLendtrnList(TrantrnInput trantrn){
		TrantrnInput trantrninput = new TrantrnInput();
		String companyID = trantrn.getCompanyID();
		//经费科目，下拉选框用
		trantrninput.setForeignFormatFlg(commonMapper.getforeignVatFormatFlg("0051",companyID,"001"));
		trantrninput.setSaleVatFormatFlg(commonMapper.getforeignVatFormatFlg("0052",companyID,"002"));
		trantrninput.setItemList(commonMapper.selectMstNameByCD("9001",Integer.valueOf(companyID)));
		trantrninput.setList_lable(joblandmapper.selectLableList(trantrn.getUserID(),1,Integer.valueOf(companyID),1));
		List<Cost> ss = costMapper.selCostForeigntwo(Integer.valueOf(companyID),null,"0050");
		trantrninput.setCost_foreign_type(ss);
		List<JobLableTrn> costlable = costMapper.selectCostLable(trantrn.getTrantrn().getInput_no(),companyID,3);
		trantrninput.setLableList(costlable);
		trantrninput.setPdfflagcri(commonMethedMapper.selPdfflg("0070","007",Integer.valueOf(companyID)));
		trantrninput.setCommonmst(commonMapper.getforeignLen(companyID));
		//trantrninput.setPdfflagpro(commonMethedMapper.selPdfflg("0010","004",Integer.valueOf(companyID)));
		return trantrninput;
	}
	public int cancelTranTx(TrantrnInput trantrn) {
		// TODO Auto-generated method stub
		String companyID = trantrn.getCompanyID();
		String jobcd = trantrn.getTrantrn().getJob_cd();
		String inputno = trantrn.getTrantrn().getInput_no();
		String status = TrantrnMapper.getstatus(jobcd,inputno,companyID);
		if(!status.equals("1")){
			return 0;
		}
		int locknum = trantrn.getTrantrn().getLock_flg();
		int locknumnow=TrantrnMapper.queryLock(jobcd,companyID,inputno);
		if(locknumnow>locknum){
			 return -1; 
		 }
		Trantrn tran = trantrn.getTrantrn();
		tran.setCancelconfirmdate(DateUtil.getNowTime());
		tran.setCancelconfirmemp(trantrn.getUserID());
		tran.setCompany_cd(trantrn.getCompanyID());
		List<JobLableTrn> jltrn = trantrn.getLableList();
		jobLandMapper.deleteByJobCd(null, trantrn.getTrantrn().getInput_no(), Integer.valueOf(companyID),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(trantrn.getTrantrn().getInput_no());
					jltrn.get(i).setLablelevel(3);
					jltrn.get(i).setCompanycd(Integer.valueOf(trantrn.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(trantrn.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(trantrn.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 TrantrnMapper.updateLock(jobcd,companyID,inputno);
		return TrantrnMapper.cancelTran(tran);
	}
	
	@Override
	//インプット確認票（振替）
	public String TrantrnConfirmPDF(OutPutInput jobLand) throws JRException {
		TrantrnConfirmBeen jobLandOut = TrantrnMapper.TrantrnConfirmPDF(jobLand.getJobNo(),Integer.valueOf(jobLand.getCompanyID()),jobLand.getInputNo());
		String language = jobLand.getLangTyp();
		String currencycode = jobLandOut.getCurrencycode();
		String moneyType = jobLandOut.getMoneyType();
		switch(language){
			case "jp":
				//currencycode=jobLandOut.getCurrencycodejp();
				moneyType = jobLandOut.getMoneyTypejp();
				break;
			case "en":
				//currencycode=jobLandOut.getCurrencycodeen();
				moneyType = jobLandOut.getMoneyTypeen();
				break;
			case "hk":
				//currencycode=jobLandOut.getCurrencycodehk();
				moneyType = jobLandOut.getMoneyTypehk();
				break;	
		}
		jobLandOut.setCurrencycode(currencycode);
		jobLandOut.setMoneyType(moneyType);
		jobLandOut.setTrantrnNum(commonMethedService.getNewNum(jobLandOut.getTrantrnNum(),Integer.parseInt(jobLandOut.getMylen())));
		List<TrantrnConfirmBeen> jbs = new ArrayList<TrantrnConfirmBeen>();
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
	//経費振替承認票
	public String TrantrnApprovalPDF(OutPutInput jobLand) throws JRException {
		TrantrnApprovalBeen jobLandOut = TrantrnMapper.TrantrnApprovalPDF(jobLand.getJobNo(),Integer.valueOf(jobLand.getCompanyID()),jobLand.getInputNo());
		String language = jobLand.getLangTyp();
		String currencycode = jobLandOut.getCurrencycode();
		String moneyType = jobLandOut.getMoneyType();
		switch(language){
			case "jp":
				//currencycode=jobLandOut.getCurrencycodejp();
				moneyType = jobLandOut.getMoneyTypejp();
				break;
			case "en":
				//currencycode=jobLandOut.getCurrencycodeen();
				moneyType = jobLandOut.getMoneyTypeen();
				break;
			case "hk":
				//currencycode=jobLandOut.getCurrencycodehk();
				moneyType = jobLandOut.getMoneyTypehk();
				break;	
		}
		jobLandOut.setCurrencycode(currencycode);
		jobLandOut.setMoneyType(moneyType);
		jobLandOut.setTrantrnNum(commonMethedService.getNewNum(jobLandOut.getTrantrnNum(),Integer.parseInt(jobLandOut.getMylen())));
		List<TrantrnApprovalBeen> jbs = new ArrayList<TrantrnApprovalBeen>();
		jbs.add(jobLandOut);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
}
