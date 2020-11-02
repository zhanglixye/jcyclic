package com.kaiwait.bean.mst.io;

import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class CompanyMstInput extends BaseInputBean{
	private static final long serialVersionUID = 5085368117650161941L;
	private Company company;
	//会社id
	private Integer company_cd;
	//会社名称
	private String company_name;
	//会社英文名
	private String company_name_en;
	//会社全称
	private String company_full_name;
	//通用语
	private String common_language;
	//工作时区
	private String time_zone;
	//通货编号
	private String currency_code;
	//0：博報堂、1：博报堂系以外、2：管理用
	private Integer company_type;
	//编号规则 0：月1：年
	private Integer number_rules;
	//开始月
	private Integer start_month;
	//
	private Integer md_skip;
	//
	private Integer cost_skip;
	//
	private Integer pay_skip;
	//
	private Integer confirm_skip;
	//
	private Integer apply_skip;
	//
	private Integer req_skip;
	//
	private Integer invoice_apply_skip;
	//
	private Integer invoice_skip;
	//请款预定日 自动设定0：不自动；1：全自动
	private Integer registration_automatic;
	//自动月
	private String  auto_month;
	//动自日
	private String  auto_day;
	//公司名称
	private String  req_company_name;
	//公司名称-英语
	private String  req_company_name_en;
	//编邮
	private String  zip_code;
	//编邮英语
	private String  zip_code_en;
	//地址一
	private String  location;
	//地址一英语
	private String  location_en;
	//址地二
	private String  location1;
	//址地二英语
	private String  location1_en;
	//行银信息
	private String  bank_info;
	//行银信息英语
	private String  bank_info_en;
	//注书发公司名称
	private String  order_company_name;
	//注书发公司名称英语
	private String  order_company_name_en;
	//注书发公司编邮
	private String  order_zip_code;
	//注书发公司编邮英语
	private String  order_zip_code_en;
	//注书发公司址地一
	private String  order_location;
	//注书发公司址地一英语
	private String  order_location_en;
	//注书发公司址地二
	private String  order_location1;
	//注书发公司址地二英语
	private String  order_location1_en;
	//标识0：正常、1：削除 
	private Integer del_flg;
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Integer getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(Integer company_cd) {
		this.company_cd = company_cd;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_name_en() {
		return company_name_en;
	}
	public void setCompany_name_en(String company_name_en) {
		this.company_name_en = company_name_en;
	}
	public String getCompany_full_name() {
		return company_full_name;
	}
	public void setCompany_full_name(String company_full_name) {
		this.company_full_name = company_full_name;
	}
	public String getCommon_language() {
		return common_language;
	}
	public void setCommon_language(String common_language) {
		this.common_language = common_language;
	}
	public String getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public Integer getCompany_type() {
		return company_type;
	}
	public void setCompany_type(Integer company_type) {
		this.company_type = company_type;
	}
	public Integer getNumber_rules() {
		return number_rules;
	}
	public void setNumber_rules(Integer number_rules) {
		this.number_rules = number_rules;
	}
	public Integer getStart_month() {
		return start_month;
	}
	public void setStart_month(Integer start_month) {
		this.start_month = start_month;
	}
	public Integer getMd_skip() {
		return md_skip;
	}
	public void setMd_skip(Integer md_skip) {
		this.md_skip = md_skip;
	}
	public Integer getCost_skip() {
		return cost_skip;
	}
	public void setCost_skip(Integer cost_skip) {
		this.cost_skip = cost_skip;
	}
	public Integer getPay_skip() {
		return pay_skip;
	}
	public void setPay_skip(Integer pay_skip) {
		this.pay_skip = pay_skip;
	}
	public Integer getConfirm_skip() {
		return confirm_skip;
	}
	public void setConfirm_skip(Integer confirm_skip) {
		this.confirm_skip = confirm_skip;
	}
	public Integer getApply_skip() {
		return apply_skip;
	}
	public void setApply_skip(Integer apply_skip) {
		this.apply_skip = apply_skip;
	}
	public Integer getReq_skip() {
		return req_skip;
	}
	public void setReq_skip(Integer req_skip) {
		this.req_skip = req_skip;
	}
	public Integer getInvoice_apply_skip() {
		return invoice_apply_skip;
	}
	public void setInvoice_apply_skip(Integer invoice_apply_skip) {
		this.invoice_apply_skip = invoice_apply_skip;
	}
	public Integer getInvoice_skip() {
		return invoice_skip;
	}
	public void setInvoice_skip(Integer invoice_skip) {
		this.invoice_skip = invoice_skip;
	}
	public Integer getRegistration_automatic() {
		return registration_automatic;
	}
	public void setRegistration_automatic(Integer registration_automatic) {
		this.registration_automatic = registration_automatic;
	}
	public String getAuto_month() {
		return auto_month;
	}
	public void setAuto_month(String auto_month) {
		this.auto_month = auto_month;
	}
	public String getAuto_day() {
		return auto_day;
	}
	public void setAuto_day(String auto_day) {
		this.auto_day = auto_day;
	}
	public String getReq_company_name() {
		return req_company_name;
	}
	public void setReq_company_name(String req_company_name) {
		this.req_company_name = req_company_name;
	}
	public String getReq_company_name_en() {
		return req_company_name_en;
	}
	public void setReq_company_name_en(String req_company_name_en) {
		this.req_company_name_en = req_company_name_en;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getZip_code_en() {
		return zip_code_en;
	}
	public void setZip_code_en(String zip_code_en) {
		this.zip_code_en = zip_code_en;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation_en() {
		return location_en;
	}
	public void setLocation_en(String location_en) {
		this.location_en = location_en;
	}
	public String getLocation1() {
		return location1;
	}
	public void setLocation1(String location1) {
		this.location1 = location1;
	}
	public String getLocation1_en() {
		return location1_en;
	}
	public void setLocation1_en(String location1_en) {
		this.location1_en = location1_en;
	}
	public String getBank_info() {
		return bank_info;
	}
	public void setBank_info(String bank_info) {
		this.bank_info = bank_info;
	}
	public String getBank_info_en() {
		return bank_info_en;
	}
	public void setBank_info_en(String bank_info_en) {
		this.bank_info_en = bank_info_en;
	}
	public String getOrder_company_name() {
		return order_company_name;
	}
	public void setOrder_company_name(String order_company_name) {
		this.order_company_name = order_company_name;
	}
	public String getOrder_company_name_en() {
		return order_company_name_en;
	}
	public void setOrder_company_name_en(String order_company_name_en) {
		this.order_company_name_en = order_company_name_en;
	}
	public String getOrder_zip_code() {
		return order_zip_code;
	}
	public void setOrder_zip_code(String order_zip_code) {
		this.order_zip_code = order_zip_code;
	}
	public String getOrder_zip_code_en() {
		return order_zip_code_en;
	}
	public void setOrder_zip_code_en(String order_zip_code_en) {
		this.order_zip_code_en = order_zip_code_en;
	}
	public String getOrder_location() {
		return order_location;
	}
	public void setOrder_location(String order_location) {
		this.order_location = order_location;
	}
	public String getOrder_location_en() {
		return order_location_en;
	}
	public void setOrder_location_en(String order_location_en) {
		this.order_location_en = order_location_en;
	}
	public String getOrder_location1() {
		return order_location1;
	}
	public void setOrder_location1(String order_location1) {
		this.order_location1 = order_location1;
	}
	public String getOrder_location1_en() {
		return order_location1_en;
	}
	public void setOrder_location1_en(String order_location1_en) {
		this.order_location1_en = order_location1_en;
	}
	public Integer getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(Integer del_flg) {
		this.del_flg = del_flg;
	}
	
}
