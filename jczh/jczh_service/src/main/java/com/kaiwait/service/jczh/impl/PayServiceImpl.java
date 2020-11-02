package com.kaiwait.service.jczh.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.Pay;
import com.kaiwait.bean.jczh.entity.PayConfirmPDF;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.entity.Skip;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CommonmstMapper;
import com.kaiwait.mappers.jczh.CostMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.mappers.jczh.LendtrnMapper;
import com.kaiwait.mappers.jczh.PayMapper;
import com.kaiwait.mappers.jczh.PaytrnMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.PayService;
import com.kaiwait.utils.common.StringUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**   
 * @ClassName:  PayServiceImpl   
 * @Description:支付情报功能模块实现类(这里用一句话描述这个类的作用)   
 * @author: "马有翼" 
 * @date:   2018年9月19日 下午2:25:08      
 * @Copyright: 2018 KAIWAIT All rights reserved. 
 * 注意：本内容仅限于海和信息技术（大连）有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class PayServiceImpl implements PayService {
	@Resource
	private CostMapper costMapper;
	@Resource
	private PayMapper payMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private PaytrnMapper paytrnMapper;
	@Resource
	private LendtrnMapper LendtrnMapper;
	@Resource
	private CommonmstMapper commonmstdMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private JobMapper jobmapper;
	
	
	

	/**   
	 * <p>Title: insertPayInfoTx</p>   
	 * <p>Description:插入支付情报信息 </p>   
	 * @param inputParam
	 * @return   
	 * @see com.kaiwait.service.jczh.PayService#insertPayInfoTx(com.kaiwait.bean.jczh.io.PayInput)
	 * @author "马有翼"
	 * @date:   2018年9月19日 下午2:25:30 
	 */ 
	public Map<String, Object> insertPayInfoTx(PayInput inputParam) {
		//原件没登录不可以更新
		HashMap<String, Object> output= new HashMap<>();
		List<Cost> cost= costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID()); 
			if(cost.size()>0) {
				//外发被更新过，不可支付登录
				if(cost.get(0).getLockflg()==inputParam.getLockflg()) {
					//外发登陆状态，可做支付情报登录
					@SuppressWarnings("unused")
					String ss =cost.get(0).getStatus();
					if("0".equals(cost.get(0).getStatus())){
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
		//验证发驻日与预定纳品日规则
		if(inputParam.getPay_dlyday().compareTo(inputParam.getPay_hope_date())>0){
			output.put("messge", "PAY_DLYDAYMORE");
			output.put("num", 0);
			return output;
		}
		//验证发票号必填
		int orderInvoiceFlg = commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID());
		if(orderInvoiceFlg == 1 && StringUtil.isEmpty(inputParam.getInvoice_no()))
		{
			output.put("messge", "VALIDATE_FORMAT_ERROR");
			output.put("num", 0);
			return  output;
		}
		
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		inputParam.setUpdate(dateformat.format(date));
		inputParam.setAdddate(dateformat.format(date));
		inputParam.setAddusercd(inputParam.getUserID());
		inputParam.setUpusercd(inputParam.getUserID());
		inputParam.setCompany_cd(inputParam.getCompanyID());
		List<JobLableTrn> jltrn = inputParam.getLableList();
		if (jltrn == null || jltrn.size() == 0) {
			jltrn = null;
		} else {
			for (int i = 0; i < jltrn.size(); i++) {
				jltrn.get(i).setCostno(inputParam.getCost_no());
				jltrn.get(i).setLablelevel(0);// 外发，支付。
				jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
				jltrn.get(i).setUpdDate(dateformat.format(date));
				jltrn.get(i).setUpUsercd(inputParam.getUserID());
				jltrn.get(i).setAddDate(dateformat.format(date));
				jltrn.get(i).setAddUsercd(inputParam.getUserID());

			}
			jobLandMapper.deleteByJobCd(null, inputParam.getCost_no(), Integer.valueOf(inputParam.getCompanyID()),
					"orderlabeltrn");
			costMapper.insertCostLable(jltrn);
		}
	/*	Skip skip = commonMethedMapper.selectSkipByCd(Integer.parseInt(inputParam.getCompanyID()));
		if(skip.getPay()==1) {//支付申请跳过
			 inputParam.setStatus(2);
			 costMapper.updateCostStatus(inputParam);	
		}else {
			 
		}*/
		
		inputParam.setStatus(1);
		 costMapper.updateCostStatus(inputParam);	
		// 插入支付
		inputParam.setCompany_cd(inputParam.getCompanyID());
		
		payMapper.insertInvoiceintrnTx(inputParam);  
		// 插入凭证
		if (inputParam.getList_rooftrn() != null&&inputParam.getList_rooftrn().size()>0) {
			LendtrnMapper.insertProoftrn(inputParam.getList_rooftrn(), inputParam.getUserID(),
					inputParam.getCompanyID(), inputParam.getInput_no(), dateformat.format(date));
		}
		int num = payMapper.insertPayInfoTx(inputParam);
		//2019/6/25添加，支付申请跳过时，插入支付申请者和时间。但状态不更新成支付申请济（咱也不知道为什么咱也不敢问）
		Skip skip = commonMethedMapper.selectSkipByCd(Integer.parseInt(inputParam.getCompanyID()));
		if(skip.getPay()==1) {//支付申请跳过
			 payMapper.updatePayREQTx(inputParam);	
		}
		output.put("num", num);
		output.put("input_no", inputParam.getInput_no());
		return output;
	}

	/**   
	 * <p>Title: initPayInfo</p>   
	 * <p>Description:支付情报侵袭初始化 </p>   
	 * @param inputParam
	 * @return   
	 * @see com.kaiwait.service.jczh.PayService#initPayInfo(com.kaiwait.bean.jczh.io.CostInput)
	 * @author "马有翼"
	 * @date:   2018年9月19日 下午2:48:44 
	 */ 
	public Map<String, Object> initPayInfo(CostInput inputParam) {
		HashMap<String, Object> outsoure = new HashMap<String, Object>();
		outsoure.put("orderNoIsMustFlg", commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID()));
		if(!commonMethedService.validatePower(inputParam.getJob_cd(), inputParam.getUserID(), Integer.valueOf(inputParam.getCompanyID()), null, "insertAndUpdPay")) {
			outsoure.put("validatePower", "0");
			return outsoure;
		}else {
			outsoure.put("validatePower", "1");
		}
		String inputno = null;
		// 初始化外货下拉列表
		List<Cost> Foreign = costMapper.selCostForeign(Integer.valueOf(inputParam.getCompanyID()),null,"0050");
		outsoure.put("Foreign", Foreign);
		// 查询得意先
		List<Cost> CLDIV = costMapper.selectCLDIV(inputParam.getJob_cd(), inputParam.getCompanyID());
		String  checkDate  = costMapper.selectCheckDate(inputParam.getJob_cd(), inputParam.getCompanyID(), inputParam.getCost_no());
		outsoure.put("checkDate",checkDate);
		if(CLDIV.size()<1) {
			outsoure.put("messge", "DATA_IS_NOT_EXIST");
			return outsoure;
		}else {
			if(!"".equals(CLDIV.get(0).getUpddate())) {
				CLDIV.get(0).setUpddate(commonMethedService.getTimeByZone(CLDIV.get(0).getUpddate(),inputParam.getCompanyID()));		
			}
		}
		outsoure.put("CLDIV", CLDIV);
		List<Lable> lable = costMapper.selectLableList(inputParam.getUserID(),inputParam.getCompanyID());
		outsoure.put("lable", lable);
		List<JobLableTrn> paylable = costMapper.selectCostLable(inputParam.getCost_no(), inputParam.getCompanyID(), 0);
		outsoure.put("paylable", paylable);
		// 发票种类
		List<Cost> PayeeType = costMapper.selectPayeeType("0061", inputParam.getCompanyID());
		// 发票内容
		List<Cost> Payeetext = costMapper.selectPayeeType("0062", inputParam.getCompanyID());
		outsoure.put("invoice_type", PayeeType);
		outsoure.put("invoice_text", Payeetext);
		// 查询外发登录信息
		List<Cost> outinfo = costMapper.selectOutSource(inputParam.getJob_cd(), inputParam.getCost_no(),inputParam.getCompanyID());
		if(outinfo.size()<1) {
			outsoure.put("messge", "DATA_IS_NOT_EXIST");
			return outsoure;
		}else {
			if(!"".equals(outinfo.get(0).getCostupdate())) {
				outinfo.get(0).setCostupdate(commonMethedService.getTimeByZone(outinfo.get(0).getCostupdate(),inputParam.getCompanyID()));		
			}
		}
		//支付登錄時查询是否是最新税率
		CostInput cost1 = new CostInput();
		cost1.setPayee_cd(outinfo.get(0).getPayeecd());
		cost1.setPlan_dlvdays(outinfo.get(0).getPlandlvday());
		cost1.setCompanyID(inputParam.getCompanyID());
		cost1.setInvoice_text(outinfo.get(0).getInvoicetext());
		cost1.setInvoice_type(outinfo.get(0).getInvoicetype());
		List<Cost> list = costMapper.selectTax(cost1);
		if(list.size()>0) {
			outinfo.get(0).setCostVatRate(list.get(0).getVatrate().toString());
		}else {
			outinfo.get(0).setCostVatRate(outinfo.get(0).getCostrate().toString());
		}
	
		outsoure.put("outinfo", outinfo);
		// cost与lable关系
		if (outinfo.size() > 0) {
			// 第一条外发成本对应的支付登陆编号
			inputno = outinfo.get(0).getInputno();
			if(inputno!=null&&!inputno.equals("")) {//当costtrn存在inputno时，代表初始化的是支付情报更新，应为inputno是，支付情报登录时录入的
				// 发票内容
				inputParam.setInput_no(inputno);
				List<Cost> Invoiceintrn = costMapper.selInvoiceintrn(inputParam.getInput_no(),inputParam.getCompanyID());
				outsoure.put("Invoiceintrn", Invoiceintrn);
			
			}else {//inputno没值时，代表支付情报登录
				List<Cost> Invoiceintrn = costMapper.selInvoiceintrn(inputParam.getCost_no(),inputParam.getCompanyID());
				outsoure.put("Invoiceintrn", Invoiceintrn);
			}
			
		}
		// 第一条支付的凭证list
		List<Prooftrn> prooftrn = paytrnMapper.rooftrnquery(inputParam.getCompanyID(), inputno);
		outsoure.put("prooftrn", prooftrn);
		String foreignFormatFlg = commonmstdMapper.getforeignVatFormatFlg("0051", inputParam.getCompanyID(), "001");
		String saleVatFormatFlg = commonmstdMapper.getforeignVatFormatFlg("0052", inputParam.getCompanyID(), "002");
		String Pdfflg = commonMethedMapper.selPdfflg("0070","004",Integer.valueOf(inputParam.getCompanyID()));
		outsoure.put("pdfflg", Pdfflg);
		outsoure.put("foreignFormatFlg", foreignFormatFlg);
		outsoure.put("saleVatFormatFlg", saleVatFormatFlg);
		JobListInput jobInput = new JobListInput();
		jobInput.setDlvfalg("005");//job计上济
		jobInput.setJob_cd(inputParam.getJob_cd());
		jobInput.setCompanyID(inputParam.getCompanyID());
		List<JobList> joblist=jobmapper.selectjobStatus(jobInput);
		outsoure.put("joblist", joblist);
		
		inputParam.setCompany_cd(inputParam.getCompanyID());
		List<Pay> pay = payMapper.selectPayInfo(inputParam);
		
		//支付更新時查询是否是最新税率
		if(pay.size()>0) {  
			@SuppressWarnings("unused")
			CostInput costup = new CostInput();
			cost1.setPayee_cd(outinfo.get(0).getPayeecd());
			cost1.setPlan_dlvdays(outinfo.get(0).getPlandlvday());
			cost1.setCompanyID(inputParam.getCompanyID());
			if(pay.get(0).getInvoicetext()!=null&&!pay.get(0).getInvoicetext().equals("")&&pay.get(0).getInvoicetype()!=null&&!pay.get(0).getInvoicetype().equals("")) {
				cost1.setInvoice_text(pay.get(0).getInvoicetext());
				cost1.setInvoice_type(pay.get(0).getInvoicetype());
			}else {
				cost1.setInvoice_text(outinfo.get(0).getInvoicetext());
				cost1.setInvoice_type(outinfo.get(0).getInvoicetype());
			}
			List<Cost> listup = costMapper.selectTax(cost1);
			if(listup.size()>0) {
				outinfo.get(0).setCostVatRate(listup.get(0).getVatrate().toString());
			}else {
				outinfo.get(0).setCostVatRate(pay.get(0).getPayrate().toString());
			}
			outsoure.put("outinfo", outinfo);
		}
		//登录时间添加时区
		if(pay.size()>0) {
			if(!"".equals(pay.get(0).getAdddate())) {
				pay.get(0).setPayAdddate(pay.get(0).getAdddate());
				pay.get(0).setAdddate(commonMethedService.getTimeByZone(pay.get(0).getAdddate(),inputParam.getCompanyID()));		
			}
			//更新时间
			if(!"".equals(pay.get(0).getUpdate())) {
				pay.get(0).setUpdate(commonMethedService.getTimeByZone(pay.get(0).getUpdate(),inputParam.getCompanyID()));		
			}
		}else {
			outsoure.put("messge", "DATA_IS_NOT_EXIST_PAYUPDATE");
			return outsoure;
		}
		outsoure.put("pay", pay);
		
		return outsoure;
	}

	/**   
	 * <p>Title: initInvoice</p>   
	 * <p>Description: </p>   
	 * @param inputParam
	 * @return   
	 * @see com.kaiwait.service.jczh.PayService#initInvoice(com.kaiwait.bean.jczh.io.CostInput)
	 * @author "马有翼"
	 * @date:   2018年9月19日 下午2:25:15 
	 */ 
	public Map<String, Object> initInvoice(CostInput inputParam) {
		HashMap<String, Object> outsoure = new HashMap<String, Object>();

		// 发票种类
		List<Cost> PayeeType = costMapper.selectPayeeType("0007", inputParam.getCompanyID());
		// 发票内容
		List<Cost> Payeetext = costMapper.selectPayeeType("0008", inputParam.getCompanyID());
		outsoure.put("invoice_type", PayeeType);
		outsoure.put("invoice_text", Payeetext);
		return outsoure;
	}

	/**   
	 * <p>Title: updatePayInfoTx</p>   
	 * <p>Description: </p>   
	 * @param inputParam
	 * @return   
	 * @see com.kaiwait.service.jczh.PayService#updatePayInfoTx(com.kaiwait.bean.jczh.io.PayInput)
	 * @author "马有翼"
	 * @date:   2018年9月19日 下午2:24:54 
	 */ 
	public Map<String, Object> updatePayInfoTx(PayInput inputParam) {
		HashMap<String, Object> outscoure = new HashMap<>();
		//原件没登录不可以更新
		List<Cost> cost= costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID());
		CostInput costInput =new CostInput();
		costInput.setJob_cd(inputParam.getJob_cd());
		costInput.setCompany_cd(inputParam.getCompanyID());
		costInput.setInput_no(inputParam.getInput_no());
		costInput.setCost_no(inputParam.getCost_no());
		List<Pay> pay = payMapper.selectPayInfo(costInput);
			if(pay.size()<1) {
				outscoure.put("messge", "SYS_VALIDATEPOWER_ERROR");
				outscoure.put("num", 0);
				return  outscoure;
			}
			if(cost.size()>0) {
				if(pay.get(0).getLockflg()==inputParam.getLockflg()) {
					//支付登录状态可以做支付更新
					if("1".equals(cost.get(0).getStatus())){
						outscoure.put("messge", "SYS_VALIDATEPOWER_OK");	
					}else {
						outscoure.put("messge", "SYS_VALIDATEPOWER_ERROR");
						outscoure.put("num", 0);
						return  outscoure;
					}
				}else {
					outscoure.put("messge", "SYS_VALIDATEPOWER_ERROR");
					outscoure.put("num", 0);
					return  outscoure;
				}
					
			}else {
				outscoure.put("messge", "SYS_VALIDATEPOWER_ERROR");
				outscoure.put("num", 0);
				return outscoure;
			}
			//验证发驻日与预定纳品日规则
		if(inputParam.getPay_dlyday().compareTo(inputParam.getPay_hope_date())>0){
			outscoure.put("messge", "PAY_DLYDAYMORE");
			outscoure.put("num", 0);
			return outscoure;
		}
		
		int orderInvoiceFlg = commonmstdMapper.getOrderInvoiceNoFlg(inputParam.getCompanyID());
		if(orderInvoiceFlg == 1 && StringUtil.isEmpty(inputParam.getInvoice_no()))
		{
			outscoure.put("messge", "VALIDATE_FORMAT_ERROR");
			outscoure.put("num", 0);
			return  outscoure;
		}
		
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		inputParam.setUpdate(dateformat.format(date));
		inputParam.setUpusercd(inputParam.getUserID());
		List<JobLableTrn> jltrn = inputParam.getLableList();
		if (jltrn == null || jltrn.size() == 0) {
			jltrn = null;
		} else {
			for (int i = 0; i < jltrn.size(); i++) {
				jltrn.get(i).setCostno(inputParam.getCost_no());
				jltrn.get(i).setLablelevel(0);// 外发，支付。
				jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
				jltrn.get(i).setUpdDate(dateformat.format(date));
				jltrn.get(i).setUpUsercd(inputParam.getUserID());
				jltrn.get(i).setAddDate(dateformat.format(date));
				jltrn.get(i).setAddUsercd(inputParam.getUserID());

			}
			jobLandMapper.deleteByJobCd(null, inputParam.getCost_no(), Integer.valueOf(inputParam.getCompanyID()),
					"orderlabeltrn");
			costMapper.insertCostLable(jltrn);
		}
		
		// 更新支付信息
		inputParam.setCompany_cd(inputParam.getCompanyID());
		inputParam.setCompany_cd(inputParam.getCompanyID());
		inputParam.setAddusercd(inputParam.getUserID());
		inputParam.setUpusercd(inputParam.getUserID());
		inputParam.setCompany_cd(inputParam.getCompanyID());
		inputParam.setCost_no(inputParam.getInput_no());
		int num = payMapper.updatePayInfoTx(inputParam);
		inputParam.setDel_flg("0");
		//更新外发表中input——no
		costInput.setInput_no(inputParam.getNewinput_no());
		costInput.setUpdate(dateformat.format(date));
		costInput.setUpusercd(inputParam.getUserID());
		costMapper.updateCostinputno(costInput);
		//更新发票表
		payMapper.updateInvoiceintrnTx(inputParam);
		LendtrnMapper.deleteProoftrn(inputParam.getCompanyID(), inputParam.getInput_no());
		if (inputParam.getList_rooftrn() != null&&inputParam.getList_rooftrn().size()>0) {
			LendtrnMapper.insertProoftrn(inputParam.getList_rooftrn(), inputParam.getUserID(),
					inputParam.getCompanyID(), inputParam.getNewinput_no(), dateformat.format(date));
		}
		
		
		/*OutPutInput  outPutInput= new OutPutInput();
		outPutInput.setInputNo(inputParam.getInput_no());
		outPutInput.setCompanyID(inputParam.getCompanyID());
		String pdfOut=PayConfirmOutPDF(outPutInput);
		outscoure.put("pdfOut", pdfOut);*/
		
		//2019/6/25添加，支付申请跳过时，插入支付申请者和时间。但状态不更新成支付申请济（咱也不知道为什么咱也不敢问）
		Skip skip = commonMethedMapper.selectSkipByCd(Integer.parseInt(inputParam.getCompanyID()));
		if(skip.getPay()==1) {//支付申请跳过
			inputParam.setInput_no(inputParam.getNewinput_no());
			 payMapper.updatePayREQTx(inputParam);	
		}
		outscoure.put("num", num);
		return outscoure;
	}

	/**   
	 * <p>Title: deletePayInfoTx</p>   
	 * <p>Description: </p>   
	 * @param inputParam
	 * @return   
	 * @see com.kaiwait.service.jczh.PayService#deletePayInfoTx(com.kaiwait.bean.jczh.io.PayInput)
	 * @author "马有翼"
	 * @date:   2018年9月19日 下午2:24:39 
	 */ 
	public int deletePayInfoTx(PayInput inputParam) {
		int num = 0;
		JobListInput jobInput = new JobListInput();
//		jobInput.setDlvfalg("005");//job计上济
//		jobInput.setJob_cd(inputParam.getJob_cd());
//		jobInput.setCompanyID(inputParam.getCompanyID());
//		List<JobList> joblist=jobmapper.selectjobStatus(jobInput);
//		if(joblist.size()<1) {
			List<Cost> cost= costMapper.selectCostInfo(inputParam.getJob_cd(),inputParam.getCost_no(),inputParam.getCompanyID()); 
			if(cost.size()>0) {
				CostInput costInput =new CostInput();
				costInput.setJob_cd(inputParam.getJob_cd());
				costInput.setCompany_cd(inputParam.getCompanyID());
				costInput.setInput_no(inputParam.getInput_no());
				costInput.setCost_no(inputParam.getCost_no());
				List<Pay> pay = payMapper.selectPayInfo(costInput);
				if(pay.size()<1) {
					return 3;		
				}
				if(pay.get(0).getLockflg()==inputParam.getLockflg()) {
					//支付登录济，可做删除
					if("1".equals(cost.get(0).getStatus())){
						Date date = new Date();
						SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						inputParam.setUpdate(dateformat.format(date));
						inputParam.setCompany_cd(inputParam.getCompanyID());
						inputParam.setUpusercd(inputParam.getUserID());
						payMapper.deletePayInfoTx(inputParam);
						inputParam.setInput_no("");
						num=costMapper.updateCostStatus(inputParam);
					}else {
						//不是支付登录济，不可删除
						num=3;
					}	
				}else{
					//更新锁
					num=3;
				}
			}else {
				//外发未登录，不可删除支付信息
				     num=3;
			}
//			}else {
//			//job计上济
//			num=2;
//		}
		return num;			
	}

	@Override
	public String PayConfirmOutPDF(OutPutInput inputParam) throws JRException {
		int foreigndecimal =0;
		String sumDebtorAmt = "";
		PayConfirmPDF payoutPDF= payMapper.PayConfirmOutPDF(inputParam);
		if(payoutPDF.getAddUserName().equals("")||payoutPDF.getAddUserName()==null) {
			String addUserName = payMapper.selectUserName(payoutPDF.getCompanycd(),payoutPDF.getInputno());
			payoutPDF.setAddUserName(addUserName);
		}
		if(inputParam.getFileName().equals("payRequest")) {
			payoutPDF.setPayeename(payoutPDF.getDivnamefull());
		}
		if(!"".equals(payoutPDF.getForeigndecimal())) {
			foreigndecimal =Integer.valueOf(payoutPDF.getForeigndecimal());
		}
		int Localdecimal =Integer.valueOf(payoutPDF.getLocaldecimal());
		
		//判断是否有外货，若没有外货，显示本国货币
		if("".equals(payoutPDF.getPayforeigntype())) {
			payoutPDF.setForeignmoney(payoutPDF.getLocalmoney());
			payoutPDF.setForeignmoneyen(payoutPDF.getLocalmoneyen());
			payoutPDF.setForeignmoneyjp(payoutPDF.getLocalmoneyjp());
			payoutPDF.setForeignmoneyhk(payoutPDF.getLocalmoneyhk());
			foreigndecimal=Localdecimal;
		}
				
		//时区转换，并截掉时分秒
		if(!"".equals(payoutPDF.getPayadddate())) {
			String adddate =commonMethedService.getTimeByZone(payoutPDF.getPayadddate(),payoutPDF.getCompanycd());
			String Payadddate =adddate.substring(0, adddate.length()-9);
			payoutPDF.setPayadddate(Payadddate);
		}
		if(!"".equals(payoutPDF.getPayreqdate())) {
			String adddate =commonMethedService.getTimeByZone(payoutPDF.getPayreqdate(),payoutPDF.getCompanycd());
			String Payadddate =adddate.substring(0, adddate.length()-9);
			payoutPDF.setPayreqdate(Payadddate);
		}
		if(!"".equals(payoutPDF.getConfirmdate())) {
			String adddate =commonMethedService.getTimeByZone(payoutPDF.getConfirmdate(),payoutPDF.getCompanycd());
			String Payadddate =adddate.substring(0, adddate.length()-9);
			payoutPDF.setConfirmdate(Payadddate);
		}
		if(!"".equals(payoutPDF.getCostadddate())) {
			String adddate =commonMethedService.getTimeByZone(payoutPDF.getCostadddate(),payoutPDF.getCompanycd());
			String Payadddate =adddate.substring(0, adddate.length()-9);
			payoutPDF.setCostadddate(Payadddate);
		}
		
		//科学记数法,以及小数点
		//发注金额
		if(!"".equals(payoutPDF.getCostrmb())) {
		payoutPDF.setCostrmb(commonMethedService.getNewNum(payoutPDF.getCostrmb(),Localdecimal));
		}
		//支付金额
		if(!"".equals(payoutPDF.getPayrmb())) {
		payoutPDF.setPayrmb(commonMethedService.getNewNum(payoutPDF.getPayrmb(),Localdecimal));
		}
		//支付原价
		if(!"".equals(payoutPDF.getPayamt())) {
			payoutPDF.setPayamt(commonMethedService.getNewNum(payoutPDF.getPayamt(),Localdecimal));
		}
		//增值税
		if(!"".equals(payoutPDF.getPayvatamt())) {
			payoutPDF.setPayvatamt(commonMethedService.getNewNum(payoutPDF.getPayvatamt(),Localdecimal));
		}
		//入力金额
		if(!"".equals(payoutPDF.getPayforeignamt())) {
			payoutPDF.setPayforeignamt(commonMethedService.getNewNum(payoutPDF.getPayforeignamt(),foreigndecimal));
		}
		//換算code去除0
		if(!"".equals(payoutPDF.getPaycurecode())) {
					payoutPDF.setPaycurecode(String.valueOf(Double.parseDouble(payoutPDF.getPaycurecode())));
		}
		if(!"".equals(payoutPDF.getDebtorAmt())&&payoutPDF.getDebtorAmt()!=null&&!"".equals(payoutPDF.getDebtorAmt1())&&payoutPDF.getDebtorAmt1()!=null)
		{
			sumDebtorAmt = String.valueOf(MathController.add(Double.valueOf(payoutPDF.getDebtorAmt()),Double.valueOf(payoutPDF.getDebtorAmt1()))); 
		}
		if(!"".equals(payoutPDF.getDebtorAmt()))
		{
			payoutPDF.setDebtorAmt(commonMethedService.getNewNum(payoutPDF.getDebtorAmt(),Localdecimal));
		}
		if(!"".equals(payoutPDF.getDebtorAmt1()))
		{
			payoutPDF.setDebtorAmt1(commonMethedService.getNewNum(payoutPDF.getDebtorAmt1(),Localdecimal));
		}
		if(!"".equals(payoutPDF.getCreditAmt()))
		{
			payoutPDF.setCreditAmt(commonMethedService.getNewNum(sumDebtorAmt,Localdecimal));
		}
		if(!"".equals(payoutPDF.getCreditAmt1()))
		{
			payoutPDF.setCreditAmt1(commonMethedService.getNewNum(payoutPDF.getCreditAmt1(),Localdecimal));
		}
		if(payoutPDF.getTypeName()==null) {
			payoutPDF.setTypeName("");
		}
		if(payoutPDF.getTypeName1()==null) {
			payoutPDF.setTypeName1("");
		}
		if(payoutPDF.getTypeName2()==null) {
			payoutPDF.setTypeName2("");
		}
		Locale local = JasperOutPdfUtil.getLocalByLangT(inputParam.getLangTyp());
		ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		
		   //税入税拔
			if("1".equals(payoutPDF.getPayishave())) {
				payoutPDF.setPayishave(bundle.getString("haveTax"));
				//税入
			}else {
				payoutPDF.setPayishave(bundle.getString("notHaveTax"));
				//税拔
			}
			//扣除可否
			if("1".equals(payoutPDF.getDeductionflg())) {
				payoutPDF.setDeductionflg(bundle.getString("isYes"));
			}else {
				payoutPDF.setDeductionflg(bundle.getString("isNo"));
			}


		List<PayConfirmPDF> jbs = new ArrayList<PayConfirmPDF>();
		jbs.add(payoutPDF);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, inputParam.getFileName(), inputParam.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	
	
	
}
