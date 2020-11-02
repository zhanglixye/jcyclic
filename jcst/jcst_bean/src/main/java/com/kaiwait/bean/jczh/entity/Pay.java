package com.kaiwait.bean.jczh.entity;

import java.math.BigDecimal;

public class Pay{
	private String  inputno="";
	private BigDecimal  payamt;
	private String  companycd="";
	private String  payforeigntype=""; 
	private BigDecimal  payrmb;
	private Double  payrate;
	private BigDecimal  payvatamt;
	private String  payhopedate="";
	private String  paydlyday="";
	private String  payforeignamt="";
	private String  paycurecode="";
	private String  payusedate="";
	private String  payrefer="";
	private String  invoicetype="";
	private String invoicetypename="";
	private String invoicetypenameen="";
	private String invoicetypenamejp="";
	private String invoicetypenamehk="";
	
	private String  invoicetext="";
	private String invoicetextnameen="";
	private String invoicetextnamejp="";
	private String invoicetextnamehk="";
	private String invoicetextname="";
	
	private String  deductionflg="";
	private String  payishave="";
	private String  addusercd="";
	private String  upusercd="";
	private String  payeecd="";
	
	private String addUserName="";
	private String upUserName="";
	private String payReqUserName="";
	private String confirmUserName="";
	private String payFinshUserName="";
	private String remark="";
	private String reqRemark="";
	private String confirmRemark="";
	private String payFinshRemark="";
	private String payplandate="";
	private String paydate="";
	private String invoiceno;
	private String invoicedate="";
	private String payremark="";
	private String canceluser="";
	private String paycanceldate="";
	private String payreqdate="";
	private String confirmdate="";
	private String adddate="";
	private String update="";
	private String payfinshdate="";
	private Double payamtdifference;//发注原件与支付原价的查
	private Double payvatamttdifference;//发注增值税，与支付增值税的查
	private Double payrmbdifference;//发注金额与支付金额的查
	private String payconfirmcancelUserName="";
	private String payconfirmcanceldate="";
	private String payupdate="";//支付处理更新时间
	private String payupuser="";//支付处理更新时间
	private String payflg="";//支付完了flag
	private String payaddusercolor="";
	private String payupdusercolor="";
	private String payReqUsercolor="";
	private String confirmusercolor="";
	private String payconfirmcancelusercolor="";
	private String payFinshUserNamecolor="";
	private String payupusercolor="";
	private String cancelusercolor="";
	private int lockflg;
	
	public int getLockflg() {
		return lockflg;
	}
	public void setLockflg(int lockflg) {
		this.lockflg = lockflg;
	}
	public String getPayaddusercolor() {
		return payaddusercolor;
	}
	public void setPayaddusercolor(String payaddusercolor) {
		this.payaddusercolor = payaddusercolor;
	}
	public String getPayupdusercolor() {
		return payupdusercolor;
	}
	public void setPayupdusercolor(String payupdusercolor) {
		this.payupdusercolor = payupdusercolor;
	}
	public String getPayReqUsercolor() {
		return payReqUsercolor;
	}
	public void setPayReqUsercolor(String payReqUsercolor) {
		this.payReqUsercolor = payReqUsercolor;
	}
	public String getConfirmusercolor() {
		return confirmusercolor;
	}
	public void setConfirmusercolor(String confirmusercolor) {
		this.confirmusercolor = confirmusercolor;
	}
	public String getPayconfirmcancelusercolor() {
		return payconfirmcancelusercolor;
	}
	public void setPayconfirmcancelusercolor(String payconfirmcancelusercolor) {
		this.payconfirmcancelusercolor = payconfirmcancelusercolor;
	}
	public String getPayFinshUserNamecolor() {
		return payFinshUserNamecolor;
	}
	public void setPayFinshUserNamecolor(String payFinshUserNamecolor) {
		this.payFinshUserNamecolor = payFinshUserNamecolor;
	}
	public String getPayupusercolor() {
		return payupusercolor;
	}
	public void setPayupusercolor(String payupusercolor) {
		this.payupusercolor = payupusercolor;
	}
	public String getCancelusercolor() {
		return cancelusercolor;
	}
	public void setCancelusercolor(String cancelusercolor) {
		this.cancelusercolor = cancelusercolor;
	}
	public String getPayflg() {
		return payflg;
	}
	public void setPayflg(String payflg) {
		this.payflg = payflg;
	}
	public String getPayupdate() {
		return payupdate;
	}
	public void setPayupdate(String payupdate) {
		this.payupdate = payupdate;
	}
	public String getPayupuser() {
		return payupuser;
	}
	public void setPayupuser(String payupuser) {
		this.payupuser = payupuser;
	}
	public String getPayconfirmcancelUserName() {
		return payconfirmcancelUserName;
	}
	public void setPayconfirmcancelUserName(String payconfirmcancelUserName) {
		this.payconfirmcancelUserName = payconfirmcancelUserName;
	}
	public String getPayconfirmcanceldate() {
		return payconfirmcanceldate;
	}
	public void setPayconfirmcanceldate(String payconfirmcanceldate) {
		this.payconfirmcanceldate = payconfirmcanceldate;
	}
	public Double getPayamtdifference() {
		return payamtdifference;
	}
	public void setPayamtdifference(Double payamtdifference) {
		this.payamtdifference = payamtdifference;
	}
	public Double getPayvatamttdifference() {
		return payvatamttdifference;
	}
	public void setPayvatamttdifference(Double payvatamttdifference) {
		this.payvatamttdifference = payvatamttdifference;
	}
	public Double getPayrmbdifference() {
		return payrmbdifference;
	}
	public void setPayrmbdifference(Double payrmbdifference) {
		this.payrmbdifference = payrmbdifference;
	}
	public String getPayfinshdate() {
		return payfinshdate;
	}
	public void setPayfinshdate(String payfinshdate) {
		this.payfinshdate = payfinshdate;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getPayremark() {
		return payremark;
	}
	public void setPayremark(String payremark) {
		this.payremark = payremark;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getPayplandate() {
		return payplandate;
	}
	public void setPayplandate(String payplandate) {
		this.payplandate = payplandate;
	}

	
	public String getInvoicetypename() {
		return invoicetypename;
	}
	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}
	public String getInvoicetypenameen() {
		return invoicetypenameen;
	}
	public void setInvoicetypenameen(String invoicetypenameen) {
		this.invoicetypenameen = invoicetypenameen;
	}
	public String getInvoicetypenamejp() {
		return invoicetypenamejp;
	}
	public void setInvoicetypenamejp(String invoicetypenamejp) {
		this.invoicetypenamejp = invoicetypenamejp;
	}
	public String getInvoicetypenamehk() {
		return invoicetypenamehk;
	}
	public void setInvoicetypenamehk(String invoicetypenamehk) {
		this.invoicetypenamehk = invoicetypenamehk;
	}
	public String getInvoicetextnameen() {
		return invoicetextnameen;
	}
	public void setInvoicetextnameen(String invoicetextnameen) {
		this.invoicetextnameen = invoicetextnameen;
	}
	public String getInvoicetextnamejp() {
		return invoicetextnamejp;
	}
	public void setInvoicetextnamejp(String invoicetextnamejp) {
		this.invoicetextnamejp = invoicetextnamejp;
	}
	public String getInvoicetextnamehk() {
		return invoicetextnamehk;
	}
	public void setInvoicetextnamehk(String invoicetextnamehk) {
		this.invoicetextnamehk = invoicetextnamehk;
	}
	public String getInvoicetextname() {
		return invoicetextname;
	}
	public void setInvoicetextname(String invoicetextname) {
		this.invoicetextname = invoicetextname;
	}
	public String getReqRemark() {
		return reqRemark;
	}
	public void setReqRemark(String reqRemark) {
		this.reqRemark = reqRemark;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	public String getPayFinshRemark() {
		return payFinshRemark;
	}
	public void setPayFinshRemark(String payFinshRemark) {
		this.payFinshRemark = payFinshRemark;
	}
	public String getPayReqUserName() {
		return payReqUserName;
	}
	public void setPayReqUserName(String payReqUserName) {
		this.payReqUserName = payReqUserName;
	}
	public String getConfirmUserName() {
		return confirmUserName;
	}
	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}
	public String getPayFinshUserName() {
		return payFinshUserName;
	}
	public void setPayFinshUserName(String payFinshUserName) {
		this.payFinshUserName = payFinshUserName;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public String getUpUserName() {
		return upUserName;
	}
	public void setUpUserName(String upUserName) {
		this.upUserName = upUserName;
	}
	public String getInputno() {
		return inputno;
	}
	public void setInputno(String inputno) {
		this.inputno = inputno;
	}
	public String getCompanycd() {
		return companycd;
	}
	public void setCompanycd(String companycd) {
		this.companycd = companycd;
	}
	public String getPayforeigntype() {
		return payforeigntype;
	}
	public void setPayforeigntype(String payforeigntype) {
		this.payforeigntype = payforeigntype;
	}
	public Double getPayrate() {
		return payrate;
	}
	public void setPayrate(Double payrate) {
		this.payrate = payrate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public BigDecimal getPayamt() {
		return payamt;
	}
	public void setPayamt(BigDecimal payamt) {
		this.payamt = payamt;
	}
	public BigDecimal getPayrmb() {
		return payrmb;
	}
	public void setPayrmb(BigDecimal payrmb) {
		this.payrmb = payrmb;
	}
	public BigDecimal getPayvatamt() {
		return payvatamt;
	}
	public void setPayvatamt(BigDecimal payvatamt) {
		this.payvatamt = payvatamt;
	}
	public String getPayhopedate() {
		return payhopedate;
	}
	public void setPayhopedate(String payhopedate) {
		this.payhopedate = payhopedate;
	}
	public String getPaydlyday() {
		return paydlyday;
	}
	public void setPaydlyday(String paydlyday) {
		this.paydlyday = paydlyday;
	}
	public String getPayforeignamt() {
		return payforeignamt;
	}
	public void setPayforeignamt(String payforeignamt) {
		this.payforeignamt = payforeignamt;
	}
	public String getPaycurecode() {
		return paycurecode;
	}
	public void setPaycurecode(String paycurecode) {
		this.paycurecode = paycurecode;
	}
	public String getPayusedate() {
		return payusedate;
	}
	public void setPayusedate(String payusedate) {
		this.payusedate = payusedate;
	}
	public String getPayrefer() {
		return payrefer;
	}
	public void setPayrefer(String payrefer) {
		this.payrefer = payrefer;
	}
	public String getInvoicetype() {
		return invoicetype;
	}
	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	public String getInvoicetext() {
		return invoicetext;
	}
	public void setInvoicetext(String invoicetext) {
		this.invoicetext = invoicetext;
	}
	public String getDeductionflg() {
		return deductionflg;
	}
	public void setDeductionflg(String deductionflg) {
		this.deductionflg = deductionflg;
	}
	public String getPayishave() {
		return payishave;
	}
	public void setPayishave(String payishave) {
		this.payishave = payishave;
	}
	public String getAddusercd() {
		return addusercd;
	}
	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}
	public String getUpusercd() {
		return upusercd;
	}
	public void setUpusercd(String upusercd) {
		this.upusercd = upusercd;
	}
	public String getPayeecd() {
		return payeecd;
	}
	public void setPayeecd(String payeecd) {
		this.payeecd = payeecd;
	}
	public String getCanceluser() {
		return canceluser;
	}
	public void setCanceluser(String canceluser) {
		this.canceluser = canceluser;
	}
	public String getPaycanceldate() {
		return paycanceldate;
	}
	public void setPaycanceldate(String paycanceldate) {
		this.paycanceldate = paycanceldate;
	}
	public String getPayreqdate() {
		return payreqdate;
	}
	public void setPayreqdate(String payreqdate) {
		this.payreqdate = payreqdate;
	}
	public String getConfirmdate() {
		return confirmdate;
	}
	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}
	
	
}
