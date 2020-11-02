package com.kaiwait.bean.jczh.io;

import java.util.Date;
import java.util.List;

import com.kaiwait.common.vo.json.server.BaseInputBean;

public class RecTrnInput extends BaseInputBean{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String job_cd;
	 private String rec_cd;
	 private Date rec_date;
	 private String rec_amt;
	 private String adddate;
	 private String update;
	 private String addusercd;
	 private String upusercd;
	 private String del_flg;
	 private String company_cd;
	 private String recremark;
	 private String deleteRecdate;
	 private String rec_status;
	 private String udpflg;
	 private String Sale_no;
	 private int lockflg;
	 private int reclockflg;
	 
	 
	public int getReclockflg() {
		return reclockflg;
	}
	public void setReclockflg(int reclockflg) {
		this.reclockflg = reclockflg;
	}
	public String getSale_no() {
		return Sale_no;
	}
	public void setSale_no(String sale_no) {
		Sale_no = sale_no;
	}
	public int getLockflg() {
		return lockflg;
	}
	public void setLockflg(int lockflg) {
		this.lockflg = lockflg;
	}
	public String getUdpflg() {
		return udpflg;
	}
	public void setUdpflg(String udpflg) {
		this.udpflg = udpflg;
	}
	private List<RecTrnInput> recList;
	 
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
	public Date getRec_date() {
		return rec_date;
	}
	public void setRec_date(Date rec_date) {
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
	public List<RecTrnInput> getRecList() {
		return recList;
	}
	public void setRecList(List<RecTrnInput> recList) {
		this.recList = recList;
	}
	public String getDeleteRecdate() {
		return deleteRecdate;
	}
	public void setDeleteRecdate(String deleteRecdate) {
		this.deleteRecdate = deleteRecdate;
	}
	public String getRec_status() {
		return rec_status;
	}
	public void setRec_status(String rec_status) {
		this.rec_status = rec_status;
	}
	 
}
