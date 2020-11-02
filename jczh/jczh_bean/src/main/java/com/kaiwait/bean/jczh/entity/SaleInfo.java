package com.kaiwait.bean.jczh.entity;

import java.math.BigDecimal;
import java.util.List;

public class SaleInfo {
  private String jobcd="";
  private String saleremark="";
  private String saleadddate="";
  private String saleupddate="";
  private String saleaddusercd="";
  private String saleadduserName="";
  private String saleupdusercd="";
  private String saleupduserName="";
  private String saleno="";
  private String saleamt="";
  private String dlvday="";
  private String saleforeignamt;
  private String saleusedate="";
  private String salecurecode="";
  private String salerefer="";
  private String saleforeigncode="";
  private int saleishave;
  private BigDecimal salevatamt;
  private int delflg;
  private int companycd;
  private String vatamt="";
  private BigDecimal reqamt;
  private String saletax1="";
  private String saletax2="";
  private int saleadmitusercd;
  private String saleadmituserName="";
  private String saleadmitdate="";
  private String saleforeigntype="";
  private String planreqdate="";
  private com.kaiwait.bean.jczh.vo.JobLandVo JobLandVo;
  private String upname="";
  private List<JobLableTrn> jlTrn;
  private String reqUserName="";
  private String reqdate="";
  private String invoiceUserName="";
  private String invoicedate="";
  private String  costfinishuserName="";
  private String  costfinishdate="";
  private String  seladdflag="";
  private String   seladmitflag="";
  private String  salecanceldate="";
  private String   saleadmitcanceluserName="";
  private String  reqtimes="";
  private String invoicetimes="";
  private String saleadduserNamecolor="";
  private String saleupduserNamecolor="";
  private String saleadmituserNamecolor="";
  private String saleadmitcanceluserNamecolor="";
  private String reqUserNamecolor="";
  private String invoiceUserNamecolor="";
  private String costfinishuserNamecolor="";
  private String itmvalue="";
  private int saleFromJpp;
  private String saleTaxTotal;
  

public int getSaleFromJpp() {
	return saleFromJpp;
}
public void setSaleFromJpp(int saleFromJpp) {
	this.saleFromJpp = saleFromJpp;
}
public String getSaleTaxTotal() {
	return saleTaxTotal;
}
public void setSaleTaxTotal(String saleTaxTotal) {
	this.saleTaxTotal = saleTaxTotal;
}
public String getItmvalue() {
	return itmvalue;
}
public void setItmvalue(String itmvalue) {
	this.itmvalue = itmvalue;
}
public String getSaleadduserNamecolor() {
	return saleadduserNamecolor;
}
public void setSaleadduserNamecolor(String saleadduserNamecolor) {
	this.saleadduserNamecolor = saleadduserNamecolor;
}
public String getSaleupduserNamecolor() {
	return saleupduserNamecolor;
}
public void setSaleupduserNamecolor(String saleupduserNamecolor) {
	this.saleupduserNamecolor = saleupduserNamecolor;
}
public String getSaleadmituserNamecolor() {
	return saleadmituserNamecolor;
}
public void setSaleadmituserNamecolor(String saleadmituserNamecolor) {
	this.saleadmituserNamecolor = saleadmituserNamecolor;
}
public String getSaleadmitcanceluserNamecolor() {
	return saleadmitcanceluserNamecolor;
}
public void setSaleadmitcanceluserNamecolor(String saleadmitcanceluserNamecolor) {
	this.saleadmitcanceluserNamecolor = saleadmitcanceluserNamecolor;
}
public String getReqUserNamecolor() {
	return reqUserNamecolor;
}
public void setReqUserNamecolor(String reqUserNamecolor) {
	this.reqUserNamecolor = reqUserNamecolor;
}
public String getInvoiceUserNamecolor() {
	return invoiceUserNamecolor;
}
public void setInvoiceUserNamecolor(String invoiceUserNamecolor) {
	this.invoiceUserNamecolor = invoiceUserNamecolor;
}
public String getCostfinishuserNamecolor() {
	return costfinishuserNamecolor;
}
public void setCostfinishuserNamecolor(String costfinishuserNamecolor) {
	this.costfinishuserNamecolor = costfinishuserNamecolor;
}
public String getReqtimes() {
	return reqtimes;
}
public void setReqtimes(String reqtimes) {
	this.reqtimes = reqtimes;
}
public String getInvoicetimes() {
	return invoicetimes;
}
public void setInvoicetimes(String invoicetimes) {
	this.invoicetimes = invoicetimes;
}
public String getSaleadmitcanceluserName() {
	return saleadmitcanceluserName;
}
public void setSaleadmitcanceluserName(String saleadmitcanceluserName) {
	this.saleadmitcanceluserName = saleadmitcanceluserName;
}
public String getSalecanceldate() {
	return salecanceldate;
}
public void setSalecanceldate(String salecanceldate) {
	this.salecanceldate = salecanceldate;
}
public String getSeladdflag() {
	return seladdflag;
}
public void setSeladdflag(String seladdflag) {
	this.seladdflag = seladdflag;
}
public String getSeladmitflag() {
	return seladmitflag;
}
public void setSeladmitflag(String seladmitflag) {
	this.seladmitflag = seladmitflag;
}
public String getCostfinishuserName() {
	return costfinishuserName;
}
public void setCostfinishuserName(String costfinishuserName) {
	this.costfinishuserName = costfinishuserName;
}
public String getCostfinishdate() {
	return costfinishdate;
}
public void setCostfinishdate(String costfinishdate) {
	this.costfinishdate = costfinishdate;
}
public String getReqdate() {
	return reqdate;
}
public void setReqdate(String reqdate) {
	this.reqdate = reqdate;
}
public String getInvoicedate() {
	return invoicedate;
}
public void setInvoicedate(String invoicedate) {
	this.invoicedate = invoicedate;
}
public String getSaleadduserName() {
	return saleadduserName;
}
public void setSaleadduserName(String saleadduserName) {
	this.saleadduserName = saleadduserName;
}
public String getSaleupduserName() {
	return saleupduserName;
}
public void setSaleupduserName(String saleupduserName) {
	this.saleupduserName = saleupduserName;
}
public String getSaleadmituserName() {
	return saleadmituserName;
}
public void setSaleadmituserName(String saleadmituserName) {
	this.saleadmituserName = saleadmituserName;
}
public String getReqUserName() {
	return reqUserName;
}
public void setReqUserName(String reqUserName) {
	this.reqUserName = reqUserName;
}
public String getInvoiceUserName() {
	return invoiceUserName;
}
public void setInvoiceUserName(String invoiceUserName) {
	this.invoiceUserName = invoiceUserName;
}
public List<JobLableTrn> getJlTrn() {
	return jlTrn;
}
public void setJlTrn(List<JobLableTrn> jlTrn) {
	this.jlTrn = jlTrn;
}
public String getUpname() {
	return upname;
}
public void setUpname(String upname) {
	this.upname = upname;
}
public String getSaleamt() {
	return saleamt;
}
public void setSaleamt(String saleamt) {
	this.saleamt = saleamt;
}
public String getSaleforeignamt() {
	return saleforeignamt;
}
public void setSaleforeignamt(String saleforeignamt) {
	this.saleforeignamt = saleforeignamt;
}
public BigDecimal getSalevatamt() {
	return salevatamt;
}
public void setSalevatamt(BigDecimal salevatamt) {
	this.salevatamt = salevatamt;
}
public String getVatamt() {
	return vatamt;
}
public void setVatamt(String vatamt) {
	this.vatamt = vatamt;
}
public BigDecimal getReqamt() {
	return reqamt;
}
public void setReqamt(BigDecimal reqamt) {
	this.reqamt = reqamt;
}
public String getSaletax1() {
	return saletax1;
}
public void setSaletax1(String saletax1) {
	this.saletax1 = saletax1;
}
public String getSaletax2() {
	return saletax2;
}
public void setSaletax2(String saletax2) {
	this.saletax2 = saletax2;
}
public com.kaiwait.bean.jczh.vo.JobLandVo getJobLandVo() {
	return JobLandVo;
}
public void setJobLandVo(com.kaiwait.bean.jczh.vo.JobLandVo jobLandVo) {
	JobLandVo = jobLandVo;
}
public String getJobcd() {
	return jobcd;
}
public void setJobcd(String jobcd) {
	this.jobcd = jobcd;
}
public String getSaleremark() {
	return saleremark;
}
public void setSaleremark(String saleremark) {
	this.saleremark = saleremark;
}
public String getSaleaddusercd() {
	return saleaddusercd;
}
public void setSaleaddusercd(String saleaddusercd) {
	this.saleaddusercd = saleaddusercd;
}
public String getSaleupdusercd() {
	return saleupdusercd;
}
public void setSaleupdusercd(String saleupdusercd) {
	this.saleupdusercd = saleupdusercd;
}
public String getSaleno() {
	return saleno;
}
public void setSaleno(String saleno) {
	this.saleno = saleno;
}
public String getSalecurecode() {
	return salecurecode;
}
public void setSalecurecode(String salecurecode) {
	this.salecurecode = salecurecode;
}
public String getSalerefer() {
	return salerefer;
}
public void setSalerefer(String salerefer) {
	this.salerefer = salerefer;
}
public String getSaleforeigncode() {
	return saleforeigncode;
}
public void setSaleforeigncode(String saleforeigncode) {
	this.saleforeigncode = saleforeigncode;
}
public int getSaleishave() {
	return saleishave;
}
public void setSaleishave(int saleishave) {
	this.saleishave = saleishave;
}
public int getDelflg() {
	return delflg;
}
public void setDelflg(int delflg) {
	this.delflg = delflg;
}
public int getCompanycd() {
	return companycd;
}
public void setCompanycd(int companycd) {
	this.companycd = companycd;
}
public int getSaleadmitusercd() {
	return saleadmitusercd;
}
public void setSaleadmitusercd(int saleadmitusercd) {
	this.saleadmitusercd = saleadmitusercd;
}
public String getSaleforeigntype() {
	return saleforeigntype;
}
public void setSaleforeigntype(String saleforeigntype) {
	this.saleforeigntype = saleforeigntype;
}
public String getSaleadddate() {
	return saleadddate;
}
public void setSaleadddate(String saleadddate) {
	this.saleadddate = saleadddate;
}
public String getSaleupddate() {
	return saleupddate;
}
public void setSaleupddate(String saleupddate) {
	this.saleupddate = saleupddate;
}
public String getDlvday() {
	return dlvday;
}
public void setDlvday(String dlvday) {
	this.dlvday = dlvday;
}
public String getSaleusedate() {
	return saleusedate;
}
public void setSaleusedate(String saleusedate) {
	this.saleusedate = saleusedate;
}
public String getSaleadmitdate() {
	return saleadmitdate;
}
public void setSaleadmitdate(String saleadmitdate) {
	this.saleadmitdate = saleadmitdate;
}
public String getPlanreqdate() {
	return planreqdate;
}
public void setPlanreqdate(String planreqdate) {
	this.planreqdate = planreqdate;
}

	
}
