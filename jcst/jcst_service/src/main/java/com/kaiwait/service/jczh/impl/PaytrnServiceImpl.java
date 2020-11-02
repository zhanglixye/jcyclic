package com.kaiwait.service.jczh.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.OrderHistorytrn;
import com.kaiwait.bean.jczh.entity.Paytrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.io.PaytrnInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.jczh.PaytrnMapper;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PaytrnService;

@Service
public class PaytrnServiceImpl implements PaytrnService{

	@Resource
	private PaytrnMapper PaytrnMapper;
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

	//0：発注登録済、1：支払登録済、2：支払申請済、3：支払承認済
	String statusep = "1";
	String statusup = "2";
	String statusap = "3";
	
	public List<Paytrn> searchPaytrnList(PaytrnInput inputParam)
	{
		@SuppressWarnings("unused")
		PaytrnInput Paytrn = new PaytrnInput();
		return PaytrnMapper.searchPaytrnList(inputParam.getUserID(),inputParam.getCompanyID());
	}
	
	public PaytrnInput PaytrnQueryList(PaytrnInput inputParam)
	{
		PaytrnInput Paytrn = new PaytrnInput();
		String companyID = inputParam.getCompanyID();
		inputParam.getPaytrn().setCompany_cd(companyID);
		Paytrn.setPaytrnList(PaytrnMapper.Paytrnquery(inputParam.getPaytrn()));
//		List<Costtrn> cost = PaytrnMapper.Costtrnquery(inputParam.getPaytrn());
//		String da1 =  cost.get(0).getOrder_date();
//		if(!"".equals(cost.get(0).getOrder_date())) {
//			String da2  =commonMethedService.getTimeByZone(da1,companyID);
//			cost.get(0).setOrder_date(da2);
//		}
		Paytrn.setCosttrnList(PaytrnMapper.Costtrnquery(inputParam.getPaytrn()));
		Paytrn.setList_rooftrn(PaytrnMapper.rooftrnquery(companyID,inputParam.getPaytrn().getInput_no()));
		Paytrn.setItemList(commonMapper.selectMstNameByCD("9002",Integer.valueOf(companyID)));
		Paytrn.setForeign(costMapper.selCostForeign(Integer.valueOf(inputParam.getCompanyID()),null,"0050"));
		Paytrn.setList_lable(costMapper.selectLableList(inputParam.getUserID(),inputParam.getCompanyID()));
		String costno = PaytrnMapper.Costtrnnoquery(inputParam.getPaytrn().getInput_no(),inputParam.getPaytrn().getJob_cd(),companyID);
		List<JobLableTrn> costlable = costMapper.selectCostLable(costno,companyID,0);
	//	List<JobLableTrn> costlable= costMapper.selectCostLable(inputParam.getCost_no(),inputParam.getCompanyID(),0);
		Paytrn.setCostlable(costlable);
		Paytrn.setInvoiceintrnList(PaytrnMapper.Invoiceintrnquery(inputParam.getPaytrn().getInput_no(),companyID));
		Paytrn.setSkip(commonMethedMapper.selectSkipByCd(Integer.parseInt(companyID)));
		Paytrn.setPaylable(costMapper.selectCostLable(inputParam.getPaytrn().getInput_no(),inputParam.getCompanyID(),1));
		Paytrn.setPdfflagcri(commonMethedMapper.selPdfflg("0070","005",Integer.valueOf(companyID)));
		Paytrn.setPdfflagpro(commonMethedMapper.selPdfflg("0070","006",Integer.valueOf(companyID)));
		return Paytrn;
	}
	
	public List<Prooftrn> getProoftrnList(String companycd,String inputno)
	{
		return PaytrnMapper.rooftrnquery(companycd,inputno);

	}
	//支付申请
	public int PaytrnApply(PaytrnInput inputParam)
	{
		inputParam.getPaytrn().setUpdate(DateUtil.getNowDate());
		String jobcd = inputParam.getPaytrn().getJob_cd();
		String costno = inputParam.getPaytrn().getCostno();
		String companyid = inputParam.getCompanyID();
		String costflg =String.valueOf(PaytrnMapper.getcostflg(companyid,costno,jobcd));
		if(!costflg.equals("1")){//必须是1：支払登録済
			return -1;
		}
		int locknum = inputParam.getPaytrn().getLock_flg();
		int locknumnow=PaytrnMapper.queryLock(inputParam.getPaytrn().getInput_no(),companyid);
		if(locknumnow>locknum){
			 return -2; 
		 }
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getPaytrn().getCostno(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getPaytrn().getCostno());
					jltrn.get(i).setLablelevel(0);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 PaytrnMapper.updateLock(inputParam.getPaytrn().getInput_no(),companyid);
		 PaytrnMapper.costtrnUpdate(inputParam.getCompanyID(),jobcd,statusup,costno);
		return PaytrnMapper.Paytrnapply(inputParam.getPaytrn());
	}
	//支付承认
	public int PaytrnApproval(PaytrnInput inputParam)
	{
		inputParam.getPaytrn().setUpdate(DateUtil.getNowDate());
		@SuppressWarnings("unused")
		String inputno = inputParam.getPaytrn().getInput_no();
		String jobcd = inputParam.getPaytrn().getJob_cd();
		String costno = inputParam.getPaytrn().getCostno();
		String companyid = inputParam.getCompanyID();
		String lenddate= inputParam.getPaytrn().getPay_plan_date();
  	     if(!isDate(lenddate)) {
	    	 return -3; 
	     }
		int locknum = inputParam.getPaytrn().getLock_flg();
		int locknumnow=PaytrnMapper.queryLock(inputParam.getPaytrn().getInput_no(),companyid);
		if(locknumnow>locknum){
			 return -2; 
		 }
		//0：発注登録済、1：支払登録済、2：支払申請済、3：支払承認済
		String costflg =String.valueOf(PaytrnMapper.getcostflg(companyid,costno,jobcd));
		//支付申请0：不跳；1：跳
		String payjump =String.valueOf(PaytrnMapper.getpayjump(companyid));
		inputParam.getPaytrn().setPayjump(payjump);
		//1：支払登録済+跳过、或者2：支払申請済可以做承认
		if(payjump.equals("0")) {
			if(!costflg.equals("2")) {
				return -1;
			}
		}
//		if(!costflg.equals("1")&&payjump.equals("1")) {
//			return -1;
//		}
//		if(!costflg.equals("2")&&!payjump.equals("1")) {
//			return -1;
//		}
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getPaytrn().getCostno(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getPaytrn().getCostno());
					jltrn.get(i).setLablelevel(0);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		//王哥报表用
		 	List<Paytrn> paytrn = PaytrnMapper.Paytrnquery(inputParam.getPaytrn());
			String month = commonMethedService.getSystemDate(Integer.valueOf(companyid));
			month =month.substring(0, 4)+month.substring(5, 7);
			OrderHistorytrn orderhistorytrn = new OrderHistorytrn();
			orderhistorytrn.setCheckMonth(month);
			orderhistorytrn.setCompanycd(inputParam.getCompanyID());
			orderhistorytrn.setCostAmt(paytrn.get(0).getPay_amt());
			orderhistorytrn.setCostVat(paytrn.get(0).getPay_vat_amt());
			orderhistorytrn.setJobNo(jobcd);
			orderhistorytrn.setOrderNo(costno);
			orderhistorytrn.setOrderStatus("1");
			orderhistorytrn.setOrderType("0");
			orderhistorytrn.setCostStatus(costflg);
			orderhistorytrn.setPayAmt(paytrn.get(0).getPay_rmb());
			orderhistorytrn.setAdddate(DateUtil.getNowTime());
			orderhistorytrn.setAddusercd(inputParam.getUserID());
			PaytrnMapper.addOrder(orderhistorytrn);
			PaytrnMapper.costtrnUpdate(inputParam.getCompanyID(),jobcd,statusap,costno);
			PaytrnMapper.updateLock(inputParam.getPaytrn().getInput_no(),companyid);
			String upuser = PaytrnMapper.queryUpuser(inputParam.getPaytrn().getInput_no(),companyid);
			if(upuser!=null) {
				inputParam.getPaytrn().setUpusercd(upuser);	
			}
			
		return PaytrnMapper.Paytrnapproval(inputParam.getPaytrn());
	}
	//支付驳回
	public int PaytrnApprovalBack(PaytrnInput inputParam)
	{
		String jobcd = inputParam.getPaytrn().getJob_cd();
		String costno = inputParam.getPaytrn().getCostno();
		inputParam.getPaytrn().setUpdate(DateUtil.getNowDate());
		String companyid = inputParam.getCompanyID();
		String costflg =String.valueOf(PaytrnMapper.getcostflg(companyid,costno,jobcd));
		if(!costflg.equals("2")){//必须是1：支払申請済
			return -1;
		}
		int locknum = inputParam.getPaytrn().getLock_flg();
		int locknumnow=PaytrnMapper.queryLock(inputParam.getPaytrn().getInput_no(),companyid);
		if(locknumnow>locknum){
			 return -2; 
		 }
		jobLandMapper.deleteByJobCd(null, inputParam.getPaytrn().getCostno(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		List<JobLableTrn> jltrn = inputParam.getLableList();
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getPaytrn().getCostno());
					jltrn.get(i).setLablelevel(0);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		PaytrnMapper.updateLock(inputParam.getPaytrn().getInput_no(),companyid);
		PaytrnMapper.costtrnUpdate(inputParam.getCompanyID(),jobcd,statusep,costno);
		return PaytrnMapper.Paytrnapprovalback(inputParam.getPaytrn());
	}
	//支付承认取消
	public int PaytrnApprovalCancel(PaytrnInput inputParam)
	{
		inputParam.getPaytrn().setUpdate(DateUtil.getNowDate());
		@SuppressWarnings("unused")
		String inputno = inputParam.getPaytrn().getInput_no();
		String jobcd = inputParam.getPaytrn().getJob_cd();
		String costno = inputParam.getPaytrn().getCostno();
		String companyid = inputParam.getCompanyID();
		String costflg =String.valueOf(PaytrnMapper.getcostflg(companyid,costno,jobcd));
		if(!costflg.equals("3")){//必须是3：支払承認済
			return -1;
		}
		int locknum = inputParam.getPaytrn().getLock_flg();
		int locknumnow=PaytrnMapper.queryLock(inputParam.getPaytrn().getInput_no(),companyid);
		if(locknumnow>locknum){
			 return -2; 
		 }
		jobLandMapper.deleteByJobCd(null, inputParam.getPaytrn().getCostno(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		List<JobLableTrn> jltrn = inputParam.getLableList();
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getPaytrn().getCostno());
					jltrn.get(i).setLablelevel(0);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
			//王哥报表用
			String month = commonMethedService.getSystemDate(Integer.valueOf(companyid));
			month =month.substring(0, 4)+month.substring(5, 7);
			OrderHistorytrn orderhistorytrn = new OrderHistorytrn();
			orderhistorytrn.setCheckMonth(month);
			orderhistorytrn.setCompanycd(inputParam.getCompanyID());
			orderhistorytrn.setJobNo(jobcd);
			orderhistorytrn.setOrderNo(costno);
			PaytrnMapper.DeleteOrder(orderhistorytrn);
			//支付申请0：不跳；1：跳
			String payjump =String.valueOf(PaytrnMapper.getpayjump(companyid));
		PaytrnMapper.costtrnUpdate(inputParam.getCompanyID(),jobcd,statusep,costno);
		if(payjump.equals("1")) {
			 PaytrnMapper.PaytrnApprovalJumpCancel(inputParam.getPaytrn());
		}else {
			 PaytrnMapper.PaytrnApprovalCancel(inputParam.getPaytrn());
		}
		return PaytrnMapper.updateLock(inputParam.getPaytrn().getInput_no(),companyid);
		
	}
	public int PaytrnAddTx(PaytrnInput inputParam)
	{
		inputParam.getPaytrn().setAdddate(DateUtil.getNowDate());
		inputParam.getPaytrn().setUpdate(DateUtil.getNowDate());		
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getPaytrn().getCostno(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getPaytrn().getCostno());
					jltrn.get(i).setLablelevel(0);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		 
		PaytrnMapper.insertProoftrn(inputParam.getList_rooftrn(),inputParam.getUserID(),inputParam.getCompanyID(),inputParam.getPaytrn().getInput_no(),DateUtil.getNowDate());
		return PaytrnMapper.insertPaytrn(inputParam.getPaytrn());

	}
	public int PaytrnUpdateTx(PaytrnInput inputParam)
	{
		inputParam.getPaytrn().setUpdate(DateUtil.getNowDate());
		List<JobLableTrn> jltrn = inputParam.getLableList();
		jobLandMapper.deleteByJobCd(null, inputParam.getPaytrn().getCostno(), Integer.valueOf(inputParam.getCompanyID()),
				"orderlabeltrn");
		 if(jltrn==null||jltrn.size()==0) {
		    	jltrn = null;
		   }else {
		    	for(int i=0;i<jltrn.size();i++) {
					jltrn.get(i).setCostno(inputParam.getPaytrn().getCostno());
					jltrn.get(i).setLablelevel(0);
					jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
					jltrn.get(i).setUpdDate(DateUtil.getNowTime());
					jltrn.get(i).setUpUsercd(inputParam.getUserID());
					jltrn.get(i).setAddDate(DateUtil.getNowTime());
					jltrn.get(i).setAddUsercd(inputParam.getUserID());
		    	}
		    	
		    	costMapper.insertCostLable(jltrn);
		    	}
		PaytrnMapper.deleteProoftrn(inputParam.getCompanyID(),inputParam.getPaytrn().getInput_no());
		PaytrnMapper.insertProoftrn(inputParam.getList_rooftrn(),inputParam.getUserID(),inputParam.getCompanyID(),inputParam.getPaytrn().getInput_no(),DateUtil.getNowDate());
		return PaytrnMapper.updatePaytrn(inputParam.getPaytrn());

	}
	//@Override
	public PaytrnInput initPaytrnList(PaytrnInput Paytrn){
		PaytrnInput Paytrninput = new PaytrnInput();
		String companyID = Paytrn.getCompanyID();
		//经费科目，下拉选框用
		Paytrninput.setItemList(commonMapper.selectMstNameByCD("9002",Integer.valueOf(companyID)));
		Paytrninput.setList_lable(joblandmapper.selectLableList(Paytrn.getCustId(),0,Integer.valueOf(companyID),1));
		Paytrninput.setForeign(costMapper.selCostForeign(Integer.valueOf(companyID),null,"0050"));
		return Paytrninput;
	}
	public Map<String,Object> getRemarkList(String companycd,String inputno,String jobcd){
		String costno = PaytrnMapper.Costtrnnoquery(inputno,jobcd,companycd);
		if(costno==null) {
			return null;
		}
		return PaytrnMapper.getRemark(companycd,inputno,jobcd,costno);
	}
	public Map<String,Object> getClmstList(String companycd,String inputno,String jobcd){
		Map<String,Object> list = PaytrnMapper.getClmstList(companycd,inputno,jobcd);
		if(list==null) {
			return null;
		}
		String datetime = (String) list.get("pay_add_date");
		String timeZone = commonMethedMapper.getTimeZoneByCompany(companycd);
		if(timeZone == null || timeZone.equals(""))
		{
			return null;
		}
		String newdate =  DateUtil.getNewTime(datetime, Integer.valueOf(timeZone));
		list.put("pay_add_date", newdate.substring(0,10));
		return list;
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
