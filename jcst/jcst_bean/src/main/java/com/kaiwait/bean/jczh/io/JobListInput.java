package com.kaiwait.bean.jczh.io;


import java.util.List;

import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.common.vo.json.server.BaseInputBean;

@SuppressWarnings("serial")
public class JobListInput extends BaseInputBean{
	private JobList JobList;
	private String job_cd;
	private String sale_cd;// 売上種目
	private String cldiv_cd;//     得意先
	private String g_company;//相手先g会社
	private String g_company_name;//相手先g会社名
	private String payer_cd; //請求先 
	private String dlvday; //計上日（予定） from to 
	private String job_name; //  件名 
	private String label_text; //ラベル（标签） 
	private String selfusercd ;	
	private String account_flg;
	private String jobend_flg;
	private String sale_no;
	private String dlvfalg;//计上ステータス
	private String assign_flg;//割当ステータス
	private String invpd ;//請求・発票ステータス的判断条件
	private String cost_finish_flg; // 原価完了ステータス 
	private String rjpd; // 入金ステータス
	private String	itemcd;//初始化时，检索commont表中下拉框数据用
	private String	mstname;//初始化时，检索commont表中下拉框数据用
	private String	itmname;//初始化时，检索commont表中下拉框数据用
	private String	itemname_hk;//初始化时，检索commont表中下拉框数据用
	private String	itemname_en;//初始化时，检索commont表中下拉框数据用
	private String	itemname_jp;//初始化时，检索commont表中下拉框数据用
	private String jspd;//计上状态的判断
	private String cldiv_name;//得意先名
	private String payer_name;//請求先名
	private String ddcd;//担当这id
	private String ddflag;//担当falg
	private String ddname;//担当者名
	private String dlvmon_end;//计上月结束时间
	private String dlvmon_sta;//计上月开始时间
	private String keyword;//模糊查询关键字
	private String  saleadddate;//壳上登录
	private String sale_admit_date;//壳上承认日
	private String jobendmonth;//job的终了月
	private String jobenddate;//job登陆日
	private String all;//job登陆日
	private String dyqx;//得意先扭付權限
	private String ddqx;//担当权限
	private String gdqx;//格挡权限
	private String ddqxcd;//权限中的usercd
	private String sale_cancel_date;
	private String  cost_finish_date;
	private List<JobList> joblistinput;
	private String  del_flg;
	private String searchFlg;
	private String userid;//
	private String timesheet;//
	private String departcd;//
	private String topFlg;
	private String lockflg;
	private String mdcd;
	
	public String getMdcd() {
		return mdcd;
	}
	public void setMdcd(String mdcd) {
		this.mdcd = mdcd;
	}
	public String getLockflg() {
		return lockflg;
	}
	public void setLockflg(String lockflg) {
		this.lockflg = lockflg;
	}
	public String getTopFlg() {
		return topFlg;
	}
	public void setTopFlg(String topFlg) {
		this.topFlg = topFlg;
	}
	public String getDepartcd() {
		return departcd;
	}
	public void setDepartcd(String departcd) {
		this.departcd = departcd;
	}
	public String getTimesheet() {
		return timesheet;
	}
	public void setTimesheet(String timesheet) {
		this.timesheet = timesheet;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSearchFlg() {
		return searchFlg;
	}
	public void setSearchFlg(String searchFlg) {
		this.searchFlg = searchFlg;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public List<JobList> getJoblistinput() {
		return joblistinput;
	}
	public void setJoblistinput(List<JobList> joblistinput) {
		this.joblistinput = joblistinput;
	}
	public String getCost_finish_date() {
		return cost_finish_date;
	}
	public void setCost_finish_date(String cost_finish_date) {
		this.cost_finish_date = cost_finish_date;
	}
	public String getSale_cancel_date() {
		return sale_cancel_date;
	}
	public void setSale_cancel_date(String sale_cancel_date) {
		this.sale_cancel_date = sale_cancel_date;
	}
	public String getDdqxcd() {
		return ddqxcd;
	}
	public void setDdqxcd(String ddqxcd) {
		this.ddqxcd = ddqxcd;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getDyqx() {
		return dyqx;
	}
	public void setDyqx(String dyqx) {
		this.dyqx = dyqx;
	}
	public String getDdqx() {
		return ddqx;
	}
	public void setDdqx(String ddqx) {
		this.ddqx = ddqx;
	}
	public String getGdqx() {
		return gdqx;
	}
	public void setGdqx(String gdqx) {
		this.gdqx = gdqx;
	}
	public JobList getJobList() {
		return JobList;
	}
	public void setJobList(JobList jobList) {
		JobList = jobList;
	}
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getSale_cd() {
		return sale_cd;
	}
	public void setSale_cd(String sale_cd) {
		this.sale_cd = sale_cd;
	}
	public String getCldiv_cd() {
		return cldiv_cd;
	}
	public void setCldiv_cd(String cldiv_cd) {
		this.cldiv_cd = cldiv_cd;
	}
	public String getG_company() {
		return g_company;
	}
	public void setG_company(String g_company) {
		this.g_company = g_company;
	}
	public String getPayer_cd() {
		return payer_cd;
	}
	public void setPayer_cd(String payer_cd) {
		this.payer_cd = payer_cd;
	}
	public String getDlvday() {
		return dlvday;
	}
	public void setDlvday(String dlvday) {
		this.dlvday = dlvday;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getLabel_text() {
		return label_text;
	}
	public void setLabel_text(String label_text) {
		this.label_text = label_text;
	}
	public String getSelfusercd() {
		return selfusercd;
	}
	public void setSelfusercd(String selfusercd) {
		this.selfusercd = selfusercd;
	}
	public String getAccount_flg() {
		return account_flg;
	}
	public void setAccount_flg(String account_flg) {
		this.account_flg = account_flg;
	}
	public String getJobend_flg() {
		return jobend_flg;
	}
	public void setJobend_flg(String jobend_flg) {
		this.jobend_flg = jobend_flg;
	}
	public String getSale_no() {
		return sale_no;
	}
	public void setSale_no(String sale_no) {
		this.sale_no = sale_no;
	}
	public String getAssign_flg() {
		return assign_flg;
	}
	public void setAssign_flg(String assign_flg) {
		this.assign_flg = assign_flg;
	}
	public String getInvpd() {
		return invpd;
	}
	public void setInvpd(String invpd) {
		this.invpd = invpd;
	}
	public String getCost_finish_flg() {
		return cost_finish_flg;
	}
	public void setCost_finish_flg(String cost_finish_flg) {
		this.cost_finish_flg = cost_finish_flg;
	}
	public String getRjpd() {
		return rjpd;
	}
	public void setRjpd(String rjpd) {
		this.rjpd = rjpd;
	}
	public String getItemcd() {
		return itemcd;
	}
	public void setItemcd(String itemcd) {
		this.itemcd = itemcd;
	}
	public String getMstname() {
		return mstname;
	}
	public void setMstname(String mstname) {
		this.mstname = mstname;
	}
	public String getItmname() {
		return itmname;
	}
	public void setItmname(String itmname) {
		this.itmname = itmname;
	}
	public String getItemname_hk() {
		return itemname_hk;
	}
	public void setItemname_hk(String itemname_hk) {
		this.itemname_hk = itemname_hk;
	}
	public String getItemname_en() {
		return itemname_en;
	}
	public void setItemname_en(String itemname_en) {
		this.itemname_en = itemname_en;
	}
	public String getItemname_jp() {
		return itemname_jp;
	}
	public void setItemname_jp(String itemname_jp) {
		this.itemname_jp = itemname_jp;
	}
	public String getJspd() {
		return jspd;
	}
	public void setJspd(String jspd) {
		this.jspd = jspd;
	}
	public String getG_company_name() {
		return g_company_name;
	}
	public void setG_company_name(String g_company_name) {
		this.g_company_name = g_company_name;
	}
	public String getDlvfalg() {
		return dlvfalg;
	}
	public void setDlvfalg(String dlvfalg) {
		this.dlvfalg = dlvfalg;
	}
	public String getCldiv_name() {
		return cldiv_name;
	}
	public void setCldiv_name(String cldiv_name) {
		this.cldiv_name = cldiv_name;
	}
	public String getPayer_name() {
		return payer_name;
	}
	public void setPayer_name(String payer_name) {
		this.payer_name = payer_name;
	}
	public String getDdcd() {
		return ddcd;
	}
	public void setDdcd(String ddcd) {
		this.ddcd = ddcd;
	}
	public String getDdflag() {
		return ddflag;
	}
	public void setDdflag(String ddflag) {
		this.ddflag = ddflag;
	}
	public String getDdname() {
		return ddname;
	}
	public void setDdname(String ddname) {
		this.ddname = ddname;
	}
	public String getDlvmon_end() {
		return dlvmon_end;
	}
	public void setDlvmon_end(String dlvmon_end) {
		this.dlvmon_end = dlvmon_end;
	}
	public String getDlvmon_sta() {
		return dlvmon_sta;
	}
	public void setDlvmon_sta(String dlvmon_sta) {
		this.dlvmon_sta = dlvmon_sta;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSaleadddate() {
		return saleadddate;
	}
	public void setSaleadddate(String saleadddate) {
		this.saleadddate = saleadddate;
	}
	public String getSale_admit_date() {
		return sale_admit_date;
	}
	public void setSale_admit_date(String sale_admit_date) {
		this.sale_admit_date = sale_admit_date;
	}
	public String getJobendmonth() {
		return jobendmonth;
	}
	public void setJobendmonth(String jobendmonth) {
		this.jobendmonth = jobendmonth;
	}
	public String getJobenddate() {
		return jobenddate;
	}
	public void setJobenddate(String jobenddate) {
		this.jobenddate = jobenddate;
	}
	
}
