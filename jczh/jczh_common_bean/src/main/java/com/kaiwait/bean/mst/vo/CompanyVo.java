package com.kaiwait.bean.mst.vo;

import java.util.List;


public class CompanyVo {
	private String companyType;
	//会社id
	private Integer company_cd;
	//会社名称
	private String company_name;
	//会社英文名
	private String company_name_en;
	//会社全称
	private String company_full_name;
	//0：博報堂、1：博报堂系以外、2：管理用
	private Integer company_type;
	//编号规则 0：月1：年
	private Integer number_rules;
	//中文简体
	private String  itemnamezc;
	//中文繁体
	private String  itemnamezt;
	//英语
	private String  itemnameen;
	//日益
	private String  itemnamejp;
	//通货语言
	//中文简体
	private String  itemnamecdzc;
	//中文繁体
	private String  itemnamecdzt;
	//英语
	private String  itemnamecden;
	//日益
	private String  itemnamecdjp;
	// 时区
	//中文简体
	private String  itemnametzzc;
	//中文繁体
	private String  itemnametzzt;
	//英语
	private String   itemnametzen;
	//日益
	private String   itemnametzjp;
	//共通id
	private String  mstcd;
	//语言下拉
	private List<CommonmstVo> cl_list;
			//通货下拉
	private List<CommonmstVo> cc_list;
			//时区下拉
	private List<CommonmstVo> tz_list;
	
	
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyTyppe(String companyType) {
		this.companyType = companyType;
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
	public String getItemnamezc() {
		return itemnamezc;
	}
	public void setItemnamezc(String itemnamezc) {
		this.itemnamezc = itemnamezc;
	}
	public String getItemnamezt() {
		return itemnamezt;
	}
	public void setItemnamezt(String itemnamezt) {
		this.itemnamezt = itemnamezt;
	}
	public String getItemnameen() {
		return itemnameen;
	}
	public void setItemnameen(String itemnameen) {
		this.itemnameen = itemnameen;
	}
	public String getItemnamejp() {
		return itemnamejp;
	}
	public void setItemnamejp(String itemnamejp) {
		this.itemnamejp = itemnamejp;
	}
	public String getItemnamecdzc() {
		return itemnamecdzc;
	}
	public void setItemnamecdzc(String itemnamecdzc) {
		this.itemnamecdzc = itemnamecdzc;
	}
	public String getItemnamecdzt() {
		return itemnamecdzt;
	}
	public void setItemnamecdzt(String itemnamecdzt) {
		this.itemnamecdzt = itemnamecdzt;
	}
	public String getItemnamecden() {
		return itemnamecden;
	}
	public void setItemnamecden(String itemnamecden) {
		this.itemnamecden = itemnamecden;
	}
	public String getItemnamecdjp() {
		return itemnamecdjp;
	}
	public void setItemnamecdjp(String itemnamecdjp) {
		this.itemnamecdjp = itemnamecdjp;
	}
	public String getItemnametzzc() {
		return itemnametzzc;
	}
	public void setItemnametzzc(String itemnametzzc) {
		this.itemnametzzc = itemnametzzc;
	}
	public String getItemnametzzt() {
		return itemnametzzt;
	}
	public void setItemnametzzt(String itemnametzzt) {
		this.itemnametzzt = itemnametzzt;
	}
	public String getItemnametzen() {
		return itemnametzen;
	}
	public void setItemnametzen(String itemnametzen) {
		this.itemnametzen = itemnametzen;
	}
	public String getItemnametzjp() {
		return itemnametzjp;
	}
	public void setItemnametzjp(String itemnametzjp) {
		this.itemnametzjp = itemnametzjp;
	}
	public String getMstcd() {
		return mstcd;
	}
	public void setMstcd(String mstcd) {
		this.mstcd = mstcd;
	}
	public List<CommonmstVo> getCl_list() {
		return cl_list;
	}
	public void setCl_list(List<CommonmstVo> cl_list) {
		this.cl_list = cl_list;
	}
	public List<CommonmstVo> getCc_list() {
		return cc_list;
	}
	public void setCc_list(List<CommonmstVo> cc_list) {
		this.cc_list = cc_list;
	}
	public List<CommonmstVo> getTz_list() {
		return tz_list;
	}
	public void setTz_list(List<CommonmstVo> tz_list) {
		this.tz_list = tz_list;
	}
}