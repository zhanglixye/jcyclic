package com.kaiwait.bean.jczh.entity;

public class LendtrnConfirmBeen extends jobPdfCommInfo{
//インプット確認票（立替）
	
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
	//立替社員
	private String lendtrnUser;
	//使用日
	private String lendUseDate;
	//控除
	private String isDeduction;
	//立替金額
	private String lendtrnNum;
	//立替原価
	private String lendtrnCost;
	//仕入増値税
	private String costVatAmt;
	//備考
	private String remark;
	//金額入力
	private String inputAmt;
	//税込
	private String isHave;
	//換算レート
	private String cureCode;
	//適用日
	private String useDate;
	//参照先
	private String refName;
	//新No.
	private String newNo;
	//旧No.
	private String oldNo;
	
	private String companyfullname;
	
	private String currencycode;
	private String currencycodejp;
	private String currencycodeen;
	private String currencycodehk;
	private String foreigntype;
	private String changeutin;
	private String foreignlen;
	private String mylen;
	private String lendforeigntype;
	
	
	public String getLendforeigntype() {
		return lendforeigntype;
	}
	public void setLendforeigntype(String lendforeigntype) {
		this.lendforeigntype = lendforeigntype;
	}
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
	public String getChangeutin() {
		return changeutin;
	}
	public void setChangeutin(String changeutin) {
		this.changeutin = changeutin;
	}
	public String getForeigntype() {
		return foreigntype;
	}
	public void setForeigntype(String foreigntype) {
		this.foreigntype = foreigntype;
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
	public String getCompanyfullname() {
		return companyfullname;
	}
	public void setCompanyfullname(String companyfullname) {
		this.companyfullname = companyfullname;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
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
	public String getLendtrnUser() {
		return lendtrnUser;
	}
	public void setLendtrnUser(String lendtrnUser) {
		this.lendtrnUser = lendtrnUser;
	}
	public String getLendUseDate() {
		return lendUseDate;
	}
	public void setLendUseDate(String lendUseDate) {
		this.lendUseDate = lendUseDate;
	}
	public String getIsDeduction() {
		return isDeduction;
	}
	public void setIsDeduction(String isDeduction) {
		this.isDeduction = isDeduction;
	}
	public String getLendtrnNum() {
		return lendtrnNum;
	}
	public void setLendtrnNum(String lendtrnNum) {
		this.lendtrnNum = lendtrnNum;
	}
	public String getLendtrnCost() {
		return lendtrnCost;
	}
	public void setLendtrnCost(String lendtrnCost) {
		this.lendtrnCost = lendtrnCost;
	}
	public String getCostVatAmt() {
		return costVatAmt;
	}
	public void setCostVatAmt(String costVatAmt) {
		this.costVatAmt = costVatAmt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInputAmt() {
		return inputAmt;
	}
	public void setInputAmt(String inputAmt) {
		this.inputAmt = inputAmt;
	}
	public String getIsHave() {
		return isHave;
	}
	public void setIsHave(String isHave) {
		this.isHave = isHave;
	}
	public String getCureCode() {
		return cureCode;
	}
	public void setCureCode(String cureCode) {
		this.cureCode = cureCode;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
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
