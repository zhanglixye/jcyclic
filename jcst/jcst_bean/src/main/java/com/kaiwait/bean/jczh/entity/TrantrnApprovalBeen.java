package com.kaiwait.bean.jczh.entity;

public class TrantrnApprovalBeen extends jobPdfCommInfo{
//経費振替承認票
	
	//インプット確認票No.
	private String inputNo;
	//承認日
	private String approvalDate;
	//承認者
	private String approvalUser;
	//得意先コード
	private String dClCode;
	//得意先
	private String dClName;
	//jobNo
	private String jobNo;
	//件名
	private String jobName;
	//売上種目
	private String saleTypeName;
	//請求先
	private String rClName;
	//相手先G会社
	private String hClName;
	//計上予定日
	private String planDlvDay;
	//計上日
	private String dlvDay;
	//責任者
	private String zUserName;
	//営業担当
	private String yUserName;
	//割当担当
	private String mdUserName;
	
	//経費科目
	private String moneyType;
	//経費科目
	private String moneyTypeen;
	//経費科目
	private String moneyTypejp;
	//経費科目
	private String moneyTypehk;
	//案件名
	private String caseName;
	//振替日
	private String trantrnDate;
	//振替金額
	private String trantrnNum;	
	//備考
	private String remark;
	//科目
	private String type;
	//借方
	private String debit;
	//貸方
	private String credit;

	private String companyfullname;
	
	private String currencycode;
	private String currencycodejp;
	private String currencycodeen;
	private String currencycodehk;
	private String foreignlen;
	private String mylen;
	
	public String getMoneyTypeen() {
		return moneyTypeen;
	}
	public void setMoneyTypeen(String moneyTypeen) {
		this.moneyTypeen = moneyTypeen;
	}
	public String getMoneyTypejp() {
		return moneyTypejp;
	}
	public void setMoneyTypejp(String moneyTypejp) {
		this.moneyTypejp = moneyTypejp;
	}
	public String getMoneyTypehk() {
		return moneyTypehk;
	}
	public void setMoneyTypehk(String moneyTypehk) {
		this.moneyTypehk = moneyTypehk;
	}
	public String getMylen() {
		return mylen;
	}
	public void setMylen(String mylen) {
		this.mylen = mylen;
	}
	public String getForeignlen() {
		return foreignlen;
	}
	public void setForeignlen(String foreignlen) {
		this.foreignlen = foreignlen;
	}
	
	public String getCurrencycodejp() {
		return currencycodejp;
	}
	public void setCurrencycodejp(String currencycodejp) {
		this.currencycodejp = currencycodejp;
	}
	public String getCurrencycodeen() {
		return currencycodeen;
	}
	public void setCurrencycodeen(String currencycodeen) {
		this.currencycodeen = currencycodeen;
	}
	public String getCurrencycodehk() {
		return currencycodehk;
	}
	public void setCurrencycodehk(String currencycodehk) {
		this.currencycodehk = currencycodehk;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
	public String getCompanyfullname() {
		return companyfullname;
	}
	public void setCompanyfullname(String companyfullname) {
		this.companyfullname = companyfullname;
	}
	public String getInputNo() {
		return inputNo;
	}
	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}
	public String getdClCode() {
		return dClCode;
	}
	public void setdClCode(String dClCode) {
		this.dClCode = dClCode;
	}
	public String getdClName() {
		return dClName;
	}
	public void setdClName(String dClName) {
		this.dClName = dClName;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getSaleTypeName() {
		return saleTypeName;
	}
	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}
	public String getrClName() {
		return rClName;
	}
	public void setrClName(String rClName) {
		this.rClName = rClName;
	}
	public String gethClName() {
		return hClName;
	}
	public void sethClName(String hClName) {
		this.hClName = hClName;
	}
	public String getPlanDlvDay() {
		return planDlvDay;
	}
	public void setPlanDlvDay(String planDlvDay) {
		this.planDlvDay = planDlvDay;
	}
	public String getDlvDay() {
		return dlvDay;
	}
	public void setDlvDay(String dlvDay) {
		this.dlvDay = dlvDay;
	}
	public String getzUserName() {
		return zUserName;
	}
	public void setzUserName(String zUserName) {
		this.zUserName = zUserName;
	}
	public String getyUserName() {
		return yUserName;
	}
	public void setyUserName(String yUserName) {
		this.yUserName = yUserName;
	}
	public String getMdUserName() {
		return mdUserName;
	}
	public void setMdUserName(String mdUserName) {
		this.mdUserName = mdUserName;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getTrantrnDate() {
		return trantrnDate;
	}
	public void setTrantrnDate(String trantrnDate) {
		this.trantrnDate = trantrnDate;
	}
	public String getTrantrnNum() {
		return trantrnNum;
	}
	public void setTrantrnNum(String trantrnNum) {
		this.trantrnNum = trantrnNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	

	
	
}
