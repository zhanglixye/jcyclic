package com.kaiwait.bean.jczh.io;

import java.util.List;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.Costtrn;
import com.kaiwait.bean.jczh.entity.Invoiceintrn;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.Paytrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.entity.Skip;
import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class PaytrnInput extends BaseInputBean {

	private static final long serialVersionUID = 5085368117650161941L;

	private Paytrn paytrn;

	private String job_cd;

	private List<Paytrn> paytrnList;

	private List<Lable> list_lable;

	private List<Cost> Foreign;

	private List<Costtrn> costtrnList;

	private List<Prooftrn> list_rooftrn;
	
	private List<Invoiceintrn> InvoiceintrnList;
	
	private Skip skip;
	
	List<JobLableTrn> paylable;
	
	private List<JobLableTrn>  lableList;

	// 振替登录经费科目list
	private List<CommonmstVo> itemList;
	
    private String pdfflagpro;
    private String pdfflagcri;
    
    
	List<JobLableTrn> costlable;
	 private String payreqremark;
	 
	 
	public String getPayreqremark() {
		return payreqremark;
	}

	public void setPayreqremark(String payreqremark) {
		this.payreqremark = payreqremark;
	}

	public String getPdfflagpro() {
		return pdfflagpro;
	}

	public void setPdfflagpro(String pdfflagpro) {
		this.pdfflagpro = pdfflagpro;
	}

	public String getPdfflagcri() {
		return pdfflagcri;
	}

	public void setPdfflagcri(String pdfflagcri) {
		this.pdfflagcri = pdfflagcri;
	}

	public List<JobLableTrn> getCostlable() {
		return costlable;
	}

	public void setCostlable(List<JobLableTrn> costlable) {
		this.costlable = costlable;
	}

	public List<Invoiceintrn> getInvoiceintrnList() {
		return InvoiceintrnList;
	}

	public void setInvoiceintrnList(List<Invoiceintrn> invoiceintrnList) {
		InvoiceintrnList = invoiceintrnList;
	}


	public List<Costtrn> getCosttrnList() {
		return costtrnList;
	}

	public void setCosttrnList(List<Costtrn> costtrnList) {
		this.costtrnList = costtrnList;
	}

	public Paytrn getPaytrn() {
		return paytrn;
	}

	public void setPaytrn(Paytrn paytrn) {
		this.paytrn = paytrn;
	}

	public String getJob_cd() {
		return job_cd;
	}

	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}

	public List<Paytrn> getPaytrnList() {
		return paytrnList;
	}

	public void setPaytrnList(List<Paytrn> PaytrnList) {
		this.paytrnList = PaytrnList;
	}

	public List<Lable> getList_lable() {
		return list_lable;
	}

	public void setList_lable(List<Lable> list_lable) {
		this.list_lable = list_lable;
	}

	public List<CommonmstVo> getItemList() {
		return itemList;
	}

	public void setItemList(List<CommonmstVo> itemList) {
		this.itemList = itemList;
	}

	public List<Cost> getForeign() {
		return Foreign;
	}

	public void setForeign(List<Cost> foreign) {
		Foreign = foreign;
	}

	public List<Prooftrn> getList_rooftrn() {
		return list_rooftrn;
	}

	public void setList_rooftrn(List<Prooftrn> list_rooftrn) {
		this.list_rooftrn = list_rooftrn;
	}

	public Skip getSkip() {
		return skip;
	}

	public void setSkip(Skip skip) {
		this.skip = skip;
	}

	public List<JobLableTrn> getPaylable() {
		return paylable;
	}

	public void setPaylable(List<JobLableTrn> paylable) {
		this.paylable = paylable;
	}

	public List<JobLableTrn> getLableList() {
		return lableList;
	}

	public void setLableList(List<JobLableTrn> lableList) {
		this.lableList = lableList;
	}

}