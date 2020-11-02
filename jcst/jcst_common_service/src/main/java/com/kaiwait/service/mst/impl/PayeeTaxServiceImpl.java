/**
 * 
 */
package com.kaiwait.service.mst.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.Payeetaxmst;
import com.kaiwait.bean.mst.io.PayeeTaxAddInput;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.PayeeTaxMapper;
import com.kaiwait.service.mst.PayeeTaxService;
import com.kaiwait.mappers.mst.CommonmstMapper;

/**
 * @author Administrator
 *
 */
@Service
public class PayeeTaxServiceImpl implements PayeeTaxService {
	
	//private static Payeetaxmst payeetaxmst;
	@Resource
	private PayeeTaxMapper payeeTaxMapper;
	@Resource
	private CommonmstMapper commonMapper;


	//往来单位登录	
	public int insertPayeetaxmstTx(Payeetaxmst payeetaxmst) {
		 String user_tax_type = payeetaxmst.getUser_tax_type();
		 String invoice_type = payeetaxmst.getInvoice_type();
		 String invoice_text = payeetaxmst.getInvoice_text();
		 String company_cd = payeetaxmst.getCompany_cd();
		 String delflg = payeetaxmst.getDel_flg();
		 if(delflg.equals("0")){//有效
			 int flag = payeeTaxMapper.payeeCheck(user_tax_type,invoice_type,invoice_text,company_cd);
			 if(flag>0){
				return -1; 
			 } 
		 }
		 payeetaxmst.setAdddate(DateUtil.getNowTime());
		 payeetaxmst.setAddusercd(payeetaxmst.getAddusercd());
		 payeetaxmst.setUpddate(DateUtil.getNowTime());
		 payeetaxmst.setUpdusercd(payeetaxmst.getUpdusercd());
		return payeeTaxMapper.insertPayeetaxmstTx(payeetaxmst);
	}
	
	//往来单位修改	
	public int updatePayeetaxmstTx(Payeetaxmst payeetaxmst) {
		 String user_tax_type = payeetaxmst.getUser_tax_type();
		 String invoice_type = payeetaxmst.getInvoice_type();
		 String invoice_text = payeetaxmst.getInvoice_text();
		 String user_tax_type1 = payeetaxmst.getUser_tax_type1();
		 String invoice_type1 = payeetaxmst.getInvoice_type1();
		 String invoice_text1 = payeetaxmst.getInvoice_text1();
		 String company_cd = payeetaxmst.getCompany_cd();
		 payeetaxmst.setUpddate(DateUtil.getNowTime());
		 payeetaxmst.setUpdusercd(payeetaxmst.getUpdusercd());
		 int locknum = payeetaxmst.getLock_flg();
		 int locknumnow=payeeTaxMapper.payeeLock(payeetaxmst.getId());
		 if(locknumnow>locknum){
			 return -2; 
		 }
		 //修改税率或者delflag
		 if(user_tax_type.equals(user_tax_type1)&&invoice_type.equals(invoice_type1)&&invoice_text.equals(invoice_text1)) {
			 payeeTaxMapper.updateLock(payeetaxmst); 
			 return payeeTaxMapper.updatePayeetaxmstTx(payeetaxmst); 
		 }
		 else{
			 
			 int flag = payeeTaxMapper.payeeCheck(user_tax_type,invoice_type,invoice_text,company_cd);
			 if(flag>0){
				return -1; 
			 }  
			 payeeTaxMapper.updateLock(payeetaxmst); 
			 return payeeTaxMapper.updatePayeetaxmstTx(payeetaxmst);
		 }
			 
		 
		 

	}
	
	
	public PayeeTaxAddInput initSalescategory(PayeeTaxAddInput payeetaxmst){
		PayeeTaxAddInput payeeTax = new PayeeTaxAddInput();
		String companyID = payeetaxmst.getCompanyID();
		String user_tax_type = payeetaxmst.getPayeetaxmst().getUser_tax_type1();
		 String invoice_type = payeetaxmst.getPayeetaxmst().getInvoice_type1();
		 String invoice_text = payeetaxmst.getPayeetaxmst().getInvoice_text1();
		 String id = payeetaxmst.getPayeetaxmst().getId();
		 if(id!=null&&!"".equals(id)) {
			 int flag = payeeTaxMapper.queryHave(user_tax_type,invoice_type,invoice_text,companyID,id);
			 if(flag<1) {
				 return null;
			 } 
		 }

		payeetaxmst.getPayeetaxmst().setCompany_cd(companyID);
		//部门列表，下拉选框用
		payeeTax.setUserListone(commonMapper.selectMstNameByCDNO("0060",Integer.valueOf(companyID)));
		payeeTax.setInvoicetextList(commonMapper.selectMstNameByCDNO("0062",Integer.valueOf(companyID)));
		payeeTax.setInvoicetypeList(commonMapper.selectMstNameByCDNO("0061",Integer.valueOf(companyID)));
		if(id!=null&&!"".equals(id)){
			Payeetaxmst ss = payeeTaxMapper.changeQuery(payeetaxmst.getPayeetaxmst());
			payeeTax.setPayeetaxmst(ss);
		}		
		return payeeTax;
	}
	//获取主键编号
	public String querymaxno() {

		Map<String,Object> ss = payeeTaxMapper.querymaxno();
		int cldivcdd = Integer.parseInt(ss.get("num").toString())+1;
		//int cldivcd = cldivcdd;
		return cldivcdd+"";
		
	}
	public static void main(String[] args) {
		//String gsID = "5";
		PayeeTaxServiceImpl clmstServiceImpl =new PayeeTaxServiceImpl();
		clmstServiceImpl.querymaxno();
		
	}
	
	//成本税率列表
	public List<Payeetaxmst> queryPayeeTaxList(PayeeTaxAddInput payeetaxmst) {
		String companycd = payeetaxmst.getCompanyID();
		List<Payeetaxmst> payeetax = payeeTaxMapper.queryPayeeTaxList(companycd,payeetaxmst.getPayeetaxmst().getDel_flg());
		return payeetax;
	}
	


}
