package com.kaiwait.bean.mst.entity;

/**
 * 承包方税率表
 */
public class Payeetaxmst {
	public Payeetaxmst() {
		
	}
	
	//税者纳种类
	private String user_tax_type;
	//发票种类
	private String invoice_type;
	//票发内容
	private String invoice_text;
	
	//税者纳种类
	private String user_tax_type_hk;
	//发票种类
	private String invoice_type_hk;
	//票发内容
	private String invoice_text_hk;
	
	//税者纳种类
	private String user_tax_type_jp;
	//发票种类
	private String invoice_type_jp;
	//票发内容
	private String invoice_text_jp;
	
	//税者纳种类
	private String user_tax_type_en;
	//发票种类
	private String invoice_type_en;
	//票发内容
	private String invoice_text_en;
	//值税率增
	private String vat_rate;
	//0：有效；1：无效
	private String del_flg;
	//0:不扣除；1：扣除
	private String deduction;

	//税者纳种类1
	private String user_tax_type1;
	//发票种类1
	private String invoice_type1;
	//票发内容1
	private String invoice_text1;
	
	private String company_cd;
	
	//税者纳种类
	private String user_tax_type_code;
	//发票种类
	private String invoice_type_code;
	//票发内容
	private String invoice_text_code;
    //
	private int companycd;
	
	private String id;
	
	private int lock_flg;
	
	private String addDate;
	private String updDate;
	private String addUsercd;
	private String upUsercd;
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
	
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getAddUsercd() {
		return addUsercd;
	}
	public void setAddUsercd(String addUsercd) {
		this.addUsercd = addUsercd;
	}
	public String getUpUsercd() {
		return upUsercd;
	}
	public void setUpUsercd(String upUsercd) {
		this.upUsercd = upUsercd;
	}
	public int getLock_flg() {
		return lock_flg;
	}
	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
	}
	public int getCompanycd() {
		return companycd;
	}
	public void setCompanycd(int companycd) {
		this.companycd = companycd;
	}
	public String getUser_tax_type() {
		return user_tax_type;
	}
	public void setUser_tax_type(String user_tax_type) {
		this.user_tax_type = user_tax_type;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_text() {
		return invoice_text;
	}
	public void setInvoice_text(String invoice_text) {
		this.invoice_text = invoice_text;
	}
	public String getVat_rate() {
		return vat_rate;
	}
	public void setVat_rate(String vat_rate) {
		this.vat_rate = vat_rate;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}

	public String getUser_tax_type1() {
		return user_tax_type1;
	}
	public void setUser_tax_type1(String user_tax_type1) {
		this.user_tax_type1 = user_tax_type1;
	}
	public String getInvoice_type1() {
		return invoice_type1;
	}
	public void setInvoice_type1(String invoice_type1) {
		this.invoice_type1 = invoice_type1;
	}
	public String getInvoice_text1() {
		return invoice_text1;
	}
	public void setInvoice_text1(String invoice_text1) {
		this.invoice_text1 = invoice_text1;
	}
	public String getDeduction() {
		return deduction;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	public String getUser_tax_type_hk() {
		return user_tax_type_hk;
	}
	public void setUser_tax_type_hk(String user_tax_type_hk) {
		this.user_tax_type_hk = user_tax_type_hk;
	}
	public String getInvoice_type_hk() {
		return invoice_type_hk;
	}
	public void setInvoice_type_hk(String invoice_type_hk) {
		this.invoice_type_hk = invoice_type_hk;
	}
	public String getInvoice_text_hk() {
		return invoice_text_hk;
	}
	public void setInvoice_text_hk(String invoice_text_hk) {
		this.invoice_text_hk = invoice_text_hk;
	}
	public String getUser_tax_type_jp() {
		return user_tax_type_jp;
	}
	public void setUser_tax_type_jp(String user_tax_type_jp) {
		this.user_tax_type_jp = user_tax_type_jp;
	}
	public String getInvoice_type_jp() {
		return invoice_type_jp;
	}
	public void setInvoice_type_jp(String invoice_type_jp) {
		this.invoice_type_jp = invoice_type_jp;
	}
	public String getInvoice_text_jp() {
		return invoice_text_jp;
	}
	public void setInvoice_text_jp(String invoice_text_jp) {
		this.invoice_text_jp = invoice_text_jp;
	}
	public String getUser_tax_type_en() {
		return user_tax_type_en;
	}
	public void setUser_tax_type_en(String user_tax_type_en) {
		this.user_tax_type_en = user_tax_type_en;
	}
	public String getInvoice_type_en() {
		return invoice_type_en;
	}
	public void setInvoice_type_en(String invoice_type_en) {
		this.invoice_type_en = invoice_type_en;
	}
	public String getInvoice_text_en() {
		return invoice_text_en;
	}
	public void setInvoice_text_en(String invoice_text_en) {
		this.invoice_text_en = invoice_text_en;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	public String getUser_tax_type_code() {
		return user_tax_type_code;
	}
	public void setUser_tax_type_code(String user_tax_type_code) {
		this.user_tax_type_code = user_tax_type_code;
	}
	public String getInvoice_type_code() {
		return invoice_type_code;
	}
	public void setInvoice_type_code(String invoice_type_code) {
		this.invoice_type_code = invoice_type_code;
	}
	public String getInvoice_text_code() {
		return invoice_text_code;
	}
	public void setInvoice_text_code(String invoice_text_code) {
		this.invoice_text_code = invoice_text_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}