package com.kaiwait.bean.jczh.entity;

public class RecTrn {

	 private String job_cd;
	 private String rec_cd;
	 private String rec_date;
	 private String rec_amt;
	 private String adddate;
	 private String update;
	 private String addusercd;
	 private String upusercd;
	 private String del_flg;
	 private String company_cd;
	 private String recremark;
	 private int recStatus;
	 private int recLockFlg;
	 
	public int getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(int recStatus) {
		this.recStatus = recStatus;
	}
	public int getRecLockFlg() {
		return recLockFlg;
	}
	public void setRecLockFlg(int recLockFlg) {
		this.recLockFlg = recLockFlg;
	}
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getRec_cd() {
		return rec_cd;
	}
	public void setRec_cd(String rec_cd) {
		this.rec_cd = rec_cd;
	}
	public String getRec_date() {
		return rec_date;
	}
	public void setRec_date(String rec_date) {
		this.rec_date = rec_date;
	}
	public String getRec_amt() {
		return rec_amt;
	}
	public void setRec_amt(String rec_amt) {
		this.rec_amt = rec_amt;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getAddusercd() {
		return addusercd;
	}
	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}
	public String getUpusercd() {
		return upusercd;
	}
	public void setUpusercd(String upusercd) {
		this.upusercd = upusercd;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	public String getRecremark() {
		return recremark;
	}
	public void setRecremark(String recremark) {
		this.recremark = recremark;
	}

}
