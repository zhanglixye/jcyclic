package com.kaiwait.bean.jczh.entity;

public class PayDeal {
	//支付处理
	private String   payremark;//支付备注
	private int   payflg;//支付状态
	private int   paycanceldateflg;//支付处理取消日期b标识
	private String   paycanceldate;//支付处理取消日期
	private String   paydate;//支付处理 日
	private int companycd;
	private String inputno;
	private String upDateDate;
	private String upUsercd;
	private int paylockflg;
	
	
	public int getPaylockflg() {
		return paylockflg;
	}
	public void setPaylockflg(int paylockflg) {
		this.paylockflg = paylockflg;
	}
	public String getUpDateDate() {
		return upDateDate;
	}
	public void setUpDateDate(String upDateDate) {
		this.upDateDate = upDateDate;
	}
	public String getUpUsercd() {
		return upUsercd;
	}
	public void setUpUsercd(String upUsercd) {
		this.upUsercd = upUsercd;
	}
	public String getInputno() {
		return inputno;
	}
	public void setInputno(String inputno) {
		this.inputno = inputno;
	}
	public int getCompanycd() {
		return companycd;
	}
	public void setCompanycd(int companycd) {
		this.companycd = companycd;
	}
	public String getPayremark() {
		return payremark;
	}
	public void setPayremark(String payremark) {
		this.payremark = payremark;
	}
	public int getPayflg() {
		return payflg;
	}
	public void setPayflg(int payflg) {
		this.payflg = payflg;
	}
	public int getPaycanceldateflg() {
		return paycanceldateflg;
	}
	public void setPaycanceldateflg(int paycanceldateflg) {
		this.paycanceldateflg = paycanceldateflg;
	}
	public String getPaycanceldate() {
		return paycanceldate;
	}
	public void setPaycanceldate(String paycanceldate) {
		this.paycanceldate = paycanceldate;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
}
