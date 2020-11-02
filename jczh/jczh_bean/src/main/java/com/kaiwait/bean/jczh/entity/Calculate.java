package com.kaiwait.bean.jczh.entity;


public class Calculate{
	//实际卖上金额
	private Double saleAmt;
	//预计卖上
	private Double planSaleAmt;
	//预计成本
	private Double planCostAmt;
	//实际成本
	private Double costAmt;
	//预计请求额
	private Double planReqAmt;
	//实际请求额
	private Double reqAmt;
	//预计支付
	private Double planPayAmt;
	//实际支付
	private Double payAmt;
	//文化税率
	private Double rate;
	//增值附加税率
	private Double rate1;
	//实际卖上增值税
	private Double saleVatAmt;
	//预计卖上增值税
	private Double planSaleVatAmt;
	//预计仕入增值税
	private Double planCostVatAmt;
	//实际仕入增值税
	private Double costVatAmt;
	//成本是否终止FLG
	private int isCostFinsh;
	//成本条数
	private int countCost;
	//营业利润
	private Double profit;
	//营业利润率
	private Double profitRate;
	//小数点
	private int pointNum;
	//外货端数flg
	private int foreignFormatFlg;
	//税金端数flg
	private int taxFormatFlg;
	//税金合计
	private Double taxTotal;
	//文化税金
	private Double taxWH;
	//增值附加税金
	private Double taxZZF;
	
	
	
	public Double getTaxTotal() {
		return taxTotal;
	}
	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}
	public Double getTaxWH() {
		return taxWH;
	}
	public void setTaxWH(Double taxWH) {
		this.taxWH = taxWH;
	}
	public Double getTaxZZF() {
		return taxZZF;
	}
	public void setTaxZZF(Double taxZZF) {
		this.taxZZF = taxZZF;
	}
	public Double getPlanSaleAmt() {
		return planSaleAmt;
	}
	public void setPlanSaleAmt(Double planSaleAmt) {
		this.planSaleAmt = planSaleAmt;
	}
	public Double getPlanSaleVatAmt() {
		return planSaleVatAmt;
	}
	public void setPlanSaleVatAmt(Double planSaleVatAmt) {
		this.planSaleVatAmt = planSaleVatAmt;
	}
	public int getPointNum() {
		return pointNum;
	}
	public void setPointNum(int pointNum) {
		this.pointNum = pointNum;
	}
	public int getForeignFormatFlg() {
		return foreignFormatFlg;
	}
	public void setForeignFormatFlg(int foreignFormatFlg) {
		this.foreignFormatFlg = foreignFormatFlg;
	}
	public int getTaxFormatFlg() {
		return taxFormatFlg;
	}
	public void setTaxFormatFlg(int taxFormatFlg) {
		this.taxFormatFlg = taxFormatFlg;
	}
	public Double getPlanReqAmt() {
		return planReqAmt;
	}
	public void setPlanReqAmt(Double planReqAmt) {
		this.planReqAmt = planReqAmt;
	}
	public Double getPlanPayAmt() {
		return planPayAmt;
	}
	public void setPlanPayAmt(Double planPayAmt) {
		this.planPayAmt = planPayAmt;
	}
	public Double getPlanCostVatAmt() {
		return planCostVatAmt;
	}
	public void setPlanCostVatAmt(Double planCostVatAmt) {
		this.planCostVatAmt = planCostVatAmt;
	}
	public Double getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(Double saleAmt) {
		this.saleAmt = saleAmt;
	}
	public Double getPlanCostAmt() {
		return planCostAmt;
	}
	public void setPlanCostAmt(Double planCostAmt) {
		this.planCostAmt = planCostAmt;
	}
	public Double getCostAmt() {
		return costAmt;
	}
	public void setCostAmt(Double costAmt) {
		this.costAmt = costAmt;
	}
	public Double getReqAmt() {
		return reqAmt;
	}
	public void setReqAmt(Double reqAmt) {
		this.reqAmt = reqAmt;
	}
	public Double getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getRate1() {
		return rate1;
	}
	public void setRate1(Double rate1) {
		this.rate1 = rate1;
	}
	public Double getSaleVatAmt() {
		return saleVatAmt;
	}
	public void setSaleVatAmt(Double saleVatAmt) {
		this.saleVatAmt = saleVatAmt;
	}
	public Double getCostVatAmt() {
		return costVatAmt;
	}
	public void setCostVatAmt(Double costVatAmt) {
		this.costVatAmt = costVatAmt;
	}
	public int getIsCostFinsh() {
		return isCostFinsh;
	}
	public void setIsCostFinsh(int isCostFinsh) {
		this.isCostFinsh = isCostFinsh;
	}
	public int getCountCost() {
		return countCost;
	}
	public void setCountCost(int countCost) {
		this.countCost = countCost;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Double getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}
	
}
