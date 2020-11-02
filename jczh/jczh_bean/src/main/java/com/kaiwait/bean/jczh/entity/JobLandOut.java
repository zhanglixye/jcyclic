package com.kaiwait.bean.jczh.entity;

public class JobLandOut extends jobPdfCommInfo{

	
	
	 private String planSale;//预计卖上
	 private String planCost;//预计原价
	 private String planTax;//预计税金
	 private String profit;//营业收益
	 private String profitRate;//营业收益率
	 private String labelText;//lable 文本
	 private String remark;//备考
	 //预计卖上的外货小数点与4过语言 
	 private String planSaleMoney;
	 private String planSaleMoneyHk;
	 private String planSaleMoneyJp;
	 private String planSaleMoneyEn;
	 private int planSalePoint;
	 //预计原价的外货小数点与4过语言 
	 private String planCostMoney;
	 private String planCostMoneyHk;
	 private String planCostMoneyJp;
	 private String planCostMoneyEn;
	 private int planCostPoint;
	 //预计仕入增值税与卖上增值税
	 private String planCostTax;
	 private String planSaleTax;
	 //预计卖上输入金额 换算code 适用日 税入税拔 参照先
	 private String planSaleForeignAmt;
	 private String planSaleCureCode;
	 private String planSaleUseDate;
	 private String planSaleIsHave;
	 private String planSaleRefer;
	 //预计原价输入金额 换算code 适用日 税入税拔 参照先
	 private String planCostCureCode;
	 private String planCostIsHave;
	 private String planCostUseDate;
	 private String planCostRefer;
	 private String planCostForeignAmt;
	 //更新日 更新者 cd  更新者名
	 private String upddate;
	 private String upUserCd;
	 private String upUserName;
	 //原价完了FLG 
	 private int costFinishFlg;
	 
	 //和面字符串
	 private String planSaleString;
	 private String planSaleTaxString;
	 private String planCostString;
	 private String planCostTaxString;
	 private String costString;
	 private String costTaxString;
	 private String taxString;
	 private String profitRateString;
	 
	 private String planSaleForeignString;
	 private String planSaleCureCodeString;
	 
	 private String planCostForeignString;
	 private String planCostCureCodeString;
	 
	 private String costForeignString;
	 private String costCureCodeString;
	 private String langTyp;
	 private String accountCd;

	public String getAccountCd() {
		return accountCd;
	}
	public void setAccountCd(String accountCd) {
		this.accountCd = accountCd;
	}
	public String getLangTyp() {
		return langTyp;
	}
	public void setLangTyp(String langTyp) {
		this.langTyp = langTyp;
	}
	public int getPlanSalePoint() {
		return planSalePoint;
	}
	public void setPlanSalePoint(int planSalePoint) {
		this.planSalePoint = planSalePoint;
	}
	public int getPlanCostPoint() {
		return planCostPoint;
	}
	public void setPlanCostPoint(int planCostPoint) {
		this.planCostPoint = planCostPoint;
	}
	public String getPlanSaleString() {
		return planSaleString;
	}
	public void setPlanSaleString(String planSaleString) {
		this.planSaleString = planSaleString;
	}
	public String getPlanSaleTaxString() {
		return planSaleTaxString;
	}
	public void setPlanSaleTaxString(String planSaleTaxString) {
		this.planSaleTaxString = planSaleTaxString;
	}
	public String getPlanCostString() {
		return planCostString;
	}
	public void setPlanCostString(String planCostString) {
		this.planCostString = planCostString;
	}
	public String getPlanCostTaxString() {
		return planCostTaxString;
	}
	public void setPlanCostTaxString(String planCostTaxString) {
		this.planCostTaxString = planCostTaxString;
	}
	public String getCostString() {
		return costString;
	}
	public void setCostString(String costString) {
		this.costString = costString;
	}
	public String getCostTaxString() {
		return costTaxString;
	}
	public void setCostTaxString(String costTaxString) {
		this.costTaxString = costTaxString;
	}
	public String getTaxString() {
		return taxString;
	}
	public void setTaxString(String taxString) {
		this.taxString = taxString;
	}
	public String getProfitRateString() {
		return profitRateString;
	}
	public void setProfitRateString(String profitRateString) {
		this.profitRateString = profitRateString;
	}
	public String getPlanSaleForeignString() {
		return planSaleForeignString;
	}
	public void setPlanSaleForeignString(String planSaleForeignString) {
		this.planSaleForeignString = planSaleForeignString;
	}
	public String getPlanSaleCureCodeString() {
		return planSaleCureCodeString;
	}
	public void setPlanSaleCureCodeString(String planSaleCureCodeString) {
		this.planSaleCureCodeString = planSaleCureCodeString;
	}
	public String getPlanCostForeignString() {
		return planCostForeignString;
	}
	public void setPlanCostForeignString(String planCostForeignString) {
		this.planCostForeignString = planCostForeignString;
	}
	public String getPlanCostCureCodeString() {
		return planCostCureCodeString;
	}
	public void setPlanCostCureCodeString(String planCostCureCodeString) {
		this.planCostCureCodeString = planCostCureCodeString;
	}
	public String getCostForeignString() {
		return costForeignString;
	}
	public void setCostForeignString(String costForeignString) {
		this.costForeignString = costForeignString;
	}
	public String getCostCureCodeString() {
		return costCureCodeString;
	}
	public void setCostCureCodeString(String costCureCodeString) {
		this.costCureCodeString = costCureCodeString;
	}
	public int getCostFinishFlg() {
		return costFinishFlg;
	}
	public void setCostFinishFlg(int costFinishFlg) {
		this.costFinishFlg = costFinishFlg;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getUpUserCd() {
		return upUserCd;
	}
	public void setUpUserCd(String upUserCd) {
		this.upUserCd = upUserCd;
	}
	public String getUpUserName() {
		return upUserName;
	}
	public void setUpUserName(String upUserName) {
		this.upUserName = upUserName;
	}
	public String getPlanSaleForeignAmt() {
		return planSaleForeignAmt;
	}
	public void setPlanSaleForeignAmt(String planSaleForeignAmt) {
		this.planSaleForeignAmt = planSaleForeignAmt;
	}
	public String getPlanSaleCureCode() {
		return planSaleCureCode;
	}
	public void setPlanSaleCureCode(String planSaleCureCode) {
		this.planSaleCureCode = planSaleCureCode;
	}
	public String getPlanSaleUseDate() {
		return planSaleUseDate;
	}
	public void setPlanSaleUseDate(String planSaleUseDate) {
		this.planSaleUseDate = planSaleUseDate;
	}
	public String getPlanSaleIsHave() {
		return planSaleIsHave;
	}
	public void setPlanSaleIsHave(String planSaleIsHave) {
		this.planSaleIsHave = planSaleIsHave;
	}
	public String getPlanSaleRefer() {
		return planSaleRefer;
	}
	public void setPlanSaleRefer(String planSaleRefer) {
		this.planSaleRefer = planSaleRefer;
	}
	public String getPlanCostCureCode() {
		return planCostCureCode;
	}
	public void setPlanCostCureCode(String planCostCureCode) {
		this.planCostCureCode = planCostCureCode;
	}
	public String getPlanCostIsHave() {
		return planCostIsHave;
	}
	public void setPlanCostIsHave(String planCostIsHave) {
		this.planCostIsHave = planCostIsHave;
	}
	public String getPlanCostUseDate() {
		return planCostUseDate;
	}
	public void setPlanCostUseDate(String planCostUseDate) {
		this.planCostUseDate = planCostUseDate;
	}
	public String getPlanCostRefer() {
		return planCostRefer;
	}
	public void setPlanCostRefer(String planCostRefer) {
		this.planCostRefer = planCostRefer;
	}
	public String getPlanCostForeignAmt() {
		return planCostForeignAmt;
	}
	public void setPlanCostForeignAmt(String planCostForeignAmt) {
		this.planCostForeignAmt = planCostForeignAmt;
	}
	public String getPlanCostTax() {
		return planCostTax;
	}
	public void setPlanCostTax(String planCostTax) {
		this.planCostTax = planCostTax;
	}
	public String getPlanSaleTax() {
		return planSaleTax;
	}
	public void setPlanSaleTax(String planSaleTax) {
		this.planSaleTax = planSaleTax;
	}
	public String getPlanSaleMoneyHk() {
		return planSaleMoneyHk;
	}
	public void setPlanSaleMoneyHk(String planSaleMoneyHk) {
		this.planSaleMoneyHk = planSaleMoneyHk;
	}
	public String getPlanSaleMoneyJp() {
		return planSaleMoneyJp;
	}
	public void setPlanSaleMoneyJp(String planSaleMoneyJp) {
		this.planSaleMoneyJp = planSaleMoneyJp;
	}
	public String getPlanSaleMoneyEn() {
		return planSaleMoneyEn;
	}
	public void setPlanSaleMoneyEn(String planSaleMoneyEn) {
		this.planSaleMoneyEn = planSaleMoneyEn;
	}
	public String getPlanCostMoneyHk() {
		return planCostMoneyHk;
	}
	public void setPlanCostMoneyHk(String planCostMoneyHk) {
		this.planCostMoneyHk = planCostMoneyHk;
	}
	public String getPlanCostMoneyJp() {
		return planCostMoneyJp;
	}
	public void setPlanCostMoneyJp(String planCostMoneyJp) {
		this.planCostMoneyJp = planCostMoneyJp;
	}
	public String getPlanCostMoneyEn() {
		return planCostMoneyEn;
	}
	public void setPlanCostMoneyEn(String planCostMoneyEn) {
		this.planCostMoneyEn = planCostMoneyEn;
	}
	public String getPlanSaleMoney() {
		return planSaleMoney;
	}
	public void setPlanSaleMoney(String planSaleMoney) {
		this.planSaleMoney = planSaleMoney;
	}
	public String getPlanCostMoney() {
		return planCostMoney;
	}
	public void setPlanCostMoney(String planCostMoney) {
		this.planCostMoney = planCostMoney;
	}
	
	public String getPlanSale() {
		return planSale;
	}
	public void setPlanSale(String planSale) {
		this.planSale = planSale;
	}
	public String getPlanCost() {
		return planCost;
	}
	public void setPlanCost(String planCost) {
		this.planCost = planCost;
	}
	public String getPlanTax() {
		return planTax;
	}
	public void setPlanTax(String planTax) {
		this.planTax = planTax;
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
	public String getLabelText() {
		return labelText;
	}
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	 
}
