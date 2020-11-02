package com.kaiwait.bean.jczh.vo;


/**
 * 
* @ClassName: JobListVo  
* @Description: TODO(Job一览  
* )  
* @author mayouyi  
* @date 2018年月2日14 
*
 */
public class JobListVo{

	private String job_cd;// jobno
	private String cldiv;//得意先 
	private String cldiv_en;
	private String cldiv_full;
	private String g_company;	//相手先g会社 
	private String payer;	// 请求先 
	private String payer_en;
	private String payer_full;	
	private String job_name; // 件名 
	private String sale_name;//売上種目 
	private String dlvday;	//计上日 
	private String sale_amt;// 壳上 
	private String vat_amt;	 // 壳上增值税 
	private String req_amt;	 // 请求金额 
	private String plan_cost_amt;	 // 予定原価 
	private String account_flg;//ACCOUNT_FLG表示洗壳终了，1表示终了 机上状态是 ‘计上计’  JOBEND_FLG表示job终了状态，1是‘终了’.SALE_NO有是‘计’。没有是‘未’
	private String jobend_flg;
	private String sale_no;//job_编号
	private String assign_flg;// 担当割当状态 
	private String req_flg; // 请求状态
	private String rec_flg;//入金状态 
	private String inv_flg; // 发票状态 
	private String cost_finish_flg;//原価完了状态 
	private String cldiv_cd;//得以先编号 
	private String zrusername;//责任者 
	private String zrusername_en;
	private String ddusername;//担当者 
	private String ddusername_en;
	private String plan_sale;// 壳上予定额 
	private String jobadduser;//job登录者 
	private String jobadduser_en;
	private String adddate; // job登录日 
	private String seladduser;//壳上登录者 
	private String seladduser_en;
	private String adminuser;//壳上承认者 
	private String adminuser_en;
	private String sale_admit_date; // 壳上承认日 
	private String dlvmon;//计上月 
	private String remark;	 // 备考 
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getCldiv() {
		return cldiv;
	}
	public void setCldiv(String cldiv) {
		this.cldiv = cldiv;
	}
	public String getCldiv_en() {
		return cldiv_en;
	}
	public void setCldiv_en(String cldiv_en) {
		this.cldiv_en = cldiv_en;
	}
	public String getCldiv_full() {
		return cldiv_full;
	}
	public void setCldiv_full(String cldiv_full) {
		this.cldiv_full = cldiv_full;
	}
	public String getG_company() {
		return g_company;
	}
	public void setG_company(String g_company) {
		this.g_company = g_company;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayer_en() {
		return payer_en;
	}
	public void setPayer_en(String payer_en) {
		this.payer_en = payer_en;
	}
	public String getPayer_full() {
		return payer_full;
	}
	public void setPayer_full(String payer_full) {
		this.payer_full = payer_full;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getSale_name() {
		return sale_name;
	}
	public void setSale_name(String sale_name) {
		this.sale_name = sale_name;
	}
	public String getDlvday() {
		return dlvday;
	}
	public void setDlvday(String dlvday) {
		this.dlvday = dlvday;
	}
	public String getSale_amt() {
		return sale_amt;
	}
	public void setSale_amt(String sale_amt) {
		this.sale_amt = sale_amt;
	}
	public String getVat_amt() {
		return vat_amt;
	}
	public void setVat_amt(String vat_amt) {
		this.vat_amt = vat_amt;
	}
	public String getReq_amt() {
		return req_amt;
	}
	public void setReq_amt(String req_amt) {
		this.req_amt = req_amt;
	}
	public String getPlan_cost_amt() {
		return plan_cost_amt;
	}
	public void setPlan_cost_amt(String plan_cost_amt) {
		this.plan_cost_amt = plan_cost_amt;
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
	public String getReq_flg() {
		return req_flg;
	}
	public void setReq_flg(String req_flg) {
		this.req_flg = req_flg;
	}
	public String getRec_flg() {
		return rec_flg;
	}
	public void setRec_flg(String rec_flg) {
		this.rec_flg = rec_flg;
	}
	public String getInv_flg() {
		return inv_flg;
	}
	public void setInv_flg(String inv_flg) {
		this.inv_flg = inv_flg;
	}
	public String getCost_finish_flg() {
		return cost_finish_flg;
	}
	public void setCost_finish_flg(String cost_finish_flg) {
		this.cost_finish_flg = cost_finish_flg;
	}
	public String getCldiv_cd() {
		return cldiv_cd;
	}
	public void setCldiv_cd(String cldiv_cd) {
		this.cldiv_cd = cldiv_cd;
	}
	public String getZrusername() {
		return zrusername;
	}
	public void setZrusername(String zrusername) {
		this.zrusername = zrusername;
	}
	public String getZrusername_en() {
		return zrusername_en;
	}
	public void setZrusername_en(String zrusername_en) {
		this.zrusername_en = zrusername_en;
	}
	public String getDdusername() {
		return ddusername;
	}
	public void setDdusername(String ddusername) {
		this.ddusername = ddusername;
	}
	public String getDdusername_en() {
		return ddusername_en;
	}
	public void setDdusername_en(String ddusername_en) {
		this.ddusername_en = ddusername_en;
	}
	public String getPlan_sale() {
		return plan_sale;
	}
	public void setPlan_sale(String plan_sale) {
		this.plan_sale = plan_sale;
	}
	public String getJobadduser() {
		return jobadduser;
	}
	public void setJobadduser(String jobadduser) {
		this.jobadduser = jobadduser;
	}
	public String getJobadduser_en() {
		return jobadduser_en;
	}
	public void setJobadduser_en(String jobadduser_en) {
		this.jobadduser_en = jobadduser_en;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getSeladduser() {
		return seladduser;
	}
	public void setSeladduser(String seladduser) {
		this.seladduser = seladduser;
	}
	public String getSeladduser_en() {
		return seladduser_en;
	}
	public void setSeladduser_en(String seladduser_en) {
		this.seladduser_en = seladduser_en;
	}
	public String getAdminuser() {
		return adminuser;
	}
	public void setAdminuser(String adminuser) {
		this.adminuser = adminuser;
	}
	public String getAdminuser_en() {
		return adminuser_en;
	}
	public void setAdminuser_en(String adminuser_en) {
		this.adminuser_en = adminuser_en;
	}
	public String getSale_admit_date() {
		return sale_admit_date;
	}
	public void setSale_admit_date(String sale_admit_date) {
		this.sale_admit_date = sale_admit_date;
	}
	public String getDlvmon() {
		return dlvmon;
	}
	public void setDlvmon(String dlvmon) {
		this.dlvmon = dlvmon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}