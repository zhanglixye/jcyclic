package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class Job {
	
	private String jobNo="";
	//JOB对象
	private JobLand jobObject;
	//卖上对象
	private SaleType saleObject;
	//外发对象
	private Cost costObject;
	//支付对象
	private Pay payObject;
	//立替对象
	private Lendtrn lendObject;
	//振替对象
	private Trantrn tranObject;
	//JobLable
	private List<JobLableTrn> jobLableList;
	//担当者
	private List<JobUserLable> jobUserList;
	//外发list
	private List<Cost> costList;
	//外发LableList
	private List<JobLableTrn> costLableList;
	//支付LableList
	private List<JobLableTrn> payLableList;
	//立替LableList
	private List<JobLableTrn> lendLableList;
	//振替LableList
	private List<JobLableTrn> tranLableList;
	//支付凭证
	private List<Prooftrn> payProoList;
	//立替凭证
	private List<Prooftrn> lendProoList;
	
	private List<JobUserLable> jobUserLable;
	private List<Cost> foreign;
	
	//JOB对象
	private JobInfo jobInfo;
		//卖上对象
	private SaleInfo saleInfo;
	
	private Skip skip;
	private String orderType;
	private Double purposrAmt;
	private String orderNo;
	private Double rate2;
	private Double rate3;
	private int isThisMonth;
	private String userIDByMonth;
	
	
	public String getUserIDByMonth() {
		return userIDByMonth;
	}
	public void setUserIDByMonth(String userIDByMonth) {
		this.userIDByMonth = userIDByMonth;
	}
	public int getIsThisMonth() {
		return isThisMonth;
	}
	public void setIsThisMonth(int isThisMonth) {
		this.isThisMonth = isThisMonth;
	}
	public Double getRate2() {
		return rate2;
	}
	public void setRate2(Double rate2) {
		this.rate2 = rate2;
	}
	public Double getRate3() {
		return rate3;
	}
	public void setRate3(Double rate3) {
		this.rate3 = rate3;
	}
	public List<Cost> getForeign() {
		return foreign;
	}
	public void setForeign(List<Cost> foreign) {
		this.foreign = foreign;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Double getPurposrAmt() {
		return purposrAmt;
	}
	public void setPurposrAmt(Double purposrAmt) {
		this.purposrAmt = purposrAmt;
	}
	public Skip getSkip() {
		return skip;
	}
	public void setSkip(Skip skip) {
		this.skip = skip;
	}
	public List<JobUserLable> getJobUserLable() {
		return jobUserLable;
	}
	public void setJobUserLable(List<JobUserLable> jobUserLable) {
		this.jobUserLable = jobUserLable;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public JobInfo getJobInfo() {
		return jobInfo;
	}
	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	public SaleInfo getSaleInfo() {
		return saleInfo;
	}
	public void setSaleInfo(SaleInfo saleInfo) {
		this.saleInfo = saleInfo;
	}
	public JobLand getJobObject() {
		return jobObject;
	}
	public void setJobObject(JobLand jobObject) {
		this.jobObject = jobObject;
	}
	public SaleType getSaleObject() {
		return saleObject;
	}
	public void setSaleObject(SaleType saleObject) {
		this.saleObject = saleObject;
	}
	public Cost getCostObject() {
		return costObject;
	}
	public void setCostObject(Cost costObject) {
		this.costObject = costObject;
	}
	public Pay getPayObject() {
		return payObject;
	}
	public void setPayObject(Pay payObject) {
		this.payObject = payObject;
	}
	public Lendtrn getLendObject() {
		return lendObject;
	}
	public void setLendObject(Lendtrn lendObject) {
		this.lendObject = lendObject;
	}
	public Trantrn getTranObject() {
		return tranObject;
	}
	public void setTranObject(Trantrn tranObject) {
		this.tranObject = tranObject;
	}
	public List<JobLableTrn> getJobLableList() {
		return jobLableList;
	}
	public void setJobLableList(List<JobLableTrn> jobLableList) {
		this.jobLableList = jobLableList;
	}
	public List<JobUserLable> getJobUserList() {
		return jobUserList;
	}
	public void setJobUserList(List<JobUserLable> jobUserList) {
		this.jobUserList = jobUserList;
	}
	public List<Cost> getCostList() {
		return costList;
	}
	public void setCostList(List<Cost> costList) {
		this.costList = costList;
	}
	public List<JobLableTrn> getCostLableList() {
		return costLableList;
	}
	public void setCostLableList(List<JobLableTrn> costLableList) {
		this.costLableList = costLableList;
	}
	public List<JobLableTrn> getPayLableList() {
		return payLableList;
	}
	public void setPayLableList(List<JobLableTrn> payLableList) {
		this.payLableList = payLableList;
	}
	public List<JobLableTrn> getLendLableList() {
		return lendLableList;
	}
	public void setLendLableList(List<JobLableTrn> lendLableList) {
		this.lendLableList = lendLableList;
	}
	public List<JobLableTrn> getTranLableList() {
		return tranLableList;
	}
	public void setTranLableList(List<JobLableTrn> tranLableList) {
		this.tranLableList = tranLableList;
	}
	public List<Prooftrn> getPayProoList() {
		return payProoList;
	}
	public void setPayProoList(List<Prooftrn> payProoList) {
		this.payProoList = payProoList;
	}
	public List<Prooftrn> getLendProoList() {
		return lendProoList;
	}
	public void setLendProoList(List<Prooftrn> lendProoList) {
		this.lendProoList = lendProoList;
	}
	
	

}