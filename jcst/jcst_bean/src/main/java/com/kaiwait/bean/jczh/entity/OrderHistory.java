package com.kaiwait.bean.jczh.entity;

public class OrderHistory{
	private String jobNo;
	private String orderNo;
	private String ordertyp;
	private String orderStatus;
	private String checkMonth;
	private String payAmt;
	private String costAmt;
	private String costVatAmt;
	private String costStatus;
	
	public String getCostStatus() {
		return costStatus;
	}
	public void setCostStatus(String costStatus) {
		this.costStatus = costStatus;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrdertyp() {
		return ordertyp;
	}
	public void setOrdertyp(String ordertyp) {
		this.ordertyp = ordertyp;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCheckMonth() {
		return checkMonth;
	}
	public void setCheckMonth(String checkMonth) {
		this.checkMonth = checkMonth;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getCostAmt() {
		return costAmt;
	}
	public void setCostAmt(String costAmt) {
		this.costAmt = costAmt;
	}
	public String getCostVatAmt() {
		return costVatAmt;
	}
	public void setCostVatAmt(String costVatAmt) {
		this.costVatAmt = costVatAmt;
	}
	
	
}