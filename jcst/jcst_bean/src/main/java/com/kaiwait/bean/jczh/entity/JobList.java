package com.kaiwait.bean.jczh.entity;

import java.util.List;

/**
 * 
* @ClassName: SalesList  
* @Description: TODO(売上種目实体类)  
* @author mayouyi  
* @date 2018年5月2日  
*
 */
public class JobList {
	private String jobcd="";// jobno
	private String costno="";
	private String cldiv="";//得意先 
	private String cldiven="";
	private String cldivfull="";
	private String gcompany="";	//相手先g会社 
	private String payer="";	// 请求先 
	private String payeren="";
	private String payerfull="";	
	private String jobname=""; // 件名 
	private String salename="";//売上種目 
	private String dlvday="";	//计上日 
	private String saleamt="";// 壳上 
	private String vatamt="";	 // 壳上增值税 
	private String reqamt="";	 // 请求金额 
	private String plancostamt="";	 // 予定原価 
	private String accountflg="";//ACCOUNTFLG表示洗壳终了，1表示终了 机上状态是 ‘计上计’  JOBENDFLG表示job终了状态，1是‘终了’.SALENO有是‘计’。没有是‘未’
	private String jobendflg="";
	private String saleno="";//job编号
	private String assignflg="";// 担当割当状态 
	private String reqflg=""; // 请求状态
	private String recflg="";//入金状态 
	private String invflg=""; // 发票状态 
	private String costfinishflg="";//原価完了状态 
	private String cldivcd="";//得以先编号 
	private String zrusername="";//责任者 
	private String zrusernameen="";
	private String ddusername="";//担当者 
	private String ddusernameen="";
	private String plansale="";// 壳上予定额 
	private String jobadduser="";//job登录者 
	private String jobadduseren="";
	private String adddate=""; // job登录日 
	private String seladduser="";//壳上登录者 
	private String seladduseren="";
	private String adminuser="";//壳上承认者 
	private String adminuseren="";
	private String saleadmitdate=""; // 壳上承认日 
	private String dlvmon="";//计上月 
	private String remark="";	 // 备考
	private String departcd=""; // 部门标号
	private String  itemcd="";
	private String	mstname="";
	private String	itmname="";
	private String	itemnamehk="";
	private String	itemnameen="";
	private String	itemnamejp="";
	private String  jsflag="";//计上状态
	private String	jsflaghk="";
	private String	jsflagen="";
	private String	jsflagjp="";
	private String saletax="";//税金
	private String salecd="";//税金
	private String costTotalAmt="";//原价合计
	private String costVatTotal="";//增值税合计
	private String tax1="";
	private String tax2="";
	private String tax3="";
	private String taxTotal="";//实际税金合计
	private String payAmtSum="";
	private String rate2="";
	private String rate3="";
	private String saleadddate="";
	private String trandate="";
	private String lenddate="";
	private String recdate="";
	private String recremark="";
	private String reccd="";
	private String trueCostTotalAmt="";//实际原价
	private String truePayAmtSum="";
	private String plantaxTotal="";//预计税金合计
	private String planprofitRate="";//预计营业率
	private String planprofit="";//预计营业合计
	private String tranlend="";//可/不可
	private String saleaddflg="";//壳上登录flag ，0：未
	private String saleadmitflg="";//壳上承认flag 0：未
	private String lableid="";
	private String plancosttax="";//预计成本增值税
	private String delflg="";//刪除標志
	private String upddate="";
	private String jobenddate="";
	private String assignflglanguage="";
	private String reqflglanguage="";
	private String recflglanguage="";
	private String invflglanguage="";
	private String costendflglanguage="";
	private String status="";
	private String planpayamt="";
	private String  costnum="";
	private String lable="";
	private String reqflglanguagezc="";
	private String reqflglanguageen="";
	private String reqflglanguagejp="";
	private String reqflglanguagezt="";
	
	private String jsflaglanguagezc="";
	private String jsflaglanguageen="";
	private String jsflaglanguagejp="";
	private String jsflaglanguagezt="";
	
	
	private String recflglanguagezc = "";
	private String recflglanguageen = "";
	private String recflglanguagejp = "";
	private String recflglanguagezt = "";
	
	private String invflglanguagezc = "";
	private String invflglanguageen = "";
	private String invflglanguagejp = "";
	private String invflglanguagezt = "";
	
	private String assignflglanguagezc = "";
	private String assignflglanguageen = "";
	private String assignflglanguagejp = "";
	private String assignflglanguagezt = "";
	
	private String costendflglanguagezc = "";
	private String costendflglanguageen = "";
	private String costendflglanguagejp = "";
	private String costendflglanguagezt = "";
	private List<String> cldivList;//登录者纽付的得意先
	private List<String> reqList;//job担当着
	private List<String> mdList;//job割当者
	private String lablelevel= "";
	private String mdnum= "";
	private String historyrate2= "";
	private String historyrate3= "";
	private int lockflg;
	private int reclockflg;
	private String reqtimes= "";
	private String invoicetimes= "";
	
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
	public int getReclockflg() {
		return reclockflg;
	}
	public void setReclockflg(int reclockflg) {
		this.reclockflg = reclockflg;
	}
	public int getLockflg() {
		return lockflg;
	}
	public void setLockflg(int lockflg) {
		this.lockflg = lockflg;
	}
	public String getHistoryrate2() {
		return historyrate2;
	}
	public void setHistoryrate2(String historyrate2) {
		this.historyrate2 = historyrate2;
	}
	public String getHistoryrate3() {
		return historyrate3;
	}
	public void setHistoryrate3(String historyrate3) {
		this.historyrate3 = historyrate3;
	}
	public String getMdnum() {
		return mdnum;
	}
	public void setMdnum(String mdnum) {
		this.mdnum = mdnum;
	}
	public String getLablelevel() {
		return lablelevel;
	}
	public void setLablelevel(String lablelevel) {
		this.lablelevel = lablelevel;
	}
	public List<String> getCldivList() {
		return cldivList;
	}
	public void setCldivList(List<String> cldivList) {
		this.cldivList = cldivList;
	}

	public List<String> getReqList() {
		return reqList;
	}
	public void setReqList(List<String> reqList) {
		this.reqList = reqList;
	}
	public List<String> getMdList() {
		return mdList;
	}
	public void setMdList(List<String> mdList) {
		this.mdList = mdList;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getJsflaglanguagezc() {
		return jsflaglanguagezc;
	}
	public void setJsflaglanguagezc(String jsflaglanguagezc) {
		this.jsflaglanguagezc = jsflaglanguagezc;
	}
	public String getJsflaglanguageen() {
		return jsflaglanguageen;
	}
	public void setJsflaglanguageen(String jsflaglanguageen) {
		this.jsflaglanguageen = jsflaglanguageen;
	}
	public String getJsflaglanguagejp() {
		return jsflaglanguagejp;
	}
	public void setJsflaglanguagejp(String jsflaglanguagejp) {
		this.jsflaglanguagejp = jsflaglanguagejp;
	}
	public String getJsflaglanguagezt() {
		return jsflaglanguagezt;
	}
	public void setJsflaglanguagezt(String jsflaglanguagezt) {
		this.jsflaglanguagezt = jsflaglanguagezt;
	}
	public String getRecflglanguagezc() {
		return recflglanguagezc;
	}
	public void setRecflglanguagezc(String recflglanguagezc) {
		this.recflglanguagezc = recflglanguagezc;
	}
	public String getRecflglanguageen() {
		return recflglanguageen;
	}
	public void setRecflglanguageen(String recflglanguageen) {
		this.recflglanguageen = recflglanguageen;
	}
	public String getRecflglanguagejp() {
		return recflglanguagejp;
	}
	public void setRecflglanguagejp(String recflglanguagejp) {
		this.recflglanguagejp = recflglanguagejp;
	}
	public String getRecflglanguagezt() {
		return recflglanguagezt;
	}
	public void setRecflglanguagezt(String recflglanguagezt) {
		this.recflglanguagezt = recflglanguagezt;
	}
	public String getInvflglanguagezc() {
		return invflglanguagezc;
	}
	public void setInvflglanguagezc(String invflglanguagezc) {
		this.invflglanguagezc = invflglanguagezc;
	}
	public String getInvflglanguageen() {
		return invflglanguageen;
	}
	public void setInvflglanguageen(String invflglanguageen) {
		this.invflglanguageen = invflglanguageen;
	}
	public String getInvflglanguagejp() {
		return invflglanguagejp;
	}
	public void setInvflglanguagejp(String invflglanguagejp) {
		this.invflglanguagejp = invflglanguagejp;
	}
	public String getInvflglanguagezt() {
		return invflglanguagezt;
	}
	public void setInvflglanguagezt(String invflglanguagezt) {
		this.invflglanguagezt = invflglanguagezt;
	}
	public String getAssignflglanguagezc() {
		return assignflglanguagezc;
	}
	public void setAssignflglanguagezc(String assignflglanguagezc) {
		this.assignflglanguagezc = assignflglanguagezc;
	}
	public String getAssignflglanguageen() {
		return assignflglanguageen;
	}
	public void setAssignflglanguageen(String assignflglanguageen) {
		this.assignflglanguageen = assignflglanguageen;
	}
	public String getAssignflglanguagejp() {
		return assignflglanguagejp;
	}
	public void setAssignflglanguagejp(String assignflglanguagejp) {
		this.assignflglanguagejp = assignflglanguagejp;
	}
	public String getAssignflglanguagezt() {
		return assignflglanguagezt;
	}
	public void setAssignflglanguagezt(String assignflglanguagezt) {
		this.assignflglanguagezt = assignflglanguagezt;
	}
	public String getCostendflglanguagezc() {
		return costendflglanguagezc;
	}
	public void setCostendflglanguagezc(String costendflglanguagezc) {
		this.costendflglanguagezc = costendflglanguagezc;
	}
	public String getCostendflglanguageen() {
		return costendflglanguageen;
	}
	public void setCostendflglanguageen(String costendflglanguageen) {
		this.costendflglanguageen = costendflglanguageen;
	}
	public String getCostendflglanguagejp() {
		return costendflglanguagejp;
	}
	public void setCostendflglanguagejp(String costendflglanguagejp) {
		this.costendflglanguagejp = costendflglanguagejp;
	}
	public String getCostendflglanguagezt() {
		return costendflglanguagezt;
	}
	public void setCostendflglanguagezt(String costendflglanguagezt) {
		this.costendflglanguagezt = costendflglanguagezt;
	}
	public String getReqflglanguagezc() {
		return reqflglanguagezc;
	}
	public void setReqflglanguagezc(String reqflglanguagezc) {
		this.reqflglanguagezc = reqflglanguagezc;
	}
	public String getReqflglanguageen() {
		return reqflglanguageen;
	}
	public void setReqflglanguageen(String reqflglanguageen) {
		this.reqflglanguageen = reqflglanguageen;
	}
	public String getReqflglanguagejp() {
		return reqflglanguagejp;
	}
	public void setReqflglanguagejp(String reqflglanguagejp) {
		this.reqflglanguagejp = reqflglanguagejp;
	}
	public String getReqflglanguagezt() {
		return reqflglanguagezt;
	}
	public void setReqflglanguagezt(String reqflglanguagezt) {
		this.reqflglanguagezt = reqflglanguagezt;
	}
	public String getCostnum() {
		return costnum;
	}
	public void setCostnum(String costnum) {
		this.costnum = costnum;
	}
	public String getPlanpayamt() {
		return planpayamt;
	}
	public void setPlanpayamt(String planpayamt) {
		this.planpayamt = planpayamt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssignflglanguage() {
		return assignflglanguage;
	}
	public void setAssignflglanguage(String assignflglanguage) {
		this.assignflglanguage = assignflglanguage;
	}
	public String getReqflglanguage() {
		return reqflglanguage;
	}
	public void setReqflglanguage(String reqflglanguage) {
		this.reqflglanguage = reqflglanguage;
	}
	public String getRecflglanguage() {
		return recflglanguage;
	}
	public void setRecflglanguage(String recflglanguage) {
		this.recflglanguage = recflglanguage;
	}
	public String getInvflglanguage() {
		return invflglanguage;
	}
	public void setInvflglanguage(String invflglanguage) {
		this.invflglanguage = invflglanguage;
	}
	
	public String getCostendflglanguage() {
		return costendflglanguage;
	}
	public void setCostendflglanguage(String costendflglanguage) {
		this.costendflglanguage = costendflglanguage;
	}
	public String getJobenddate() {
		return jobenddate;
	}
	public void setJobenddate(String jobenddate) {
		this.jobenddate = jobenddate;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public String getPlancosttax() {
		return plancosttax;
	}
	public void setPlancosttax(String plancosttax) {
		this.plancosttax = plancosttax;
	}
	public String getLableid() {
		return lableid;
	}
	public void setLableid(String lableid) {
		this.lableid = lableid;
	}
	public String getSaleadmitflg() {
		return saleadmitflg;
	}
	public void setSaleadmitflg(String saleadmitflg) {
		this.saleadmitflg = saleadmitflg;
	}
	public String getSaleaddflg() {
		return saleaddflg;
	}
	public void setSaleaddflg(String saleaddflg) {
		this.saleaddflg = saleaddflg;
	}
	public String getTranlend() {
		return tranlend;
	}
	public void setTranlend(String tranlend) {
		this.tranlend = tranlend;
	}
	List<Timesheettrn>  Timesheettrn;
	
	public String getPlanprofitRate() {
		return planprofitRate;
	}
	public void setPlanprofitRate(String planprofitRate) {
		this.planprofitRate = planprofitRate;
	}
	public String getPlanprofit() {
		return planprofit;
	}
	public void setPlanprofit(String planprofit) {
		this.planprofit = planprofit;
	}
	public String getPlantaxTotal() {
		return plantaxTotal;
	}
	public void setPlantaxTotal(String plantaxTotal) {
		this.plantaxTotal = plantaxTotal;
	}
	public String getTruePayAmtSum() {
		return truePayAmtSum;
	}
	public void setTruePayAmtSum(String truePayAmtSum) {
		this.truePayAmtSum = truePayAmtSum;
	}
	public String getTrueCostTotalAmt() {
		return trueCostTotalAmt;
	}
	public void setTrueCostTotalAmt(String trueCostTotalAmt) {
		this.trueCostTotalAmt = trueCostTotalAmt;
	}
	public String getJobcd() {
		return jobcd;
	}
	public void setJobcd(String jobcd) {
		this.jobcd = jobcd;
	}
	public String getCldiv() {
		return cldiv;
	}
	public void setCldiv(String cldiv) {
		this.cldiv = cldiv;
	}
	public String getCldiven() {
		return cldiven;
	}
	public void setCldiven(String cldiven) {
		this.cldiven = cldiven;
	}
	public String getCldivfull() {
		return cldivfull;
	}
	public void setCldivfull(String cldivfull) {
		this.cldivfull = cldivfull;
	}
	public String getGcompany() {
		return gcompany;
	}
	public void setGcompany(String gcompany) {
		this.gcompany = gcompany;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayeren() {
		return payeren;
	}
	public void setPayeren(String payeren) {
		this.payeren = payeren;
	}
	public String getPayerfull() {
		return payerfull;
	}
	public void setPayerfull(String payerfull) {
		this.payerfull = payerfull;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getSalename() {
		return salename;
	}
	public void setSalename(String salename) {
		this.salename = salename;
	}
	public String getDlvday() {
		return dlvday;
	}
	public void setDlvday(String dlvday) {
		this.dlvday = dlvday;
	}
	public String getSaleamt() {
		return saleamt;
	}
	public void setSaleamt(String saleamt) {
		this.saleamt = saleamt;
	}
	public String getVatamt() {
		return vatamt;
	}
	public void setVatamt(String vatamt) {
		this.vatamt = vatamt;
	}
	public String getReqamt() {
		return reqamt;
	}
	public void setReqamt(String reqamt) {
		this.reqamt = reqamt;
	}
	public String getPlancostamt() {
		return plancostamt;
	}
	public void setPlancostamt(String plancostamt) {
		this.plancostamt = plancostamt;
	}
	public String getAccountflg() {
		return accountflg;
	}
	public void setAccountflg(String accountflg) {
		this.accountflg = accountflg;
	}
	public String getJobendflg() {
		return jobendflg;
	}
	public void setJobendflg(String jobendflg) {
		this.jobendflg = jobendflg;
	}
	public String getSaleno() {
		return saleno;
	}
	public void setSaleno(String saleno) {
		this.saleno = saleno;
	}
	public String getAssignflg() {
		return assignflg;
	}
	public void setAssignflg(String assignflg) {
		this.assignflg = assignflg;
	}
	public String getReqflg() {
		return reqflg;
	}
	public void setReqflg(String reqflg) {
		this.reqflg = reqflg;
	}
	public String getRecflg() {
		return recflg;
	}
	public void setRecflg(String recflg) {
		this.recflg = recflg;
	}
	public String getInvflg() {
		return invflg;
	}
	public void setInvflg(String invflg) {
		this.invflg = invflg;
	}
	public String getCostfinishflg() {
		return costfinishflg;
	}
	public void setCostfinishflg(String costfinishflg) {
		this.costfinishflg = costfinishflg;
	}
	public String getCldivcd() {
		return cldivcd;
	}
	public void setCldivcd(String cldivcd) {
		this.cldivcd = cldivcd;
	}
	public String getZrusername() {
		return zrusername;
	}
	public void setZrusername(String zrusername) {
		this.zrusername = zrusername;
	}
	public String getZrusernameen() {
		return zrusernameen;
	}
	public void setZrusernameen(String zrusernameen) {
		this.zrusernameen = zrusernameen;
	}
	public String getDdusername() {
		return ddusername;
	}
	public void setDdusername(String ddusername) {
		this.ddusername = ddusername;
	}
	public String getDdusernameen() {
		return ddusernameen;
	}
	public void setDdusernameen(String ddusernameen) {
		this.ddusernameen = ddusernameen;
	}
	public String getPlansale() {
		return plansale;
	}
	public void setPlansale(String plansale) {
		this.plansale = plansale;
	}
	public String getJobadduser() {
		return jobadduser;
	}
	public void setJobadduser(String jobadduser) {
		this.jobadduser = jobadduser;
	}
	public String getJobadduseren() {
		return jobadduseren;
	}
	public void setJobadduseren(String jobadduseren) {
		this.jobadduseren = jobadduseren;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getSeladduser() {
		return seladduser;
	}
	public void setSeladduser(String seladduser) {
		this.seladduser = seladduser;
	}
	public String getSeladduseren() {
		return seladduseren;
	}
	public void setSeladduseren(String seladduseren) {
		this.seladduseren = seladduseren;
	}
	public String getAdminuser() {
		return adminuser;
	}
	public void setAdminuser(String adminuser) {
		this.adminuser = adminuser;
	}
	public String getAdminuseren() {
		return adminuseren;
	}
	public void setAdminuseren(String adminuseren) {
		this.adminuseren = adminuseren;
	}
	public String getSaleadmitdate() {
		return saleadmitdate;
	}
	public void setSaleadmitdate(String saleadmitdate) {
		this.saleadmitdate = saleadmitdate;
	}
	public String getDlvmon() {
		return dlvmon;
	}
	public void setDlvmon(String dlvmon) {
		this.dlvmon = dlvmon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepartcd() {
		return departcd;
	}
	public void setDepartcd(String departcd) {
		this.departcd = departcd;
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
	public String getSaletax() {
		return saletax;
	}
	public void setSaletax(String saletax) {
		this.saletax = saletax;
	}
	public String getJsflag() {
		return jsflag;
	}
	public void setJsflag(String jsflag) {
		this.jsflag = jsflag;
	}
	public String getJsflaghk() {
		return jsflaghk;
	}
	public void setJsflaghk(String jsflaghk) {
		this.jsflaghk = jsflaghk;
	}
	public String getJsflagen() {
		return jsflagen;
	}
	public void setJsflagen(String jsflagen) {
		this.jsflagen = jsflagen;
	}
	public String getJsflagjp() {
		return jsflagjp;
	}
	public void setJsflagjp(String jsflagjp) {
		this.jsflagjp = jsflagjp;
	}
	public String getSalecd() {
		return salecd;
	}
	public void setSalecd(String salecd) {
		this.salecd = salecd;
	}
	public String getCostTotalAmt() {
		return costTotalAmt;
	}
	public void setCostTotalAmt(String costTotalAmt) {
		this.costTotalAmt = costTotalAmt;
	}
	public String getCostVatTotal() {
		return costVatTotal;
	}
	public void setCostVatTotal(String costVatTotal) {
		this.costVatTotal = costVatTotal;
	}
	public String getTax1() {
		return tax1;
	}
	public void setTax1(String tax1) {
		this.tax1 = tax1;
	}
	public String getTax2() {
		return tax2;
	}
	public void setTax2(String tax2) {
		this.tax2 = tax2;
	}
	public String getTax3() {
		return tax3;
	}
	public void setTax3(String tax3) {
		this.tax3 = tax3;
	}
	public String getTaxTotal() {
		return taxTotal;
	}
	public void setTaxTotal(String taxTotal) {
		this.taxTotal = taxTotal;
	}
	public String getPayAmtSum() {
		return payAmtSum;
	}
	public void setPayAmtSum(String payAmtSum) {
		this.payAmtSum = payAmtSum;
	}
	public String getRate2() {
		return rate2;
	}
	public void setRate2(String rate2) {
		this.rate2 = rate2;
	}
	public String getRate3() {
		return rate3;
	}
	public void setRate3(String rate3) {
		this.rate3 = rate3;
	}
	public String getSaleadddate() {
		return saleadddate;
	}
	public void setSaleadddate(String saleadddate) {
		this.saleadddate = saleadddate;
	}
	public String getCostno() {
		return costno;
	}
	public void setCostno(String costno) {
		this.costno = costno;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	public String getLenddate() {
		return lenddate;
	}
	public void setLenddate(String lenddate) {
		this.lenddate = lenddate;
	}
	public String getRecdate() {
		return recdate;
	}
	public void setRecdate(String recdate) {
		this.recdate = recdate;
	}
	public String getRecremark() {
		return recremark;
	}
	public void setRecremark(String recremark) {
		this.recremark = recremark;
	}
	public String getReccd() {
		return reccd;
	}
	public void setReccd(String reccd) {
		this.reccd = reccd;
	}
	public List<Timesheettrn> getTimesheettrn() {
		return Timesheettrn;
	}
	public void setTimesheettrn(List<Timesheettrn> timesheettrn) {
		Timesheettrn = timesheettrn;
	}
	
}