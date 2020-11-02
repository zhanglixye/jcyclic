package com.kaiwait.bean.jczh.entity;

import java.math.BigDecimal;

public class Cost{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String   job_cd="";
	private String   cost_no="";//发注编号
	private String   costno="";
	private String   order_date="";//发注日
	private String   payee_cd="";//发注先
	private String   cost_name="";//外发名称
	private String   cost_type="";//分类项目
	private String   plan_dlvday="";//纳品预定日
	private String   cost_remark="";//备考
	private String   cost_rate="";//税率
	private String   cost_foreign_amt="";//外货发注金额
	private String   cost_rmb="";//发注金额本国货币
	private String   cost_vat_amt="";//增值税
	private String   cost_cure_code="";//换算CODE
	private String   cost_use_date="";//用日适
	private String   cost_ishave="";//卖上是否税入="";0：税拔；1：税入；
	private String   cost_refer="";//照线先
	private String   cost_foreign_type="";//外货货币种类编号
	private String   invoice_type="";//发票种类
	private String   invoice_text="";//发票内容
	private String   input_no="";//支付编号
	private String   inputno="";//支付编号
	private String   status="";//0：発注登録済、1：支払登録済、2：支払申請済、3：支払承認済
	private String   del_flg="";
	private String   company_cd="";
	private String   adddate="";
	private String   update="";
	private String   addusercd="";
	private String   addUserName="";
	private String   upusercd="";
	private String upUserName="";
	//下拉框
	private String  itemcd="";
	private String	mstname="";
	private String	itmname="";
	private String	itemnamehk="";
	private String	itemnameen="";
	private String	itemnamejp="";
	private String	itemnamezc="";
	private String aidcd="";
	private String changeutin="";
	private String divnm="";//最新更新的得意先名称
	private String orderName;
	private String upddate="";//job最新更新的时间
	private String username="";//更新这名称
	private String deduction="";//发票可否扣除
	private Double vatrate;
	private String itmvalue="";//小数点位数
	private String costpdfdate="";
	private String costpdfaddusername="";
	private String updusercolorv="";
	private String costaddcolorv="";
	private String costpdfaddusernamecolor="";
	private int lockflg;
	private String payeeflg;
	private String costVatRate;
	private String plancostishave;
	private String plansaleishave;
	private int costFromJpp;
	
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public int getCostFromJpp() {
		return costFromJpp;
	}
	public void setCostFromJpp(int costFromJpp) {
		this.costFromJpp = costFromJpp;
	}
	public String getItemnamezc() {
		return itemnamezc;
	}
	public void setItemnamezc(String itemnamezc) {
		this.itemnamezc = itemnamezc;
	}
	public String getPlansaleishave() {
		return plansaleishave;
	}
	public void setPlansaleishave(String plansaleishave) {
		this.plansaleishave = plansaleishave;
	}
	public String getPlancostishave() {
		return plancostishave;
	}
	public void setPlancostishave(String plancostishave) {
		this.plancostishave = plancostishave;
	}
	public String getCostVatRate() {
		return costVatRate;
	}
	public void setCostVatRate(String costVatRate) {
		this.costVatRate = costVatRate;
	}
	public String getPayeeflg() {
		return payeeflg;
	}
	public void setPayeeflg(String payeeflg) {
		this.payeeflg = payeeflg;
	}
	public int getLockflg() {
		return lockflg;
	}
	public void setLockflg(int lockflg) {
		this.lockflg = lockflg;
	}
	public String getCostpdfaddusernamecolor() {
		return costpdfaddusernamecolor;
	}
	public void setCostpdfaddusernamecolor(String costpdfaddusernamecolor) {
		this.costpdfaddusernamecolor = costpdfaddusernamecolor;
	}
	public String getCostaddcolorv() {
		return costaddcolorv;
	}
	public void setCostaddcolorv(String costaddcolorv) {
		this.costaddcolorv = costaddcolorv;
	}
	public String getUpdusercolorv() {
		return updusercolorv;
	}
	public void setUpdusercolorv(String updusercolorv) {
		this.updusercolorv = updusercolorv;
	}
	public String getCostpdfdate() {
		return costpdfdate;
	}
	public void setCostpdfdate(String costpdfdate) {
		this.costpdfdate = costpdfdate;
	}
	public String getCostpdfaddusername() {
		return costpdfaddusername;
	}
	public void setCostpdfaddusername(String costpdfaddusername) {
		this.costpdfaddusername = costpdfaddusername;
	}
	public String getItmvalue() {
		return itmvalue;
	}
	public void setItmvalue(String itmvalue) {
		this.itmvalue = itmvalue;
	}
	//成本更新信息共同显示区块
	//原价金额
	private String sumamt="";
	//增值税
	private String sumvat="";
	//支付金额
	private String paysum="";
	private String updatetime="";
	private String costupdname="";
	
	//外发查询返回值
	private Double costrate;
	private String costforeignamt="";
	private String costforeigntype="";
	private BigDecimal costrmb;
	private BigDecimal costvatamt;
	private String deductionflg="";
	private String outsoureamt="";
	private String plandlvday="";
	private String orderdate="";
	private String costremark="";
	private String costtype="";
	private String costrefer="";
	private String  costusedate="";
	private String costcurecode="";
	private String payeecd="";
	private BigDecimal  costpayamt;
	private String outsoureuser="";//外发登录的承包方
	private String costname="";
	private String costupdate="";
	private String costishave="";
	private String invoicetype="";
	private String invoicetext="";
	private String invoiceno="";
	private String invoicedate="";
	private String remark="";
	private String orderTyp="";
	private String invoicetypename="";
	private String invoicetypenameen="";
	private String invoicetypenamejp="";
	private String invoicetypenamehk="";
	
	private String invoicetextnameen="";
	private String invoicetextnamejp="";
	private String invoicetextnamehk="";
	private String invoicetextname="";
	private String updusername="";
	private String addupdusername="";
	private String costadddate="";
	private String saleAddFlag="";
	private String jobcd="";
	private String jobname="";
	private String costfinishflg="";
	private String costnum="";
	
	public String getCostnum() {
		return costnum;
	}
	public void setCostnum(String costnum) {
		this.costnum = costnum;
	}
	public String getCostfinishflg() {
		return costfinishflg;
	}
	public void setCostfinishflg(String costfinishflg) {
		this.costfinishflg = costfinishflg;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getJobcd() {
		return jobcd;
	}
	public void setJobcd(String jobcd) {
		this.jobcd = jobcd;
	}
	public String getSaleAddFlag() {
		return saleAddFlag;
	}
	public void setSaleAddFlag(String saleAddFlag) {
		this.saleAddFlag = saleAddFlag;
	}
	//成本合计
	private String costsum;
	
	public String getCostsum() {
		return costsum;
	}
	public void setCostsum(String costsum) {
		this.costsum = costsum;
	}
	public String getSumvat() {
		return sumvat;
	}
	public void setSumvat(String sumvat) {
		this.sumvat = sumvat;
	}

	public String getPaysum() {
		return paysum;
	}
	public void setPaysum(String paysum) {
		this.paysum = paysum;
	}
	public String getCostadddate() {
		return costadddate;
	}
	public void setCostadddate(String costadddate) {
		this.costadddate = costadddate;
	}
	public String getUpdusername() {
		return updusername;
	}
	public void setUpdusername(String updusername) {
		this.updusername = updusername;
	}
	public String getAddupdusername() {
		return addupdusername;
	}
	public void setAddupdusername(String addupdusername) {
		this.addupdusername = addupdusername;
	}
	public String getInputno() {
		return inputno;
	}
	public void setInputno(String inputno) {
		this.inputno = inputno;
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
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getCost_no() {
		return cost_no;
	}
	public void setCost_no(String cost_no) {
		this.cost_no = cost_no;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getPayee_cd() {
		return payee_cd;
	}
	public void setPayee_cd(String payee_cd) {
		this.payee_cd = payee_cd;
	}
	public String getCost_name() {
		return cost_name;
	}
	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}
	public String getCost_type() {
		return cost_type;
	}
	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
	}
	public String getPlan_dlvday() {
		return plan_dlvday;
	}
	public void setPlan_dlvday(String plan_dlvday) {
		this.plan_dlvday = plan_dlvday;
	}
	public String getCost_remark() {
		return cost_remark;
	}
	public void setCost_remark(String cost_remark) {
		this.cost_remark = cost_remark;
	}
	public String getCost_rate() {
		return cost_rate;
	}
	public void setCost_rate(String cost_rate) {
		this.cost_rate = cost_rate;
	}
	public String getCost_foreign_amt() {
		return cost_foreign_amt;
	}
	public void setCost_foreign_amt(String cost_foreign_amt) {
		this.cost_foreign_amt = cost_foreign_amt;
	}
	public String getCost_rmb() {
		return cost_rmb;
	}
	public void setCost_rmb(String cost_rmb) {
		this.cost_rmb = cost_rmb;
	}
	public String getCost_vat_amt() {
		return cost_vat_amt;
	}
	public void setCost_vat_amt(String cost_vat_amt) {
		this.cost_vat_amt = cost_vat_amt;
	}
	public String getCost_cure_code() {
		return cost_cure_code;
	}
	public void setCost_cure_code(String cost_cure_code) {
		this.cost_cure_code = cost_cure_code;
	}
	public String getCost_use_date() {
		return cost_use_date;
	}
	public void setCost_use_date(String cost_use_date) {
		this.cost_use_date = cost_use_date;
	}
	public String getCost_ishave() {
		return cost_ishave;
	}
	public void setCost_ishave(String cost_ishave) {
		this.cost_ishave = cost_ishave;
	}
	public String getCost_refer() {
		return cost_refer;
	}
	public void setCost_refer(String cost_refer) {
		this.cost_refer = cost_refer;
	}
	public String getCost_foreign_type() {
		return cost_foreign_type;
	}
	public void setCost_foreign_type(String cost_foreign_type) {
		this.cost_foreign_type = cost_foreign_type;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_text() {
		return invoice_text;
	}
	public void setInvoice_text(String invoice_text) {
		this.invoice_text = invoice_text;
	}
	public String getInput_no() {
		return input_no;
	}
	public void setInput_no(String input_no) {
		this.input_no = input_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
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
	public String getItemcd() {
		return itemcd;
	}
	public void setItemcd(String itemcd) {
		this.itemcd = itemcd;
	}
	public String getMstname() {
		return mstname;
	}
	public void setMstname(String mstname) {
		this.mstname = mstname;
	}
	public String getItmname() {
		return itmname;
	}
	public void setItmname(String itmname) {
		this.itmname = itmname;
	}
	public String getItemnamehk() {
		return itemnamehk;
	}
	public void setItemnamehk(String itemnamehk) {
		this.itemnamehk = itemnamehk;
	}
	public String getItemnameen() {
		return itemnameen;
	}
	public void setItemnameen(String itemnameen) {
		this.itemnameen = itemnameen;
	}
	public String getItemnamejp() {
		return itemnamejp;
	}
	public void setItemnamejp(String itemnamejp) {
		this.itemnamejp = itemnamejp;
	}
	public String getAidcd() {
		return aidcd;
	}
	public void setAidcd(String aidcd) {
		this.aidcd = aidcd;
	}
	public String getChangeutin() {
		return changeutin;
	}
	public void setChangeutin(String changeutin) {
		this.changeutin = changeutin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDivnm() {
		return divnm;
	}
	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getUsername() {
		return username;
	}
	public void setUser_name(String username) {
		this.username = username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDeduction() {
		return deduction;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	public Double getVatrate() {
		return vatrate;
	}
	public void setVatrate(Double vatrate) {
		this.vatrate = vatrate;
	}
	public String getSumamt() {
		return sumamt;
	}
	public void setSumamt(String sumamt) {
		this.sumamt = sumamt;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getCostupdname() {
		return costupdname;
	}
	public void setCostupdname(String costupdname) {
		this.costupdname = costupdname;
	}

	public Double getCostrate() {
		return costrate;
	}
	public void setCostrate(Double costrate) {
		this.costrate = costrate;
	}
	public String getCostforeignamt() {
		return costforeignamt;
	}
	public void setCostforeignamt(String costforeignamt) {
		this.costforeignamt = costforeignamt;
	}

	public String getDeductionflg() {
		return deductionflg;
	}
	public void setDeductionflg(String deductionflg) {
		this.deductionflg = deductionflg;
	}
	public String getOutsoureamt() {
		return outsoureamt;
	}
	public void setOutsoureamt(String outsoureamt) {
		this.outsoureamt = outsoureamt;
	}
	public String getPlandlvday() {
		return plandlvday;
	}
	public void setPlandlvday(String plandlvday) {
		this.plandlvday = plandlvday;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getCostremark() {
		return costremark;
	}
	public void setCostremark(String costremark) {
		this.costremark = costremark;
	}
	public String getOutsoureuser() {
		return outsoureuser;
	}
	public void setOutsoureuser(String outsoureuser) {
		this.outsoureuser = outsoureuser;
	}
	public String getCostname() {
		return costname;
	}
	public void setCostname(String costname) {
		this.costname = costname;
	}
	public String getCostupdate() {
		return costupdate;
	}
	public void setCostupdate(String costupdate) {
		this.costupdate = costupdate;
	}
	public String getCostishave() {
		return costishave;
	}
	public void setCostishave(String costishave) {
		this.costishave = costishave;
	}
	public String getCostforeigntype() {
		return costforeigntype;
	}
	public void setCostforeigntype(String costforeigntype) {
		this.costforeigntype = costforeigntype;
	}
	public String getCosttype() {
		return costtype;
	}
	public void setCosttype(String costtype) {
		this.costtype = costtype;
	}
	public String getCostrefer() {
		return costrefer;
	}
	public void setCostrefer(String costrefer) {
		this.costrefer = costrefer;
	}
	public String getCostusedate() {
		return costusedate;
	}
	public void setCostusedate(String costusedate) {
		this.costusedate = costusedate;
	}
	public String getCostcurecode() {
		return costcurecode;
	}
	public void setCostcurecode(String costcurecode) {
		this.costcurecode = costcurecode;
	}
	public String getPayeecd() {
		return payeecd;
	}
	public void setPayeecd(String payeecd) {
		this.payeecd = payeecd;
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
	public String getCostno() {
		return costno;
	}
	public void setCostno(String costno) {
		this.costno = costno;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderTyp() {
		return orderTyp;
	}
	public void setOrderTyp(String orderTyp) {
		this.orderTyp = orderTyp;
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
	public BigDecimal getCostrmb() {
		return costrmb;
	}
	public void setCostrmb(BigDecimal costrmb) {
		this.costrmb = costrmb;
	}
	public BigDecimal getCostvatamt() {
		return costvatamt;
	}
	public void setCostvatamt(BigDecimal costvatamt) {
		this.costvatamt = costvatamt;
	}
	public BigDecimal getCostpayamt() {
		return costpayamt;
	}
	public void setCostpayamt(BigDecimal costpayamt) {
		this.costpayamt = costpayamt;
	}
	
}
