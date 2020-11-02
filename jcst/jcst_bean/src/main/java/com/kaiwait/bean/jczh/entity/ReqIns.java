package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class ReqIns extends jobPdfCommInfo {
	private String reqMoney;
	private String saleMoney;
	private String saleRate;//卖上增值税
	private String costMoney;
	private String costRate;//仕入增值税
	private String planCostTax;
	private String rate2;//税金2
	private String rate3;//税金3
	private String profit;
	private String profitRate;
	private List<Cost> cost;
	private int costNum;
	private int costFinishFlg;
	//右侧卖上金额
	private String saleForeignAmt;//外货金额
	private String saleIsHave;
	private String rateType;//税入税拔
	private String cureCode;//换算
	private String saleForeignCode;//外货
	private String saleMoneyUnit;
	private String saleMoneyUnitEn;
	private String saleMoneyUnitHk;
	private String saleMoneyUnitJp;
	private int salePoint;
	
	private String userDate;//适用日
	private String saleRefer;
	private String remark;
	private String times;
	private String timeName;
	private String planCostMoney;
	private String saleChangeUnit;
	//需要整合的字符串
	private String saleMoneyString;
	private String saleTaxString;
	private String costMoneyString;
	private String costTaxString ;
	private String tax2String;
	private String tax3String;
	private String profitString;
	private String profitRateString;
	private String saleForeignAmtString;
	private String cureCodeString;
	
	//卖上承认信息
	private String saleAdminDate;
	private String saleAdminRemark;
	private String saleAdminUser;
	//发票
	private String inTimes;
	
	public int getSalePoint() {
		return salePoint;
	}
	public void setSalePoint(int salePoint) {
		this.salePoint = salePoint;
	}
	public String getInTimes() {
		return inTimes;
	}
	public void setInTimes(String inTimes) {
		this.inTimes = inTimes;
	}
	public String getSaleAdminDate() {
		return saleAdminDate;
	}
	public void setSaleAdminDate(String saleAdminDate) {
		this.saleAdminDate = saleAdminDate;
	}
	public String getSaleAdminRemark() {
		return saleAdminRemark;
	}
	public void setSaleAdminRemark(String saleAdminRemark) {
		this.saleAdminRemark = saleAdminRemark;
	}
	public String getSaleAdminUser() {
		return saleAdminUser;
	}
	public void setSaleAdminUser(String saleAdminUser) {
		this.saleAdminUser = saleAdminUser;
	}
	public String getSaleIsHave() {
		return saleIsHave;
	}
	public void setSaleIsHave(String saleIsHave) {
		this.saleIsHave = saleIsHave;
	}
	public String getSaleMoneyString() {
		return saleMoneyString;
	}
	public void setSaleMoneyString(String saleMoneyString) {
		this.saleMoneyString = saleMoneyString;
	}
	public String getSaleTaxString() {
		return saleTaxString;
	}
	public void setSaleTaxString(String saleTaxString) {
		this.saleTaxString = saleTaxString;
	}
	public String getCostMoneyString() {
		return costMoneyString;
	}
	public void setCostMoneyString(String costMoneyString) {
		this.costMoneyString = costMoneyString;
	}
	public String getCostTaxString() {
		return costTaxString;
	}
	public void setCostTaxString(String costTaxString) {
		this.costTaxString = costTaxString;
	}
	public String getTax2String() {
		return tax2String;
	}
	public void setTax2String(String tax2String) {
		this.tax2String = tax2String;
	}
	public String getTax3String() {
		return tax3String;
	}
	public void setTax3String(String tax3String) {
		this.tax3String = tax3String;
	}
	public String getProfitString() {
		return profitString;
	}
	public void setProfitString(String profitString) {
		this.profitString = profitString;
	}
	public String getProfitRateString() {
		return profitRateString;
	}
	public void setProfitRateString(String profitRateString) {
		this.profitRateString = profitRateString;
	}
	public String getSaleForeignAmtString() {
		return saleForeignAmtString;
	}
	public void setSaleForeignAmtString(String saleForeignAmtString) {
		this.saleForeignAmtString = saleForeignAmtString;
	}
	public String getCureCodeString() {
		return cureCodeString;
	}
	public void setCureCodeString(String cureCodeString) {
		this.cureCodeString = cureCodeString;
	}
	public String getPlanCostTax() {
		return planCostTax;
	}
	public void setPlanCostTax(String planCostTax) {
		this.planCostTax = planCostTax;
	}
	public String getSaleMoneyUnit() {
		return saleMoneyUnit;
	}
	public void setSaleMoneyUnit(String saleMoneyUnit) {
		this.saleMoneyUnit = saleMoneyUnit;
	}
	public String getSaleMoneyUnitEn() {
		return saleMoneyUnitEn;
	}
	public void setSaleMoneyUnitEn(String saleMoneyUnitEn) {
		this.saleMoneyUnitEn = saleMoneyUnitEn;
	}
	public String getSaleMoneyUnitHk() {
		return saleMoneyUnitHk;
	}
	public void setSaleMoneyUnitHk(String saleMoneyUnitHk) {
		this.saleMoneyUnitHk = saleMoneyUnitHk;
	}
	public String getSaleMoneyUnitJp() {
		return saleMoneyUnitJp;
	}
	public void setSaleMoneyUnitJp(String saleMoneyUnitJp) {
		this.saleMoneyUnitJp = saleMoneyUnitJp;
	}
	public String getSaleChangeUnit() {
		return saleChangeUnit;
	}
	public void setSaleChangeUnit(String saleChangeUnit) {
		this.saleChangeUnit = saleChangeUnit;
	}
	public String getPlanCostMoney() {
		return planCostMoney;
	}
	public void setPlanCostMoney(String planCostMoney) {
		this.planCostMoney = planCostMoney;
	}
	public int getCostFinishFlg() {
		return costFinishFlg;
	}
	public void setCostFinishFlg(int costFinishFlg) {
		this.costFinishFlg = costFinishFlg;
	}
	public List<Cost> getCost() {
		return cost;
	}
	public void setCost(List<Cost> cost) {
		this.cost = cost;
	}
	public int getCostNum() {
		return costNum;
	}
	public void setCostNum(int costNum) {
		this.costNum = costNum;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}
	public String getReqMoney() {
		return reqMoney;
	}
	public void setReqMoney(String reqMoney) {
		this.reqMoney = reqMoney;
	}
	public String getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(String saleMoney) {
		this.saleMoney = saleMoney;
	}
	public String getSaleRate() {
		return saleRate;
	}
	public void setSaleRate(String saleRate) {
		this.saleRate = saleRate;
	}
	public String getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(String costMoney) {
		this.costMoney = costMoney;
	}
	public String getCostRate() {
		return costRate;
	}
	public void setCostRate(String costRate) {
		this.costRate = costRate;
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
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}
	public String getSaleForeignAmt() {
		return saleForeignAmt;
	}
	public void setSaleForeignAmt(String saleForeignAmt) {
		this.saleForeignAmt = saleForeignAmt;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getCureCode() {
		return cureCode;
	}
	public void setCureCode(String cureCode) {
		this.cureCode = cureCode;
	}
	public String getSaleForeignCode() {
		return saleForeignCode;
	}
	public void setSaleForeignCode(String saleForeignCode) {
		this.saleForeignCode = saleForeignCode;
	}
	public String getUserDate() {
		return userDate;
	}
	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}
	public String getSaleRefer() {
		return saleRefer;
	}
	public void setSaleRefer(String saleRefer) {
		this.saleRefer = saleRefer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
