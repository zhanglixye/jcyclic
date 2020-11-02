package com.kaiwait.bean.jczh.io;

import java.util.List;

import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.TimesheetUser;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class TimesheetInput extends BaseInputBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usercd;

	private String jobcd;

	private String jsmonth;

	private String today;
	
	private String companycd;
	
	private String daynum;
	
	private Double timenum;
	
	private String user_cd;
	
	private String adddate;
	
	private String addusercd;
	
	private String update;
	
	private String upusercd;
	
	private String company_cd;
	
	List<TimesheetInput> insertlist;
	
	List<JobList> joblist;
	
	List<TimesheetUser> timesheetuser;

	
	public List<TimesheetUser> getTimesheetuser() {
		return timesheetuser;
	}

	public void setTimesheetuser(List<TimesheetUser> timesheetuser) {
		this.timesheetuser = timesheetuser;
	}

	public String getUsercd() {
		return usercd;
	}

	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}

	public String getJobcd() {
		return jobcd;
	}

	public void setJobcd(String jobcd) {
		this.jobcd = jobcd;
	}

	public String getJsmonth() {
		return jsmonth;
	}

	public void setJsmonth(String jsmonth) {
		this.jsmonth = jsmonth;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getCompanycd() {
		return companycd;
	}

	public void setCompanycd(String companycd) {
		this.companycd = companycd;
	}


	public List<TimesheetInput> getInsertlist() {
		return insertlist;
	}

	public void setInsertlist(List<TimesheetInput> insertlist) {
		this.insertlist = insertlist;
	}

	public String getDaynum() {
		return daynum;
	}

	public void setDaynum(String daynum) {
		this.daynum = daynum;
	}

	public Double getTimenum() {
		return timenum;
	}

	public void setTimenum(Double timenum) {
		this.timenum = timenum;
	}

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getAddusercd() {
		return addusercd;
	}

	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getUpusercd() {
		return upusercd;
	}

	public void setUpusercd(String upusercd) {
		this.upusercd = upusercd;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public List<JobList> getJoblist() {
		return joblist;
	}

	public void setJoblist(List<JobList> joblist) {
		this.joblist = joblist;
	}
	

}