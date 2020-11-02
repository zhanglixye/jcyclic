package com.kaiwait.bean.jczh.entity;

/**
 * 
* @ClassName: Msgtrn  
* @Description: TODO(留言板实体类)  
* @author fqq  
* @date 2018年6月25日  
*
 */
public class LabelMst {
	

	   private String label_id;
	   
	   private String label_level;
	   
	   private String label_text;
	   
	   private Integer company_cd;
	   
	   private String adddate;
	   
	   private String upddate;
	   
	   private String addusercd;
	   
	   private String updusercd;
	   
	   private String label_type;	
	   
	   public String getLabel_id() {
		return label_id;
	}

	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}

	public String getLabel_level() {
		return label_level;
	}

	public void setLabel_level(String label_level) {
		this.label_level = label_level;
	}

	public String getLabel_text() {
		return label_text;
	}

	public void setLabel_text(String label_text) {
		this.label_text = label_text;
	}

	public Integer getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(Integer company_cd) {
		this.company_cd = company_cd;
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

	public String getLabel_type() {
		return label_type;
	}

	public void setLabel_type(String label_type) {
		this.label_type = label_type;
	}

	public String getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}

	private String del_flg;
}