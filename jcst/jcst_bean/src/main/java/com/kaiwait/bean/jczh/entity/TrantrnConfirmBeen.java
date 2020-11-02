package com.kaiwait.bean.jczh.entity;

public class TrantrnConfirmBeen extends jobPdfCommInfo{
//インプット確認票（振替）
	
	//インプット確認票No.
	private String inputNo;
	//登録日
	private String addDate;
	//登録者
	private String addUserName;
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
	//新No.
	private String newNo;
	//旧No.
	private String oldNo;

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
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
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
	public String getNewNo() {
		return newNo;
	}
	public void setNewNo(String newNo) {
		this.newNo = newNo;
	}
	public String getOldNo() {
		return oldNo;
	}
	public void setOldNo(String oldNo) {
		this.oldNo = oldNo;
	}

	
	
}
