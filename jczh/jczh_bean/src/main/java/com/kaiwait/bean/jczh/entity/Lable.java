package com.kaiwait.bean.jczh.entity;


public class Lable {
   private String lableid;
   private int lablelevel;
   private String labletext;
   private int companycd;
   private int labletype;
   private String adddate;
   private String upddate;
   private String addusercd;
   private String updusercd;
   private String jobcd;
   private String   cldivcd;
 //权限 all
 	private String all;
 	//扭付得意先
 	private String cldivUser;
 	//担当
 	private String adUser;
 	//割当
 	private String mdUser;
 	private String sysDate;
 	
 	
   
public String getSysDate() {
		return sysDate;
	}
	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}
public String getCldivcd() {
		return cldivcd;
	}
	public void setCldivcd(String cldivcd) {
		this.cldivcd = cldivcd;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getCldivUser() {
		return cldivUser;
	}
	public void setCldivUser(String cldivUser) {
		this.cldivUser = cldivUser;
	}
	public String getAdUser() {
		return adUser;
	}
	public void setAdUser(String adUser) {
		this.adUser = adUser;
	}
	public String getMdUser() {
		return mdUser;
	}
	public void setMdUser(String mdUser) {
		this.mdUser = mdUser;
	}
public String getJobcd() {
	return jobcd;
}
public void setJobcd(String jobcd) {
	this.jobcd = jobcd;
}
public String getLableid() {
	return lableid;
}
public void setLableid(String lableid) {
	this.lableid = lableid;
}
public int getLablelevel() {
	return lablelevel;
}
public void setLablelevel(int lablelevel) {
	this.lablelevel = lablelevel;
}
public String getLabletext() {
	return labletext;
}
public void setLabletext(String labletext) {
	this.labletext = labletext;
}
public int getCompanycd() {
	return companycd;
}
public void setCompanycd(int companycd) {
	this.companycd = companycd;
}
public int getLabletype() {
	return labletype;
}
public void setLabletype(int labletype) {
	this.labletype = labletype;
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
  
}
