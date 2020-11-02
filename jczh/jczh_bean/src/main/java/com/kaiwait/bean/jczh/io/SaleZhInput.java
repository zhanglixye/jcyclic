package com.kaiwait.bean.jczh.io;

import java.util.Date;
import java.util.List;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.SaleAdmin;
import com.kaiwait.bean.jczh.entity.SaleType;
import com.kaiwait.common.vo.json.server.BaseInputBean;


public class SaleZhInput extends BaseInputBean {
	private static final long serialVersionUID = 5085368117650161941L;
	private SaleType saleType;
	private SaleAdmin saleAdmin;
	  private String job_cd;
	  private String sale_remark;
	  private String sale_admit_remark;
	  private Date saleadddate;
	  private Date saleupddate;
	  private String saleaddusercd;
	  private String saleupdusercd;
	  private String saleno;
	  private float sale_amt;
	  private Date dlvday;
	  private float sale_foreign_amt;
	  private Date sale_use_date;
	  private String sale_cure_code;
	  private String sale_refer;
	  private String sale_foreign_code;
	  private int sale_ishave;
	  private float sale_vat_amt;
	  private int del_flg;
	  private int company_cd;
	  private float vat_amt;
	  private float req_amt;
	  private float sale_tax1;
	  private float sale_tax2;
	  private int sale_admit_usercd;
	  private Date sale_admit_date;
	  private String sale_foreign_type;
	  private JobLand jobland;
	  private Date plan_req_date;
	  private int ad_flg;
	  private List<JobLableTrn> jlTrn;
	  private int saleLockFlg;
	  //税率
	  private String vat_rate;
	  
	  
	public String getVat_rate() {
		return vat_rate;
	}
	public void setVat_rate(String vat_rate) {
		this.vat_rate = vat_rate;
	}
	public int getSaleLockFlg() {
		return saleLockFlg;
	}
	public void setSaleLockFlg(int saleLockFlg) {
		this.saleLockFlg = saleLockFlg;
	}
	public List<JobLableTrn> getJlTrn() {
		return jlTrn;
	}
	public void setJlTrn(List<JobLableTrn> jlTrn) {
		this.jlTrn = jlTrn;
	}
	public String getSale_admit_remark() {
		return sale_admit_remark;
	}
	public void setSale_admit_remark(String sale_admit_remark) {
		this.sale_admit_remark = sale_admit_remark;
	}
	public int getAd_flg() {
		return ad_flg;
	}
	public void setAd_flg(int ad_flg) {
		this.ad_flg = ad_flg;
	}
	public SaleAdmin getSaleAdmin() {
		return saleAdmin;
	}
	public void setSaleAdmin(SaleAdmin saleAdmin) {
		this.saleAdmin = saleAdmin;
	}
	public Date getPlan_req_date() {
		return plan_req_date;
	}
	public void setPlan_req_date(Date plan_req_date) {
		this.plan_req_date = plan_req_date;
	}
	public SaleType getSaleType() {
		return saleType;
	}
	public void setSaleType(SaleType saleType) {
		this.saleType = saleType;
	}
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getSale_remark() {
		return sale_remark;
	}
	public void setSale_remark(String sale_remark) {
		this.sale_remark = sale_remark;
	}
	public Date getSaleadddate() {
		return saleadddate;
	}
	public void setSaleadddate(Date saleadddate) {
		this.saleadddate = saleadddate;
	}
	public Date getSaleupddate() {
		return saleupddate;
	}
	public void setSaleupddate(Date saleupddate) {
		this.saleupddate = saleupddate;
	}
	public String getSaleaddusercd() {
		return saleaddusercd;
	}
	public void setSaleaddusercd(String saleaddusercd) {
		this.saleaddusercd = saleaddusercd;
	}
	public String getSaleupdusercd() {
		return saleupdusercd;
	}
	public void setSaleupdusercd(String saleupdusercd) {
		this.saleupdusercd = saleupdusercd;
	}

	public String getSaleno() {
		return saleno;
	}
	public void setSaleno(String saleno) {
		this.saleno = saleno;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public float getSale_amt() {
		return sale_amt;
	}
	public void setSale_amt(float sale_amt) {
		this.sale_amt = sale_amt;
	}
	public Date getDlvday() {
		return dlvday;
	}
	public void setDlvday(Date dlvday) {
		this.dlvday = dlvday;
	}
	public float getSale_foreign_amt() {
		return sale_foreign_amt;
	}
	public void setSale_foreign_amt(float sale_foreign_amt) {
		this.sale_foreign_amt = sale_foreign_amt;
	}
	public Date getSale_use_date() {
		return sale_use_date;
	}
	public void setSale_use_date(Date sale_use_date) {
		this.sale_use_date = sale_use_date;
	}
	public String getSale_cure_code() {
		return sale_cure_code;
	}
	public void setSale_cure_code(String sale_cure_code) {
		this.sale_cure_code = sale_cure_code;
	}
	public String getSale_refer() {
		return sale_refer;
	}
	public void setSale_refer(String sale_refer) {
		this.sale_refer = sale_refer;
	}
	public String getSale_foreign_code() {
		return sale_foreign_code;
	}
	public void setSale_foreign_code(String sale_foreign_code) {
		this.sale_foreign_code = sale_foreign_code;
	}
	public int getSale_ishave() {
		return sale_ishave;
	}
	public void setSale_ishave(int sale_ishave) {
		this.sale_ishave = sale_ishave;
	}
	public float getSale_vat_amt() {
		return sale_vat_amt;
	}
	public void setSale_vat_amt(float sale_vat_amt) {
		this.sale_vat_amt = sale_vat_amt;
	}
	public int getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(int del_flg) {
		this.del_flg = del_flg;
	}
	public int getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(int company_cd) {
		this.company_cd = company_cd;
	}
	public float getVat_amt() {
		return vat_amt;
	}
	public void setVat_amt(float vat_amt) {
		this.vat_amt = vat_amt;
	}
	public float getReq_amt() {
		return req_amt;
	}
	public void setReq_amt(float req_amt) {
		this.req_amt = req_amt;
	}
	public float getSale_tax1() {
		return sale_tax1;
	}
	public void setSale_tax1(float sale_tax1) {
		this.sale_tax1 = sale_tax1;
	}
	public float getSale_tax2() {
		return sale_tax2;
	}
	public void setSale_tax2(float sale_tax2) {
		this.sale_tax2 = sale_tax2;
	}
	public int getSale_admit_usercd() {
		return sale_admit_usercd;
	}
	public void setSale_admit_usercd(int sale_admit_usercd) {
		this.sale_admit_usercd = sale_admit_usercd;
	}
	public Date getSale_admit_date() {
		return sale_admit_date;
	}
	public void setSale_admit_date(Date sale_admit_date) {
		this.sale_admit_date = sale_admit_date;
	}
	public String getSale_foreign_type() {
		return sale_foreign_type;
	}
	public void setSale_foreign_type(String sale_foreign_type) {
		this.sale_foreign_type = sale_foreign_type;
	}
	public JobLand getJobland() {
		return jobland;
	}
	public void setJobland(JobLand jobland) {
		this.jobland = jobland;
	}
}
