package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class SaleType {
  private String job_cd;
  private String sale_remark; //备考
  private String saleadddate;//上传日期
  private String saleupddate;//更新日期
  private String saleaddusercd;//上传者
  private String saleadduserName;//
  private String saleupdusercd;//更新者
  private String saleupduserName;//
  private String saleaddcolor;
  private String saleupcolor;
  private String sale_no;//卖上标号
  private String sale_amt;//卖上金额
  private String dlvday;//终了日
  private String sale_foreign_amt;//外货金额
  private String sale_use_date;//适用日
  private String sale_cure_code;//汇率
  private String sale_refer;//参照先
  private String sale_foreign_code;//
  private int sale_ishave;//税入税拔
  private String sale_vat_amt;//卖上增值税
  private int del_flg;//有效标识
  private int company_cd;//公司id
  private String vat_amt;//税金
  private String req_amt;//请求金额
  private String sale_tax1;//税金1
  private String sale_tax2;//税金2
  private String sale_admit_usercd;//承认者id
  private String sale_admit_userName;//承认者名字
  private String sale_admit_date;//承认日期
  private String sale_foreign_type;//外货货币种类编号
  private String plan_req_date;// 预计请求日
  private com.kaiwait.bean.jczh.vo.JobLandVo JobLandVo;//
  private String upname;//上传者
  private List<JobLableTrn> jlTrn;// 与job绑定的lable集合
  private String reqUserName;//请求书人名
  private String invoiceUserName;//发注书 人名
  private ReqInfo req;//请求书类
  private InvoiceInfo inv;//发注书类
  private int reqFlg;//请求书   1：发行  0：不发行
  private int invFlg;//发注书书 1 发行，0：不发行
  private int confirmskipflg;//跳过标识
  private String salecanceldate;//卖上取消日期
  private String salecancelusercd;//卖上取消者
  private RealSaleCost realcost;//实际原价
  private Company company;
  private int saleVatChangeFlg;
  private int saleLockFlg;
  private int jobLockFlg;
  private int recLockFlg;
  
public int getRecLockFlg() {
	return recLockFlg;
}
public void setRecLockFlg(int recLockFlg) {
	this.recLockFlg = recLockFlg;
}
public int getJobLockFlg() {
	return jobLockFlg;
}
public void setJobLockFlg(int jobLockFlg) {
	this.jobLockFlg = jobLockFlg;
}
public int getSaleLockFlg() {
	return saleLockFlg;
}
public void setSaleLockFlg(int saleLockFlg) {
	this.saleLockFlg = saleLockFlg;
}
public String getSaleaddcolor() {
	return saleaddcolor;
}
public void setSaleaddcolor(String saleaddcolor) {
	this.saleaddcolor = saleaddcolor;
}
public String getSaleupcolor() {
	return saleupcolor;
}
public void setSaleupcolor(String saleupcolor) {
	this.saleupcolor = saleupcolor;
}
public int getSaleVatChangeFlg() {
	return saleVatChangeFlg;
}
public void setSaleVatChangeFlg(int saleVatChangeFlg) {
	this.saleVatChangeFlg = saleVatChangeFlg;
}
public Company getCompany() {
	return company;
}
public void setCompany(Company company) {
	this.company = company;
}
public RealSaleCost getRealcost() {
	return realcost;
}
public void setRealcost(RealSaleCost realcost) {
	this.realcost = realcost;
}
public String getSalecanceldate() {
	return salecanceldate;
}
public void setSalecanceldate(String salecanceldate) {
	this.salecanceldate = salecanceldate;
}
public String getSalecancelusercd() {
	return salecancelusercd;
}
public void setSalecancelusercd(String salecancelusercd) {
	this.salecancelusercd = salecancelusercd;
}
public int getConfirmskipflg() {
	return confirmskipflg;
}
public void setConfirmskipflg(int confirmskipflg) {
	this.confirmskipflg = confirmskipflg;
}
public String getJob_cd() {
	return job_cd;
}
public void setJob_cd(String job_cd) {
	this.job_cd = job_cd;
}
public String getSale_remark() {
	return sale_remark;
}
public void setSale_remark(String sale_remark) {
	this.sale_remark = sale_remark;
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
public String getSaleaddusercd() {
	return saleaddusercd;
}
public void setSaleaddusercd(String saleaddusercd) {
	this.saleaddusercd = saleaddusercd;
}
public String getSaleadduserName() {
	return saleadduserName;
}
public void setSaleadduserName(String saleadduserName) {
	this.saleadduserName = saleadduserName;
}
public String getSaleupdusercd() {
	return saleupdusercd;
}
public void setSaleupdusercd(String saleupdusercd) {
	this.saleupdusercd = saleupdusercd;
}
public String getSaleupduserName() {
	return saleupduserName;
}
public void setSaleupduserName(String saleupduserName) {
	this.saleupduserName = saleupduserName;
}
public String getSale_no() {
	return sale_no;
}
public void setSale_no(String sale_no) {
	this.sale_no = sale_no;
}
public String getSale_amt() {
	return sale_amt;
}
public void setSale_amt(String sale_amt) {
	this.sale_amt = sale_amt;
}
public String getDlvday() {
	return dlvday;
}
public void setDlvday(String dlvday) {
	this.dlvday = dlvday;
}
public String getSale_foreign_amt() {
	return sale_foreign_amt;
}
public void setSale_foreign_amt(String sale_foreign_amt) {
	this.sale_foreign_amt = sale_foreign_amt;
}
public String getSale_use_date() {
	return sale_use_date;
}
public void setSale_use_date(String sale_use_date) {
	this.sale_use_date = sale_use_date;
}
public String getSale_cure_code() {
	return sale_cure_code;
}
public void setSale_cure_code(String sale_cure_code) {
	this.sale_cure_code = sale_cure_code;
}
public String getSale_refer() {
	return sale_refer;
}
public void setSale_refer(String sale_refer) {
	this.sale_refer = sale_refer;
}
public String getSale_foreign_code() {
	return sale_foreign_code;
}
public void setSale_foreign_code(String sale_foreign_code) {
	this.sale_foreign_code = sale_foreign_code;
}
public int getSale_ishave() {
	return sale_ishave;
}
public void setSale_ishave(int sale_ishave) {
	this.sale_ishave = sale_ishave;
}
public String getSale_vat_amt() {
	return sale_vat_amt;
}
public void setSale_vat_amt(String sale_vat_amt) {
	this.sale_vat_amt = sale_vat_amt;
}
public int getDel_flg() {
	return del_flg;
}
public void setDel_flg(int del_flg) {
	this.del_flg = del_flg;
}
public int getCompany_cd() {
	return company_cd;
}
public void setCompany_cd(int company_cd) {
	this.company_cd = company_cd;
}
public String getVat_amt() {
	return vat_amt;
}
public void setVat_amt(String vat_amt) {
	this.vat_amt = vat_amt;
}
public String getReq_amt() {
	return req_amt;
}
public void setReq_amt(String req_amt) {
	this.req_amt = req_amt;
}
public String getSale_tax1() {
	return sale_tax1;
}
public void setSale_tax1(String sale_tax1) {
	this.sale_tax1 = sale_tax1;
}
public String getSale_tax2() {
	return sale_tax2;
}
public void setSale_tax2(String sale_tax2) {
	this.sale_tax2 = sale_tax2;
}

public String getSale_admit_usercd() {
	return sale_admit_usercd;
}
public void setSale_admit_usercd(String sale_admit_usercd) {
	this.sale_admit_usercd = sale_admit_usercd;
}
public String getSale_admit_userName() {
	return sale_admit_userName;
}
public void setSale_admit_userName(String sale_admit_userName) {
	this.sale_admit_userName = sale_admit_userName;
}
public String getSale_admit_date() {
	return sale_admit_date;
}
public void setSale_admit_date(String sale_admit_date) {
	this.sale_admit_date = sale_admit_date;
}
public String getSale_foreign_type() {
	return sale_foreign_type;
}
public void setSale_foreign_type(String sale_foreign_type) {
	this.sale_foreign_type = sale_foreign_type;
}
public String getPlan_req_date() {
	return plan_req_date;
}
public void setPlan_req_date(String plan_req_date) {
	this.plan_req_date = plan_req_date;
}
public com.kaiwait.bean.jczh.vo.JobLandVo getJobLandVo() {
	return JobLandVo;
}
public void setJobLandVo(com.kaiwait.bean.jczh.vo.JobLandVo jobLandVo) {
	JobLandVo = jobLandVo;
}
public String getUpname() {
	return upname;
}
public void setUpname(String upname) {
	this.upname = upname;
}
public List<JobLableTrn> getJlTrn() {
	return jlTrn;
}
public void setJlTrn(List<JobLableTrn> jlTrn) {
	this.jlTrn = jlTrn;
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
public ReqInfo getReq() {
	return req;
}
public void setReq(ReqInfo req) {
	this.req = req;
}
public InvoiceInfo getInv() {
	return inv;
}
public void setInv(InvoiceInfo inv) {
	this.inv = inv;
}
public int getReqFlg() {
	return reqFlg;
}
public void setReqFlg(int reqFlg) {
	this.reqFlg = reqFlg;
}
public int getInvFlg() {
	return invFlg;
}
public void setInvFlg(int invFlg) {
	this.invFlg = invFlg;
}
}
