package com.kaiwait.bean.jczh.entity;

public class AccountMonth{
	private Double sellRegistrationNotConfirmAmt;
	private Double sellIsConfirmAmt;
	//卖上承认件数
	private int sellIsConfirmNums;
	private int sellCountNums;
	private Double orderAmtTotal;
	private Double costAmTotal;
	private Double lendAmtTotal;
	//卖上登陆件数
	private int isSellRegistrationNums;
	//卖上未登录件数
	private int notSellRegistrationNums;
	//卖上登陆金额
	private Double isSellRegistrationAmt;
	//卖上为登陆金额
	private Double notSellRegistrationAmt;
	//原价未终了件数
	private int costNotFinshNums;
	//外发成本未承认金额（暂估）
	private Double notConfirmCostAmt;
	//外发成本承认金额
	private Double confirmCostAmt;
	//立替成本未承认金额（暂估）
	private Double notConfirmLendAmt;
	//立替成本承认金额
	private Double confirmLendAmt;
	//振替金额
	private Double tranTotalAmt;
	//支付申请未承认件数
	private int payReqNotConfirmNums;
	//立替保留必须承认件数
	private int notConfirmLendNums;
	//振替为承认件数
	private int notConfirmTranNums;
	private int sellRegistrationNotConfirmNums;
	private int isCostFinshFlg;
	private int isSellconfirmFlg;
	private int invoiceFlg;
	private int jobNotInvoiceNoNums;
	private int jobNotJournalNums;
	private int sellInvoiceIsNullConts;
	
	public int getSellInvoiceIsNullConts() {
		return sellInvoiceIsNullConts;
	}
	public void setSellInvoiceIsNullConts(int sellInvoiceIsNullConts) {
		this.sellInvoiceIsNullConts = sellInvoiceIsNullConts;
	}
	public int getInvoiceFlg() {
		return invoiceFlg;
	}
	public void setInvoiceFlg(int invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}
	public int getJobNotInvoiceNoNums() {
		return jobNotInvoiceNoNums;
	}
	public void setJobNotInvoiceNoNums(int jobNotInvoiceNoNums) {
		this.jobNotInvoiceNoNums = jobNotInvoiceNoNums;
	}
	public int getJobNotJournalNums() {
		return jobNotJournalNums;
	}
	public void setJobNotJournalNums(int jobNotJournalNums) {
		this.jobNotJournalNums = jobNotJournalNums;
	}
	public int getIsCostFinshFlg() {
		return isCostFinshFlg;
	}
	public void setIsCostFinshFlg(int isCostFinshFlg) {
		this.isCostFinshFlg = isCostFinshFlg;
	}
	public int getIsSellconfirmFlg() {
		return isSellconfirmFlg;
	}
	public void setIsSellconfirmFlg(int isSellconfirmFlg) {
		this.isSellconfirmFlg = isSellconfirmFlg;
	}
	public Double getSellRegistrationNotConfirmAmt() {
		return sellRegistrationNotConfirmAmt;
	}
	public void setSellRegistrationNotConfirmAmt(Double sellRegistrationNotConfirmAmt) {
		this.sellRegistrationNotConfirmAmt = sellRegistrationNotConfirmAmt;
	}
	public Double getSellIsConfirmAmt() {
		return sellIsConfirmAmt;
	}
	public void setSellIsConfirmAmt(Double sellIsConfirmAmt) {
		this.sellIsConfirmAmt = sellIsConfirmAmt;
	}
	public int getSellIsConfirmNums() {
		return sellIsConfirmNums;
	}
	public void setSellIsConfirmNums(int sellIsConfirmNums) {
		this.sellIsConfirmNums = sellIsConfirmNums;
	}
	public int getSellRegistrationNotConfirmNums() {
		return sellRegistrationNotConfirmNums;
	}
	public void setSellRegistrationNotConfirmNums(int sellRegistrationNotConfirmNums) {
		this.sellRegistrationNotConfirmNums = sellRegistrationNotConfirmNums;
	}
	public int getSellCountNums() {
		return sellCountNums;
	}
	public void setSellCountNums(int sellCountNums) {
		this.sellCountNums = sellCountNums;
	}
	public Double getOrderAmtTotal() {
		return orderAmtTotal;
	}
	public void setOrderAmtTotal(Double orderAmtTotal) {
		this.orderAmtTotal = orderAmtTotal;
	}
	public Double getCostAmTotal() {
		return costAmTotal;
	}
	public void setCostAmTotal(Double costAmTotal) {
		this.costAmTotal = costAmTotal;
	}
	public Double getLendAmtTotal() {
		return lendAmtTotal;
	}
	public void setLendAmtTotal(Double lendAmtTotal) {
		this.lendAmtTotal = lendAmtTotal;
	}
	public int getPayReqNotConfirmNums() {
		return payReqNotConfirmNums;
	}
	public void setPayReqNotConfirmNums(int payReqNotConfirmNums) {
		this.payReqNotConfirmNums = payReqNotConfirmNums;
	}
	public int getNotConfirmLendNums() {
		return notConfirmLendNums;
	}
	public void setNotConfirmLendNums(int notConfirmLendNums) {
		this.notConfirmLendNums = notConfirmLendNums;
	}
	public int getNotConfirmTranNums() {
		return notConfirmTranNums;
	}
	public void setNotConfirmTranNums(int notConfirmTranNums) {
		this.notConfirmTranNums = notConfirmTranNums;
	}
	public Double getNotConfirmCostAmt() {
		return notConfirmCostAmt;
	}
	public void setNotConfirmCostAmt(Double notConfirmCostAmt) {
		this.notConfirmCostAmt = notConfirmCostAmt;
	}
	public Double getConfirmCostAmt() {
		return confirmCostAmt;
	}
	public void setConfirmCostAmt(Double confirmCostAmt) {
		this.confirmCostAmt = confirmCostAmt;
	}
	public Double getNotConfirmLendAmt() {
		return notConfirmLendAmt;
	}
	public void setNotConfirmLendAmt(Double notConfirmLendAmt) {
		this.notConfirmLendAmt = notConfirmLendAmt;
	}
	public Double getConfirmLendAmt() {
		return confirmLendAmt;
	}
	public void setConfirmLendAmt(Double confirmLendAmt) {
		this.confirmLendAmt = confirmLendAmt;
	}
	public Double getTranTotalAmt() {
		return tranTotalAmt;
	}
	public void setTranTotalAmt(Double tranTotalAmt) {
		this.tranTotalAmt = tranTotalAmt;
	}
	public int getIsSellRegistrationNums() {
		return isSellRegistrationNums;
	}
	public void setIsSellRegistrationNums(int isSellRegistrationNums) {
		this.isSellRegistrationNums = isSellRegistrationNums;
	}
	public int getNotSellRegistrationNums() {
		return notSellRegistrationNums;
	}
	public void setNotSellRegistrationNums(int notSellRegistrationNums) {
		this.notSellRegistrationNums = notSellRegistrationNums;
	}
	public Double getIsSellRegistrationAmt() {
		return isSellRegistrationAmt;
	}
	public void setIsSellRegistrationAmt(Double isSellRegistrationAmt) {
		this.isSellRegistrationAmt = isSellRegistrationAmt;
	}
	public Double getNotSellRegistrationAmt() {
		return notSellRegistrationAmt;
	}
	public void setNotSellRegistrationAmt(Double notSellRegistrationAmt) {
		this.notSellRegistrationAmt = notSellRegistrationAmt;
	}
	public int getCostNotFinshNums() {
		return costNotFinshNums;
	}
	public void setCostNotFinshNums(int costNotFinshNums) {
		this.costNotFinshNums = costNotFinshNums;
	}
	
	
}