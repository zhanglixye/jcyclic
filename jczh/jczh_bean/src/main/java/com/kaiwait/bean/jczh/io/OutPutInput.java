package com.kaiwait.bean.jczh.io;

import java.util.List;

import com.kaiwait.common.vo.json.server.BaseInputBean;

public class OutPutInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;

	private String jobNo;
	private String costNo;
	private String inputNo;
	private String fileName;
	private String langTyp;
	private String startDate;
	private String endDate;
	private String cldivID;
	private String jobDtFlg;//job详细标识
	private List<String> jobList;
	private List<String> costList;
	private boolean moreFlg;//单个还是多个的标识
	
	//名称
	private String nickname;
	//部门ID
	private String departCD;
	//级别
	private String level;
	//删除FLG
	private String delFlg;
	//用户编号，一般用于被操作的用户
	private String userCD;
	private String roleID;
	private String sales;
	private String pdfName;
	private String accountCd;
	private String sheetName;
	
	
	
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getAccountCd() {
		return accountCd;
	}
	public void setAccountCd(String accountCd) {
		this.accountCd = accountCd;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public List<String> getCostList() {
		return costList;
	}
	public void setCostList(List<String> costList) {
		this.costList = costList;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDepartCD() {
		return departCD;
	}
	public void setDepartCD(String departCD) {
		this.departCD = departCD;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getUserCD() {
		return userCD;
	}
	public void setUserCD(String userCD) {
		this.userCD = userCD;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public boolean isMoreFlg() {
		return moreFlg;
	}
	public void setMoreFlg(boolean moreFlg) {
		this.moreFlg = moreFlg;
	}
	public List<String> getJobList() {
		return jobList;
	}
	public void setJobList(List<String> jobList) {
		this.jobList = jobList;
	}
	public String getJobDtFlg() {
		return jobDtFlg;
	}
	public void setJobDtFlg(String jobDtFlg) {
		this.jobDtFlg = jobDtFlg;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCldivID() {
		return cldivID;
	}
	public void setCldivID(String cldivID) {
		this.cldivID = cldivID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getCostNo() {
		return costNo;
	}
	public void setCostNo(String costNo) {
		this.costNo = costNo;
	}
	public String getInputNo() {
		return inputNo;
	}
	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLangTyp() {
		return langTyp;
	}
	public void setLangTyp(String langTyp) {
		this.langTyp = langTyp;
	}

	

}