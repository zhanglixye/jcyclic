package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class Timesheettrn {
	
    private String job_cd;

    private String input_no;

    private String company_cd;

    private String tran_date;

    private String item_code;

    private String tran_amt;

    private String tran_name;

    private String remark;

    private String add_date;

    private String up_date;

    private String add_usercd;

    private String up_usercd;

    private String tran_status;
    
    private String itmname;
    
    private String itemname_hk;
    
    private String itemname_en;
    
    private String itemname_jp;
    
    private List<Lable> Trantrnlabellist;

	public String getJob_cd() {
		return job_cd;
	}

	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}

	public String getInput_no() {
		return input_no;
	}

	public void setInput_no(String input_no) {
		this.input_no = input_no;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getTran_date() {
		return tran_date;
	}

	public void setTran_date(String tran_date) {
		this.tran_date = tran_date;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getTran_amt() {
		return tran_amt;
	}

	public void setTran_amt(String tran_amt) {
		this.tran_amt = tran_amt;
	}

	public String getTran_name() {
		return tran_name;
	}

	public void setTran_name(String tran_name) {
		this.tran_name = tran_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getUp_date() {
		return up_date;
	}

	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}

	public String getAdd_usercd() {
		return add_usercd;
	}

	public void setAdd_usercd(String add_usercd) {
		this.add_usercd = add_usercd;
	}

	public String getUp_usercd() {
		return up_usercd;
	}

	public void setUp_usercd(String up_usercd) {
		this.up_usercd = up_usercd;
	}

	public String getTran_status() {
		return tran_status;
	}

	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
	}

	public String getItmname() {
		return itmname;
	}

	public void setItmname(String itmname) {
		this.itmname = itmname;
	}

	public String getItemname_hk() {
		return itemname_hk;
	}

	public void setItemname_hk(String itemname_hk) {
		this.itemname_hk = itemname_hk;
	}

	public String getItemname_en() {
		return itemname_en;
	}

	public void setItemname_en(String itemname_en) {
		this.itemname_en = itemname_en;
	}

	public String getItemname_jp() {
		return itemname_jp;
	}

	public void setItemname_jp(String itemname_jp) {
		this.itemname_jp = itemname_jp;
	}

	public List<Lable> getTrantrnlabellist() {
		return Trantrnlabellist;
	}

	public void setTrantrnlabellist(List<Lable> trantrnlabellist) {
		Trantrnlabellist = trantrnlabellist;
	}
    

}
