package com.kaiwait.bean.mst.io;

import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class Company0000001Input extends BaseInputBean{
	private static final long serialVersionUID = 5085368117650161941L;
	private Company company;
	private Integer company_cd;
    private String company_name;
	private String company_full_name;
	private String company_location;
	private Integer company_type;
	private String company_note;
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
	public String getCompany_full_name() {
		return company_full_name;
	}
	public void setCompany_full_name(String company_full_name) {
		this.company_full_name = company_full_name;
	}
	public String getCompany_location() {
		return company_location;
	}
	public void setCompany_location(String company_location) {
		this.company_location = company_location;
	}
	public Integer getCompany_type() {
		return company_type;
	}
	public void setCompany_type(Integer company_type) {
		this.company_type = company_type;
	}
	public String getCompany_note() {
		return company_note;
	}
	public void setCompany_note(String company_note) {
		this.company_note = company_note;
	}
	public Integer getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(Integer del_flg) {
		this.del_flg = del_flg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
