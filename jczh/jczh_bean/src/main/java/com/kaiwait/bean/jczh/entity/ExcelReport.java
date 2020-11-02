package com.kaiwait.bean.jczh.entity;

public class ExcelReport extends Calculate{
	private String jobNo;
	private String jobName;
	private String dClientName;
	private String rClientName;
	private String gClientName;
	private String pClientName;
	private String pClientID;
	private String cldivAccountID;
	private String jobCreateDate;
	private String planDlvday;
	private int isSellFlg;
	private String saleTypName;
	private String companyFullName;
	private String cldivID;
	private String labelID;
	private String labelName;
	private String confirmDate;
	private String costNo;
	private String inputNo;
	private Double payAmt;
	private String orderIndex;
	private String payeeID;
	private String payeeName;
	private String orderAddDate;
	private String cName;
	//前月残
	private Double lastMonthResidual;
	//当月発生
	private Double thisMonthHappened;
	//当月精算
	private Double thisMonthAccurate;
	//精算差額
	private Double isThisMonthAccurate;
	//当月残
	private Double isThisMonthHappened;
	
	private String sellupdate;
	private int isInvoicePublish;
	private int isReqPublish; 
	private Double saleAmt;
	private Double saleVat;
	private Double confirmCostTotalAmt;
	private Double notConfirmCostTotalAmt;
	private Double costVatTotal;
	private Double taxTotal;
	private int isThisMonthJob;
	private String dlvday;
	//去掉报表中的函数时，为了不与上面的变量冲突，重新定义新的变量使用
	private Double confirmCostTotalAmtByClinet;
	private Double notConfirmCostTotalAmtByClinet;
	private Double costVatTotalByClinet;
	private Double taxTotalByClinet; 
	private Double profitAmt;
	private String profitAmtRate;
	
	
	public Double getProfitAmt() {
		return profitAmt;
	}
	public void setProfitAmt(Double profitAmt) {
		this.profitAmt = profitAmt;
	}
	public String getProfitAmtRate() {
		return profitAmtRate;
	}
	public void setProfitAmtRate(String profitAmtRate) {
		this.profitAmtRate = profitAmtRate;
	}
	public Double getConfirmCostTotalAmtByClinet() {
		return confirmCostTotalAmtByClinet;
	}
	public void setConfirmCostTotalAmtByClinet(Double confirmCostTotalAmtByClinet) {
		this.confirmCostTotalAmtByClinet = confirmCostTotalAmtByClinet;
	}
	public Double getNotConfirmCostTotalAmtByClinet() {
		return notConfirmCostTotalAmtByClinet;
	}
	public void setNotConfirmCostTotalAmtByClinet(Double notConfirmCostTotalAmtByClinet) {
		this.notConfirmCostTotalAmtByClinet = notConfirmCostTotalAmtByClinet;
	}
	public Double getCostVatTotalByClinet() {
		return costVatTotalByClinet;
	}
	public void setCostVatTotalByClinet(Double costVatTotalByClinet) {
		this.costVatTotalByClinet = costVatTotalByClinet;
	}
	public Double getTaxTotalByClinet() {
		return taxTotalByClinet;
	}
	public void setTaxTotalByClinet(Double taxTotalByClinet) {
		this.taxTotalByClinet = taxTotalByClinet;
	}
	public String getDlvday() {
		return dlvday;
	}
	public void setDlvday(String dlvday) {
		this.dlvday = dlvday;
	}
	public int getIsThisMonthJob() {
		return isThisMonthJob;
	}
	public void setIsThisMonthJob(int isThisMonthJob) {
		this.isThisMonthJob = isThisMonthJob;
	}
	public String getSellupdate() {
		return sellupdate;
	}
	public void setSellupdate(String sellupdate) {
		this.sellupdate = sellupdate;
	}
	public int getIsInvoicePublish() {
		return isInvoicePublish;
	}
	public void setIsInvoicePublish(int isInvoicePublish) {
		this.isInvoicePublish = isInvoicePublish;
	}
	public int getIsReqPublish() {
		return isReqPublish;
	}
	public void setIsReqPublish(int isReqPublish) {
		this.isReqPublish = isReqPublish;
	}
	public Double getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(Double saleAmt) {
		this.saleAmt = saleAmt;
	}
	public Double getSaleVat() {
		return saleVat;
	}
	public void setSaleVat(Double saleVat) {
		this.saleVat = saleVat;
	}
	public Double getConfirmCostTotalAmt() {
		return confirmCostTotalAmt;
	}
	public void setConfirmCostTotalAmt(Double confirmCostTotalAmt) {
		this.confirmCostTotalAmt = confirmCostTotalAmt;
	}
	public Double getNotConfirmCostTotalAmt() {
		return notConfirmCostTotalAmt;
	}
	public void setNotConfirmCostTotalAmt(Double notConfirmCostTotalAmt) {
		this.notConfirmCostTotalAmt = notConfirmCostTotalAmt;
	}
	public Double getCostVatTotal() {
		return costVatTotal;
	}
	public void setCostVatTotal(Double costVatTotal) {
		this.costVatTotal = costVatTotal;
	}
	public Double getTaxTotal() {
		return taxTotal;
	}
	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}
	public String getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getPayeeID() {
		return payeeID;
	}
	public void setPayeeID(String payeeID) {
		this.payeeID = payeeID;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getOrderAddDate() {
		return orderAddDate;
	}
	public void setOrderAddDate(String orderAddDate) {
		this.orderAddDate = orderAddDate;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Double getLastMonthResidual() {
		return lastMonthResidual;
	}
	public void setLastMonthResidual(Double lastMonthResidual) {
		this.lastMonthResidual = lastMonthResidual;
	}
	public Double getThisMonthHappened() {
		return thisMonthHappened;
	}
	public void setThisMonthHappened(Double thisMonthHappened) {
		this.thisMonthHappened = thisMonthHappened;
	}
	public Double getThisMonthAccurate() {
		return thisMonthAccurate;
	}
	public void setThisMonthAccurate(Double thisMonthAccurate) {
		this.thisMonthAccurate = thisMonthAccurate;
	}
	public Double getIsThisMonthAccurate() {
		return isThisMonthAccurate;
	}
	public void setIsThisMonthAccurate(Double isThisMonthAccurate) {
		this.isThisMonthAccurate = isThisMonthAccurate;
	}
	public Double getIsThisMonthHappened() {
		return isThisMonthHappened;
	}
	public void setIsThisMonthHappened(Double isThisMonthHappened) {
		this.isThisMonthHappened = isThisMonthHappened;
	}
	public Double getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}
	public String getpClientName() {
		return pClientName;
	}
	public void setpClientName(String pClientName) {
		this.pClientName = pClientName;
	}
	public String getpClientID() {
		return pClientID;
	}
	public void setpClientID(String pClientID) {
		this.pClientID = pClientID;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getCostNo() {
		return costNo;
	}
	public void setCostNo(String costNo) {
		this.costNo = costNo;
	}
	public String getInputNo() {
		return inputNo;
	}
	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}
	public String getCldivAccountID() {
		return cldivAccountID;
	}
	public void setCldivAccountID(String cldivAccountID) {
		this.cldivAccountID = cldivAccountID;
	}
	public String getLabelID() {
		return labelID;
	}
	public void setLabelID(String labelID) {
		this.labelID = labelID;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getCldivID() {
		return cldivID;
	}
	public void setCldivID(String cldivID) {
		this.cldivID = cldivID;
	}
	public String getCompanyFullName() {
		return companyFullName;
	}
	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
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
	public String getdClientName() {
		return dClientName;
	}
	public void setdClientName(String dClientName) {
		this.dClientName = dClientName;
	}
	public String getrClientName() {
		return rClientName;
	}
	public void setrClientName(String rClientName) {
		this.rClientName = rClientName;
	}
	public String getgClientName() {
		return gClientName;
	}
	public void setgClientName(String gClientName) {
		this.gClientName = gClientName;
	}
	public String getJobCreateDate() {
		return jobCreateDate;
	}
	public void setJobCreateDate(String jobCreateDate) {
		this.jobCreateDate = jobCreateDate;
	}
	public String getPlanDlvday() {
		return planDlvday;
	}
	public void setPlanDlvday(String planDlvday) {
		this.planDlvday = planDlvday;
	}
	public int getIsSellFlg() {
		return isSellFlg;
	}
	public void setIsSellFlg(int isSellFlg) {
		this.isSellFlg = isSellFlg;
	}
	public String getSaleTypName() {
		return saleTypName;
	}
	public void setSaleTypName(String saleTypName) {
		this.saleTypName = saleTypName;
	}
	 public boolean equals(Object obj) {
		 ExcelReport u = (ExcelReport) obj;
         return cldivID.equals(u.cldivID);
     }
 
     public int hashCode() {
         String in = cldivID;
         return in.hashCode();
    }
	
}