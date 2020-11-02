package com.kaiwait.bean.jczh.vo;

import java.util.List;

public class CostListVo {
	private String   jobcd; 
	private String  jobName;
	private String   cldivcd;
	private String   saletype;
	private String     dalday;
	private String     dalday1;
	private String     dalmon;
	private String   diszc;//区分
	private String   disen;//区分
	private String   disjp;//区分
	private String   diszt;//区分
	private String   discd;//区分
	private String   costno;//发注编号
	private String   inputno;//登録票No.
	private String   paycd;//发注先
	private String   name;//案件名
	private String statuscd;//0：発注登録済、1：支払登録済、2：支払申請済、3：支払承認済
	private String status;//下拉状态用来做查询条件
	private String   statuszc;
	private String   statusen;
	private String   statusjp;
	private String   statuszt;
	private String   amt;//原価額
	private String   vatamt;//増値税(仕入)
	private String   payamt;//支払金額（税込）
	private String   adddate;//登録日
	private String   payreqdate;//申請日
	private String   confirmdate;//承認日
	private String   outputflgcd;
	private String   outputflgzc;//発注書印刷ステータス
	private String  outputflgen;
	private String  outputflgjp;
	private String outputflghk;
	private String   payflgcd;
	private String   payflg;//查询用
	private String   payflgzc;//支払ステータス
	private String   payflgen;
	private String   payflgjp;
	private String   payflgzt;
	private String   invoicno;//発票番号
	private String   invoicedate;//発票受領日
	private String   invoicedate1;//発票受領日
	private String   addusercd;//登録者
	private String   payreqemp;//（発注）申請者
	private String   confirmemp;//承認者
	private String   paydlyday;//（発注）納品日
	private String   plandlvday;//（発注）納品予定日
	private String   payhopedate;//支払希望日
	private String   lenddate;//（立替）使用日

	private String   lenduser;//（立替）立替者
	private String   lendname;//（立替）立替内容
	private String   tranitemcode;//（振替）経費科目
	private String   remark;//备考
	private String   costtype;//分類項目
	private String   paydate;
	/*private String   payplandate;*/
	private String   lendnameen;
	private String   lendnamezc;
	private String   lendnamezt;
	private String   lendnamejp;
	private String planpaydate;
	private String   oldinputno;//旧的登録票No.
	private String 	paySkip;
	
	public String getPaySkip() {
		return paySkip;
	}
	public void setPaySkip(String paySkip) {
		this.paySkip = paySkip;
	}
	public String getOldinputno() {
		return oldinputno;
	}
	public void setOldinputno(String oldinputno) {
		this.oldinputno = oldinputno;
	}
	public String getLendnamezt() {
		return lendnamezt;
	}
	public void setLendnamezt(String lendnamezt) {
		this.lendnamezt = lendnamezt;
	}
	public String getLendnameen() {
		return lendnameen;
	}
	public void setLendnameen(String lendnameen) {
		this.lendnameen = lendnameen;
	}
	public String getLendnamezc() {
		return lendnamezc;
	}
	public void setLendnamezc(String lendnamezc) {
		this.lendnamezc = lendnamezc;
	}
	public String getLendnamejp() {
		return lendnamejp;
	}
	public void setLendnamejp(String lendnamejp) {
		this.lendnamejp = lendnamejp;
	}

	
	public String getPlanpaydate() {
		return planpaydate;
	}
	public void setPlanpaydate(String planpaydate) {
		this.planpaydate = planpaydate;
	}
	private int  searchType;////详细检索获取 检索类型 0 ：其他  1 基本
	private String jobendflgcd;
	private String jobendflg;//job终了flg
	private String  accountflgcd;
	private String  accountflg;//締め終了フラグ 0:未；1：终了
	private String   lable;//标签
	private String  lableid;
	private String   salename;//
	private String   cldivname;//
	private String   addusername;//
	private String   payreqempname;//
	private String   confirmempname;//
	private String   tranitemnamezc; //
	private String   tranitemnameen; //
	private String   tranitemnamejp; //
	private String   tranitemnamezt; //
	private String keyword;
	private int companycd;
	private String saleamt;
	private String payeename;
	private String sumCost;
	private String bl;
	private List<String> cldivList;
	private List<String> reqList;
	private List<String> mdList;
	private  String lablelevel;
	private String payremark;
	private int  paylockflg;
	private boolean conditionFlg;
	public boolean isConditionFlg() {
		return conditionFlg;
	}
	public void setConditionFlg(boolean conditionFlg) {
		this.conditionFlg = conditionFlg;
	}
	//模糊检索关键字
	private String str;
	//跳转检索 ad 
	private String ad;
	//跳转检索 日期
	private String checkDate;
	//权限 all
	private String all;
	//扭付得意先
	private String cldivUser;
	//担当
	private String adUser;
	//割当
	private String mdUser;
	
	public String getPayremark() {
		return payremark;
	}
	public void setPayremark(String payremark) {
		this.payremark = payremark;
	}
	private  List<CommonmstVo> payStatsList;
	
	private  List<CommonmstVo> coststatusList;
	
	
	public List<CommonmstVo> getCoststatusList() {
		return coststatusList;
	}
	public void setCoststatusList(List<CommonmstVo> coststatusList) {
		this.coststatusList = coststatusList;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public List<CommonmstVo> getPayStatsList() {
		return payStatsList;
	}
	public void setPayStatsList(List<CommonmstVo> payStatsList) {
		this.payStatsList = payStatsList;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getCldivUser() {
		return cldivUser;
	}
	public void setCldivUser(String cldivUser) {
		this.cldivUser = cldivUser;
	}
	public String getAdUser() {
		return adUser;
	}
	public void setAdUser(String adUser) {
		this.adUser = adUser;
	}
	public String getMdUser() {
		return mdUser;
	}
	public void setMdUser(String mdUser) {
		this.mdUser = mdUser;
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
	public String getBl() {
		return bl;
	}
	public void setBl(String bl) {
		this.bl = bl;
	}
	public String getDalmon() {
		return dalmon;
	}
	public void setDalmon(String dalmon) {
		this.dalmon = dalmon;
	}
	public String getPayflg() {
		return payflg;
	}
	public void setPayflg(String payflg) {
		this.payflg = payflg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusen() {
		return statusen;
	}
	public void setStatusen(String statusen) {
		this.statusen = statusen;
	}
	public String getStatusjp() {
		return statusjp;
	}
	public void setStatusjp(String statusjp) {
		this.statusjp = statusjp;
	}

	public String getOutputflgen() {
		return outputflgen;
	}
	public void setOutputflgen(String outputflgen) {
		this.outputflgen = outputflgen;
	}
	public String getOutputflgjp() {
		return outputflgjp;
	}
	public void setOutputflgjp(String outputflgjp) {
		this.outputflgjp = outputflgjp;
	}
	public String getOutputflghk() {
		return outputflghk;
	}
	public void setOutputflghk(String outputflghk) {
		this.outputflghk = outputflghk;
	}
	public String getPayflgen() {
		return payflgen;
	}
	public void setPayflgen(String payflgen) {
		this.payflgen = payflgen;
	}
	public String getPayflgjp() {
		return payflgjp;
	}
	public void setPayflgjp(String payflgjp) {
		this.payflgjp = payflgjp;
	}
	public String getStatuscd() {
		return statuscd;
	}
	public void setStatuscd(String statuscd) {
		this.statuscd = statuscd;
	}
	public String getOutputflgcd() {
		return outputflgcd;
	}
	public void setOutputflgcd(String outputflgcd) {
		this.outputflgcd = outputflgcd;
	}
	public String getPayflgcd() {
		return payflgcd;
	}
	public void setPayflgcd(String payflgcd) {
		this.payflgcd = payflgcd;
	}
	public String getJobendflgcd() {
		return jobendflgcd;
	}
	public void setJobendflgcd(String jobendflgcd) {
		this.jobendflgcd = jobendflgcd;
	}
	public String getAccountflgcd() {
		return accountflgcd;
	}
	public void setAccountflgcd(String accountflgcd) {
		this.accountflgcd = accountflgcd;
	}
	public String getLableid() {
		return lableid;
	}
	public void setLableid(String lableid) {
		this.lableid = lableid;
	}
	public String getDisen() {
		return disen;
	}
	public void setDisen(String disen) {
		this.disen = disen;
	}
	public String getDisjp() {
		return disjp;
	}
	public void setDisjp(String disjp) {
		this.disjp = disjp;
	}

	public String getDiscd() {
		return discd;
	}
	public void setDiscd(String discd) {
		this.discd = discd;
	}
	public String getSumCost() {
		return sumCost;
	}
	public void setSumCost(String sumCost) {
		this.sumCost = sumCost;
	}
	public String getPayeename() {
		return payeename;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
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
	public String getSaleamt() {
		return saleamt;
	}
	public void setSaleamt(String saleamt) {
		this.saleamt = saleamt;
	}
	public int getCompanycd() {
		return companycd;
	}
	public void setCompanycd(int companycd) {
		this.companycd = companycd;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getInvoicedate1() {
		return invoicedate1;
	}
	public void setInvoicedate1(String invoicedate1) {
		this.invoicedate1 = invoicedate1;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
/*	public String getPayplandate() {
		return payplandate;
	}
	public void setPayplandate(String payplandate) {
		this.payplandate = payplandate;
	}*/
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public String getJobcd() {
		return jobcd;
	}
	public void setJobcd(String jobcd) {
		this.jobcd = jobcd;
	}
	public String getCldivcd() {
		return cldivcd;
	}
	public void setCldivcd(String cldivcd) {
		this.cldivcd = cldivcd;
	}
	public String getSaletype() {
		return saletype;
	}
	public void setSaletype(String saletype) {
		this.saletype = saletype;
	}
	public String getDalday() {
		return dalday;
	}
	public void setDalday(String dalday) {
		this.dalday = dalday;
	}
	public String getDalday1() {
		return dalday1;
	}
	public void setDalday1(String dalday1) {
		this.dalday1 = dalday1;
	}

	public String getDiszc() {
		return diszc;
	}
	public void setDiszc(String diszc) {
		this.diszc = diszc;
	}
	public String getDiszt() {
		return diszt;
	}
	public void setDiszt(String diszt) {
		this.diszt = diszt;
	}
	public String getCostno() {
		return costno;
	}
	public void setCostno(String costno) {
		this.costno = costno;
	}
	public String getInputno() {
		return inputno;
	}
	public void setInputno(String inputno) {
		this.inputno = inputno;
	}
	public String getPaycd() {
		return paycd;
	}
	public void setPaycd(String paycd) {
		this.paycd = paycd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getVatamt() {
		return vatamt;
	}
	public void setVatamt(String vatamt) {
		this.vatamt = vatamt;
	}
	public String getPayamt() {
		return payamt;
	}
	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
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
	public String getOutputflgzc() {
		return outputflgzc;
	}
	public void setOutputflgzc(String outputflgzc) {
		this.outputflgzc = outputflgzc;
	}

	public String getStatuszc() {
		return statuszc;
	}
	public void setStatuszc(String statuszc) {
		this.statuszc = statuszc;
	}
	public String getStatuszt() {
		return statuszt;
	}
	public void setStatuszt(String statuszt) {
		this.statuszt = statuszt;
	}
	public String getPayflgzc() {
		return payflgzc;
	}
	public void setPayflgzc(String payflgzc) {
		this.payflgzc = payflgzc;
	}
	public String getPayflgzt() {
		return payflgzt;
	}
	public void setPayflgzt(String payflgzt) {
		this.payflgzt = payflgzt;
	}

	public String getTranitemnamezc() {
		return tranitemnamezc;
	}
	public void setTranitemnamezc(String tranitemnamezc) {
		this.tranitemnamezc = tranitemnamezc;
	}
	public String getTranitemnameen() {
		return tranitemnameen;
	}
	public void setTranitemnameen(String tranitemnameen) {
		this.tranitemnameen = tranitemnameen;
	}
	public String getTranitemnamejp() {
		return tranitemnamejp;
	}
	public void setTranitemnamejp(String tranitemnamejp) {
		this.tranitemnamejp = tranitemnamejp;
	}
	public String getTranitemnamezt() {
		return tranitemnamezt;
	}
	public void setTranitemnamezt(String tranitemnamezt) {
		this.tranitemnamezt = tranitemnamezt;
	}
	public String getInvoicno() {
		return invoicno;
	}
	public void setInvoicno(String invoicno) {
		this.invoicno = invoicno;
	}
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}
	public String getAddusercd() {
		return addusercd;
	}
	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}
	public String getPayreqemp() {
		return payreqemp;
	}
	public void setPayreqemp(String payreqemp) {
		this.payreqemp = payreqemp;
	}
	public String getConfirmemp() {
		return confirmemp;
	}
	public void setConfirmemp(String confirmemp) {
		this.confirmemp = confirmemp;
	}
	public String getPaydlyday() {
		return paydlyday;
	}
	public void setPaydlyday(String paydlyday) {
		this.paydlyday = paydlyday;
	}
	public String getPlandlvday() {
		return plandlvday;
	}
	public void setPlandlvday(String plandlvday) {
		this.plandlvday = plandlvday;
	}
	
	public String getPayhopedate() {
		return payhopedate;
	}
	public void setPayhopedate(String payhopedate) {
		this.payhopedate = payhopedate;
	}
	public String getLenddate() {
		return lenddate;
	}
	public void setLenddate(String lenddate) {
		this.lenddate = lenddate;
	}
	public String getLenduser() {
		return lenduser;
	}
	public void setLenduser(String lenduser) {
		this.lenduser = lenduser;
	}
	public String getLendname() {
		return lendname;
	}
	public void setLendname(String lendname) {
		this.lendname = lendname;
	}
	public String getTranitemcode() {
		return tranitemcode;
	}
	public void setTranitemcode(String tranitemcode) {
		this.tranitemcode = tranitemcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCosttype() {
		return costtype;
	}
	public void setCosttype(String costtype) {
		this.costtype = costtype;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getSalename() {
		return salename;
	}
	public void setSalename(String salename) {
		this.salename = salename;
	}
	public String getCldivname() {
		return cldivname;
	}
	public void setCldivname(String cldivname) {
		this.cldivname = cldivname;
	}
	public String getAddusername() {
		return addusername;
	}
	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}
	public String getPayreqempname() {
		return payreqempname;
	}
	public void setPayreqempname(String payreqempname) {
		this.payreqempname = payreqempname;
	}
	public String getConfirmempname() {
		return confirmempname;
	}
	public void setConfirmempname(String confirmempname) {
		this.confirmempname = confirmempname;
	}

	public int getPaylockflg() {
		return paylockflg;
	}
	public void setPaylockflg(int paylockflg) {
		this.paylockflg = paylockflg;
	}
	
	
}
