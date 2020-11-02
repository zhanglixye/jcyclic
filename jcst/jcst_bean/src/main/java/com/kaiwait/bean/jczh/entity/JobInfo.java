package com.kaiwait.bean.jczh.entity;

import java.math.BigDecimal;
import java.util.List;

public class JobInfo {
	// jobtrn 表
	// job编号
	private String jobcd="";
	// 件名
	private String jobname="";
	// 売上种目
	private String saletyp="";
	private String saleName="";
	// 得意先
	private String cldivcd="";
	private String clName="";
	// 请求先
	private String payercd="";
	private String payerName="";
	// 相守G公司
	private String gcompany="";
	private String gName="";

	// 计预终了日
	private String plandalday="";
	private String dlvday="";
	// 预计卖上金额
	private BigDecimal plansale;
	private BigDecimal planSaleVat;
	// 预计卖上金额外货
	private BigDecimal plansaleforeignamt;
	// 卖上换算CODE
	private String plansalecurecode="";
	// 卖上适用日
	private String plansaleusedate="";
	// 卖上是否税入="";0：税拔；1：税入；
	private Integer plansaleishave;
	// 参照先
	private String plansalerefer="";
	// 金税1
	private float plansaletax1;
	// 税金2
	private float plansaletax2;
	// 税金合计
	private float plansaletaxtotal;
	// 预计仕入增值税
	private float plancosttax;
	// 外货CODE
	private String plansaleforeigncode="";
	// 预计成本
	private float plancostamt;
	// 预计成本适用日
	private String plancostusedate="";
	// 预计成本参照先
	private String plancostrefer="";
	// 预计换算code
	private String plancostcurecode="";
	// 预计成本外货
	private float plancostforeignamt;
	// 预计成本外货CODE
	private String plancostforeigncode="";
	// 预计成本是否税入="";0：税拔；1：税入；
	private Integer plancostishave;
	// JOB登録日時
	private String adddate="";
	// JOB更新日時
	private String upddate="";
	// JOB登録者コード
	private String addusercd="";
	private String addUserName="";
	// JOB更新者コード
	private String updusercd="";
	private String upUserName="";
	// job登陆者姓名
	private String adduser="";
	// job 更新这 姓名
	private String upduser="";
	// 备考
	private String remark="";
	// 公司ID
	private Integer companycd;
	// 0：正常、1：削除
	private Integer delflg;
	// joblabletrn
	// lable 编号
	private List<JobLableTrn> jlTrn;
	private List<JobUserLable> julable;
	// lablemst
	// 标签编号
	private String lableid="";
	// 标签文本
	private String labletext="";
	// 标签等级
	private Integer lablelevel;
	// 请求金额
	private BigDecimal planreqamt;
	// 支付金额
	private float planpayamt;
	// 得意先 名
	private String divnm="";
	private String divnmen="";
	private String divnamefull="";
	// 卖上承认Flg
	private int confirmFlg;
	// 记上FLg
	private int dlvFlg;
	// 原价完了Flg
	private int costFinshFlg;
	private String costFinshUser="";
	// Job终了Flg
	private int jobEndFlg;
	// 请求预定日
	private String planreqdate="";
	// 入金日
	private String recdate="";
	// 实际壳上额
	private BigDecimal saleamt;
	// 实际壳上增值税
	private BigDecimal salevatamt;

	// 壳上预计实际金额差值
	private Double saledifference;
	// 壳上增值税预计实际差值
	private Double vatamtdifference;
	// 请求金额预计实际金额差值
	private Double reqdifference;
	// 入金备注
	private String recremark="";
	// 壳上承认备考
	private String saleadmitremark="";
	// 入金更新者
	private String recupdName="";
	// 入金更新時間
	private String recupddate="";

	private String taxFormatFlg="";
	private String foreignFormatFlg="";
	private String planrate2="";
	private String planrate3="";
	private String rate2="";
	private String rate3="";
	private String truePayAmtSum="";
	private String trueCostTotalAmt="";
	private String truecostVatTotal="";
	private String accountflg="";
	private String recflg="";
	private String costnum="";
	private String saleno="";
	private String assignflg="";
	private String reccolorv="";
	private String upUsercolorv="";
	private String costFinshusercolorv="";
	private String addusercolorv="";
	
	private Double historyrate3;
	private Double historyrate2;
	
	
	public Double getHistoryrate3() {
		return historyrate3;
	}

	public void setHistoryrate3(Double historyrate3) {
		this.historyrate3 = historyrate3;
	}

	public Double getHistoryrate2() {
		return historyrate2;
	}

	public void setHistoryrate2(Double historyrate2) {
		this.historyrate2 = historyrate2;
	}

	public String getReccolorv() {
		return reccolorv;
	}

	public void setReccolorv(String reccolorv) {
		this.reccolorv = reccolorv;
	}

	public String getUpUsercolorv() {
		return upUsercolorv;
	}

	public void setUpUsercolorv(String upUsercolorv) {
		this.upUsercolorv = upUsercolorv;
	}

	public String getCostFinshusercolorv() {
		return costFinshusercolorv;
	}

	public void setCostFinshusercolorv(String costFinshusercolorv) {
		this.costFinshusercolorv = costFinshusercolorv;
	}

	public String getAddusercolorv() {
		return addusercolorv;
	}

	public void setAddusercolorv(String addusercolorv) {
		this.addusercolorv = addusercolorv;
	}

	public String getAssignflg() {
		return assignflg;
	}

	public void setAssignflg(String assignflg) {
		this.assignflg = assignflg;
	}

	public String getSaleno() {
		return saleno;
	}

	public void setSaleno(String saleno) {
		this.saleno = saleno;
	}

	public String getCostnum() {
		return costnum;
	}

	public void setCostnum(String costnum) {
		this.costnum = costnum;
	}

	public String getForeignFormatFlg() {
		return foreignFormatFlg;
	}

	public void setForeignFormatFlg(String foreignFormatFlg) {
		this.foreignFormatFlg = foreignFormatFlg;
	}

	public String getRecflg() {
		return recflg;
	}

	public void setRecflg(String recflg) {
		this.recflg = recflg;
	}

	public String getAccountflg() {
		return accountflg;
	}

	public void setAccountflg(String accountflg) {
		this.accountflg = accountflg;
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

	public String getTruecostVatTotal() {
		return truecostVatTotal;
	}

	public void setTruecostVatTotal(String truecostVatTotal) {
		this.truecostVatTotal = truecostVatTotal;
	}

	public String getPlanrate2() {
		return planrate2;
	}

	public void setPlanrate2(String planrate2) {
		this.planrate2 = planrate2;
	}

	public String getPlanrate3() {
		return planrate3;
	}

	public void setPlanrate3(String planrate3) {
		this.planrate3 = planrate3;
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

	public String getTaxFormatFlg() {
		return taxFormatFlg;
	}

	public void setTaxFormatFlg(String taxFormatFlg) {
		this.taxFormatFlg = taxFormatFlg;
	}

	public String getRecupdName() {
		return recupdName;
	}

	public void setRecupdName(String recupdName) {
		this.recupdName = recupdName;
	}

	public String getRecupddate() {
		return recupddate;
	}

	public void setRecupddate(String recupddate) {
		this.recupddate = recupddate;
	}

	public String getSaleadmitremark() {
		return saleadmitremark;
	}

	public void setSaleadmitremark(String saleadmitremark) {
		this.saleadmitremark = saleadmitremark;
	}

	public String getRecremark() {
		return recremark;
	}

	public void setRecremark(String recremark) {
		this.recremark = recremark;
	}

	public Double getSaledifference() {
		return saledifference;
	}

	public void setSaledifference(Double saledifference) {
		this.saledifference = saledifference;
	}

	public Double getVatamtdifference() {
		return vatamtdifference;
	}

	public void setVatamtdifference(Double vatamtdifference) {
		this.vatamtdifference = vatamtdifference;
	}

	public Double getReqdifference() {
		return reqdifference;
	}

	public void setReqdifference(Double reqdifference) {
		this.reqdifference = reqdifference;
	}

	public BigDecimal getSalevatamt() {
		return salevatamt;
	}

	public void setSalevatamt(BigDecimal salevatamt) {
		this.salevatamt = salevatamt;
	}

	public BigDecimal getSaleamt() {
		return saleamt;
	}

	public void setSaleamt(BigDecimal saleamt) {
		this.saleamt = saleamt;
	}

	public String getRecdate() {
		return recdate;
	}

	public void setRecdate(String recdate) {
		this.recdate = recdate;
	}

	public String getPlanreqdate() {
		return planreqdate;
	}

	public void setPlanreqdate(String planreqdate) {
		this.planreqdate = planreqdate;
	}

	public String getJobcd() {
		return jobcd;
	}

	public void setJobcd(String jobcd) {
		this.jobcd = jobcd;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getSaletyp() {
		return saletyp;
	}

	public void setSaletyp(String saletyp) {
		this.saletyp = saletyp;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getCldivcd() {
		return cldivcd;
	}

	public void setCldivcd(String cldivcd) {
		this.cldivcd = cldivcd;
	}

	public String getClName() {
		return clName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}

	public String getPayercd() {
		return payercd;
	}

	public void setPayercd(String payercd) {
		this.payercd = payercd;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getGcompany() {
		return gcompany;
	}

	public void setGcompany(String gcompany) {
		this.gcompany = gcompany;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getDlvday() {
		return dlvday;
	}

	public void setDlvday(String dlvday) {
		this.dlvday = dlvday;
	}

	public BigDecimal getPlansale() {
		return plansale;
	}

	public void setPlansale(BigDecimal plansale) {
		this.plansale = plansale;
	}

	public BigDecimal getPlansaleforeignamt() {
		return plansaleforeignamt;
	}

	public void setPlansaleforeignamt(BigDecimal plansaleforeignamt) {
		this.plansaleforeignamt = plansaleforeignamt;
	}

	public BigDecimal getPlanSaleVat() {
		return planSaleVat;
	}

	public void setPlanSaleVat(BigDecimal planSaleVat) {
		this.planSaleVat = planSaleVat;
	}

	public String getPlansalecurecode() {
		return plansalecurecode;
	}

	public void setPlansalecurecode(String plansalecurecode) {
		this.plansalecurecode = plansalecurecode;
	}

	public Integer getPlansaleishave() {
		return plansaleishave;
	}

	public void setPlansaleishave(Integer plansaleishave) {
		this.plansaleishave = plansaleishave;
	}

	public String getPlansalerefer() {
		return plansalerefer;
	}

	public void setPlansalerefer(String plansalerefer) {
		this.plansalerefer = plansalerefer;
	}

	public float getPlansaletax1() {
		return plansaletax1;
	}

	public void setPlansaletax1(float plansaletax1) {
		this.plansaletax1 = plansaletax1;
	}

	public float getPlansaletax2() {
		return plansaletax2;
	}

	public void setPlansaletax2(float plansaletax2) {
		this.plansaletax2 = plansaletax2;
	}

	public float getPlansaletaxtotal() {
		return plansaletaxtotal;
	}

	public void setPlansaletaxtotal(float plansaletaxtotal) {
		this.plansaletaxtotal = plansaletaxtotal;
	}

	public float getPlancosttax() {
		return plancosttax;
	}

	public void setPlancosttax(float plancosttax) {
		this.plancosttax = plancosttax;
	}

	public String getPlansaleforeigncode() {
		return plansaleforeigncode;
	}

	public void setPlansaleforeigncode(String plansaleforeigncode) {
		this.plansaleforeigncode = plansaleforeigncode;
	}

	public float getPlancostamt() {
		return plancostamt;
	}

	public void setPlancostamt(float plancostamt) {
		this.plancostamt = plancostamt;
	}

	public String getPlancostrefer() {
		return plancostrefer;
	}

	public void setPlancostrefer(String plancostrefer) {
		this.plancostrefer = plancostrefer;
	}

	public String getPlancostcurecode() {
		return plancostcurecode;
	}

	public void setPlancostcurecode(String plancostcurecode) {
		this.plancostcurecode = plancostcurecode;
	}

	public float getPlancostforeignamt() {
		return plancostforeignamt;
	}

	public void setPlancostforeignamt(float plancostforeignamt) {
		this.plancostforeignamt = plancostforeignamt;
	}

	public String getPlancostforeigncode() {
		return plancostforeigncode;
	}

	public void setPlancostforeigncode(String plancostforeigncode) {
		this.plancostforeigncode = plancostforeigncode;
	}

	public Integer getPlancostishave() {
		return plancostishave;
	}

	public void setPlancostishave(Integer plancostishave) {
		this.plancostishave = plancostishave;
	}

	public String getAddusercd() {
		return addusercd;
	}

	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getUpdusercd() {
		return updusercd;
	}

	public void setUpdusercd(String updusercd) {
		this.updusercd = updusercd;
	}

	public String getUpUserName() {
		return upUserName;
	}

	public void setUpUserName(String upUserName) {
		this.upUserName = upUserName;
	}

	public String getAdduser() {
		return adduser;
	}

	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}

	public String getUpduser() {
		return upduser;
	}

	public void setUpduser(String upduser) {
		this.upduser = upduser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCompanycd() {
		return companycd;
	}

	public void setCompanycd(Integer companycd) {
		this.companycd = companycd;
	}

	public Integer getDelflg() {
		return delflg;
	}

	public void setDelflg(Integer delflg) {
		this.delflg = delflg;
	}

	public List<JobLableTrn> getJlTrn() {
		return jlTrn;
	}

	public void setJlTrn(List<JobLableTrn> jlTrn) {
		this.jlTrn = jlTrn;
	}

	public List<JobUserLable> getJulable() {
		return julable;
	}

	public void setJulable(List<JobUserLable> julable) {
		this.julable = julable;
	}

	public String getLableid() {
		return lableid;
	}

	public void setLableid(String lableid) {
		this.lableid = lableid;
	}

	public String getLabletext() {
		return labletext;
	}

	public void setLabletext(String labletext) {
		this.labletext = labletext;
	}

	public Integer getLablelevel() {
		return lablelevel;
	}

	public void setLablelevel(Integer lablelevel) {
		this.lablelevel = lablelevel;
	}

	public BigDecimal getPlanreqamt() {
		return planreqamt;
	}

	public void setPlanreqamt(BigDecimal planreqamt) {
		this.planreqamt = planreqamt;
	}

	public String getDivnm() {
		return divnm;
	}

	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}

	public String getDivnmen() {
		return divnmen;
	}

	public void setDivnmen(String divnmen) {
		this.divnmen = divnmen;
	}

	public String getDivnamefull() {
		return divnamefull;
	}

	public void setDivnamefull(String divnamefull) {
		this.divnamefull = divnamefull;
	}

	public int getConfirmFlg() {
		return confirmFlg;
	}

	public void setConfirmFlg(int confirmFlg) {
		this.confirmFlg = confirmFlg;
	}

	public int getDlvFlg() {
		return dlvFlg;
	}

	public void setDlvFlg(int dlvFlg) {
		this.dlvFlg = dlvFlg;
	}

	public int getCostFinshFlg() {
		return costFinshFlg;
	}

	public void setCostFinshFlg(int costFinshFlg) {
		this.costFinshFlg = costFinshFlg;
	}

	public String getCostFinshUser() {
		return costFinshUser;
	}

	public void setCostFinshUser(String costFinshUser) {
		this.costFinshUser = costFinshUser;
	}

	public int getJobEndFlg() {
		return jobEndFlg;
	}

	public void setJobEndFlg(int jobEndFlg) {
		this.jobEndFlg = jobEndFlg;
	}

	public float getPlanpayamt() {
		return planpayamt;
	}

	public void setPlanpayamt(float planpayamt) {
		this.planpayamt = planpayamt;
	}

	public String getPlandalday() {
		return plandalday;
	}

	public void setPlandalday(String plandalday) {
		this.plandalday = plandalday;
	}

	public String getPlansaleusedate() {
		return plansaleusedate;
	}

	public void setPlansaleusedate(String plansaleusedate) {
		this.plansaleusedate = plansaleusedate;
	}

	public String getPlancostusedate() {
		return plancostusedate;
	}

	public void setPlancostusedate(String plancostusedate) {
		this.plancostusedate = plancostusedate;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getUpddate() {
		return upddate;
	}

	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}

}
