package com.kaiwait.bean.jczh.entity;


public class OrderHistorytrn {
	
    private String jobNo;
    
    private String orderNo;

    private String checkMonth;

    private Double payAmt;

    private Double costAmt;

    private Double costVat;

    private String orderStatus;

    private String orderType;
    
    private String companycd;
	private String costStatus;
	private String addusercd;
	
	private String updusercd;
	
	private String adddate;
	
	private String upddate;
	
	
	public String getAddusercd() {
		return addusercd;
	}
	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}
	public String getUpdusercd() {
		return updusercd;
	}
	public void setUpdusercd(String updusercd) {
		this.updusercd = updusercd;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
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

	public String getCheckMonth() {
		return checkMonth;
	}

	public void setCheckMonth(String checkMonth) {
		this.checkMonth = checkMonth;
	}

	public Double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}

	public Double getCostAmt() {
		return costAmt;
	}

	public void setCostAmt(Double costAmt) {
		this.costAmt = costAmt;
	}

	public Double getCostVat() {
		return costVat;
	}

	public void setCostVat(Double costVat) {
		this.costVat = costVat;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCompanycd() {
		return companycd;
	}

	public void setCompanycd(String companycd) {
		this.companycd = companycd;
	}

}
