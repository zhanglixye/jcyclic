package com.kaiwait.bean.jczh.io;

import java.util.Date;
import java.util.List;

import com.kaiwait.bean.jczh.entity.BindLable;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.JobUserLable;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.ListLable;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class JobZhInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	  private JobLand job_land;
	  private Lable lable;
	  private BindLable bindlable;
	  private ListLable listlable;
	  private String job_cd;
	//件名
	   private String job_name;
	   //売上种目
	   private String sale_typ;
	   //得意先
	   private String cldiv_cd;
	   //请求先
	   private String payer_cd;
	   //相守G公司
	   private String g_company;
	   //计预终了日
	   private Date plan_dalday;
	   //预计卖上金额
	   private float plan_sale;
	   //预计卖上金额外货
	   private float plan_sale_foreign_amt;
	   //卖上换算CODE
	   private String plan_sale_cure_code;
	//卖上适用日
	   private Date plan_sale_use_date;
	   //卖上是否税入;0：税拔；1：税入；
	   private Integer plan_sale_ishave;
	   //参照先
	   private String  plan_sale_refer;
	   //金税1
	   private float plan_sale_tax1;
	   //税金2
	   private float plan_sale_tax2;
	   //税金合计
	   private float plansale_tax_total;
	   //预计仕入增值税
	   private float plancost_tax;
	   //外货CODE
	   private String plansale_foreign_code;
	   //预计成本
	   private float plancost_amt;
	   //预计成本适用日
	   private Date plan_cost_use_date;
	   //预计成本参照先
	   private String plan_cost_refer;
	   //预计换算code
	   private String plan_cost_cure_code;
	   //预计成本外货
	   private float plan_cost_foreign_amt;
	   //预计成本外货CODE
	   private String plan_cost_foreign_code;
	   //预计成本是否税入;0：税拔；1：税入；
	   private Integer plan_cost_ishave;
	   //JOB登録日時
	   private Date add_date;
	   //JOB更新日時
	   private Date upd_date;
	   //JOB登録者コード
	   private String add_user_cd;
	   //JOB更新者コード
	   private String upd_user_cd;
	   //备考
	   private String remark;
	   //公司ID
	   private Integer company_cd;
	   //0：正常、1：削除
	   private Integer del_flg;

	   private List<JobUserLable> julable;

	//lablemst
	   //标签编号
	   private String lable_id;
	   //标签文本
	   private String lable_text;
	   //标签等级
	   private Integer lable_level;
	   // 请求金额
	   private float  plan_req_amt;
	   //支付金额
	   private float plan_vat_amt;
	  //获取卖上税金
	    private String dalday;
	    private String salecd;
	    private String fileName;
	    //本国货币编号
	    private String localMoneyCode;
	    private String langTyp;
	   private int lockFlg;
	   private int saleLockFlg;
	   private int recLockFlg;
	   
	   
	public int getRecLockFlg() {
		return recLockFlg;
	}
	public void setRecLockFlg(int recLockFlg) {
		this.recLockFlg = recLockFlg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getSaleLockFlg() {
		return saleLockFlg;
	}
	public void setSaleLockFlg(int saleLockFlg) {
		this.saleLockFlg = saleLockFlg;
	}
	public int getLockFlg() {
		return lockFlg;
	}
	public void setLockFlg(int lockFlg) {
		this.lockFlg = lockFlg;
	}
	public String getLangTyp() {
			return langTyp;
		}
		public void setLangTyp(String langTyp) {
			this.langTyp = langTyp;
		}
	public String getLocalMoneyCode() {
			return localMoneyCode;
		}
		public void setLocalMoneyCode(String localMoneyCode) {
			this.localMoneyCode = localMoneyCode;
		}
	public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	public String getDalday() {
			return dalday;
		}
		public void setDalday(String dalday) {
			this.dalday = dalday;
		}
		public String getSalecd() {
			return salecd;
		}
		public void setSalecd(String salecd) {
			this.salecd = salecd;
		}
	public BindLable getBindlable() {
		return bindlable;
	}
	public void setBindlable(BindLable bindlable) {
		this.bindlable = bindlable;
	}
	public String getG_company() {
		return g_company;
	}
	public void setG_company(String g_company) {
		this.g_company = g_company;
	}
	public float getPlan_req_amt() {
		return plan_req_amt;
	}
	public void setPlan_req_amt(float plan_req_amt) {
		this.plan_req_amt = plan_req_amt;
	}
	public float getPlan_vat_amt() {
		return plan_vat_amt;
	}
	public void setPlan_vat_amt(float plan_vat_amt) {
		this.plan_vat_amt = plan_vat_amt;
	}
    public Lable getLable() {
			return lable;
		}
	public void setLable(Lable lable) {
			this.lable = lable;
		}
	public List<JobUserLable> getJulable() {
		return julable;
	}
	public void setJulable(List<JobUserLable> julable) {
		this.julable = julable;
	}
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getSale_typ() {
		return sale_typ;
	}
	public void setSale_typ(String sale_typ) {
		this.sale_typ = sale_typ;
	}
	public String getCldiv_cd() {
		return cldiv_cd;
	}
	public void setCldiv_cd(String cldiv_cd) {
		this.cldiv_cd = cldiv_cd;
	}
	public String getPayer_cd() {
		return payer_cd;
	}
	public void setPayer_cd(String payer_cd) {
		this.payer_cd = payer_cd;
	}
	public Date getPlan_dalday() {
		return plan_dalday;
	}
	public void setPlan_dalday(Date plan_dalday) {
		this.plan_dalday = plan_dalday;
	}
	public float getPlan_sale() {
		return plan_sale;
	}
	public void setPlan_sale(float plan_sale) {
		this.plan_sale = plan_sale;
	}
	public float getPlan_sale_foreign_amt() {
		return plan_sale_foreign_amt;
	}
	public void setPlan_sale_foreign_amt(float plan_sale_foreign_amt) {
		this.plan_sale_foreign_amt = plan_sale_foreign_amt;
	}
	public String getPlan_sale_cure_code() {
		return plan_sale_cure_code;
	}
	public void setPlan_sale_cure_code(String plan_sale_cure_code) {
		this.plan_sale_cure_code = plan_sale_cure_code;
	}
	public Date getPlan_sale_use_date() {
		return plan_sale_use_date;
	}
	public void setPlan_sale_use_date(Date plan_sale_use_date) {
		this.plan_sale_use_date = plan_sale_use_date;
	}
	public Integer getPlan_sale_ishave() {
		return plan_sale_ishave;
	}
	public void setPlan_sale_ishave(Integer plan_sale_ishave) {
		this.plan_sale_ishave = plan_sale_ishave;
	}
	public String getPlan_sale_refer() {
		return plan_sale_refer;
	}
	public void setPlan_sale_refer(String plan_sale_refer) {
		this.plan_sale_refer = plan_sale_refer;
	}
	public float getPlan_sale_tax1() {
		return plan_sale_tax1;
	}
	public void setPlan_sale_tax1(float plan_sale_tax1) {
		this.plan_sale_tax1 = plan_sale_tax1;
	}
	public float getPlan_sale_tax2() {
		return plan_sale_tax2;
	}
	public void setPlan_sale_tax2(float plan_sale_tax2) {
		this.plan_sale_tax2 = plan_sale_tax2;
	}
	public float getPlansale_tax_total() {
		return plansale_tax_total;
	}
	public void setPlansale_tax_total(float plansale_tax_total) {
		this.plansale_tax_total = plansale_tax_total;
	}
	public float getPlancost_tax() {
		return plancost_tax;
	}
	public void setPlancost_tax(float plancost_tax) {
		this.plancost_tax = plancost_tax;
	}
	public String getPlansale_foreign_code() {
		return plansale_foreign_code;
	}
	public void setPlansale_foreign_code(String plansale_foreign_code) {
		this.plansale_foreign_code = plansale_foreign_code;
	}
	public float getPlancost_amt() {
		return plancost_amt;
	}
	public void setPlancost_amt(float plancost_amt) {
		this.plancost_amt = plancost_amt;
	}
	public Date getPlan_cost_use_date() {
		return plan_cost_use_date;
	}
	public void setPlan_cost_use_date(Date plan_cost_use_date) {
		this.plan_cost_use_date = plan_cost_use_date;
	}
	public String getPlan_cost_refer() {
		return plan_cost_refer;
	}
	public void setPlan_cost_refer(String plan_cost_refer) {
		this.plan_cost_refer = plan_cost_refer;
	}
	public String getPlan_cost_cure_code() {
		return plan_cost_cure_code;
	}
	public void setPlan_cost_cure_code(String plan_cost_cure_code) {
		this.plan_cost_cure_code = plan_cost_cure_code;
	}
	public float getPlan_cost_foreign_amt() {
		return plan_cost_foreign_amt;
	}
	public void setPlan_cost_foreign_amt(float plan_cost_foreign_amt) {
		this.plan_cost_foreign_amt = plan_cost_foreign_amt;
	}
	public String getPlan_cost_foreign_code() {
		return plan_cost_foreign_code;
	}
	public void setPlan_cost_foreign_code(String plan_cost_foreign_code) {
		this.plan_cost_foreign_code = plan_cost_foreign_code;
	}
	public Integer getPlan_cost_ishave() {
		return plan_cost_ishave;
	}
	public void setPlan_cost_ishave(Integer plan_cost_ishave) {
		this.plan_cost_ishave = plan_cost_ishave;
	}
	public Date getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}
	public Date getUpd_date() {
		return upd_date;
	}
	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}
	public String getAdd_user_cd() {
		return add_user_cd;
	}
	public void setAdd_user_cd(String add_user_cd) {
		this.add_user_cd = add_user_cd;
	}
	public String getUpd_user_cd() {
		return upd_user_cd;
	}
	public void setUpd_user_cd(String upd_user_cd) {
		this.upd_user_cd = upd_user_cd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(Integer company_cd) {
		this.company_cd = company_cd;
	}
	public Integer getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(Integer del_flg) {
		this.del_flg = del_flg;
	}

//	public List<String> getUser_cd() {
//		return user_cd;
//	}
//	public void setUser_cd(List<String> user_cd) {
//		this.user_cd = user_cd;
//	}
//	public List<String> getLevel_flg() {
//		return level_flg;
//	}
//	public void setLevel_flg(List<String> level_flg) {
//		this.level_flg = level_flg;
//	}
//	public String getJuser_del_flg() {
//		return juser_del_flg;
//	}
//	public void setJuser_del_flg(String juser_del_flg) {
//		this.juser_del_flg = juser_del_flg;
//	}
	public String getLable_id() {
		return lable_id;
	}
	public void setLable_id(String lable_id) {
		this.lable_id = lable_id;
	}
	public String getLable_text() {
		return lable_text;
	}
	public void setLable_text(String lable_text) {
		this.lable_text = lable_text;
	}
	public Integer getLable_level() {
		return lable_level;
	}
	public void setLable_level(Integer lable_level) {
		this.lable_level = lable_level;
	}
	   public JobLand getJob_land() {
		return job_land;
	}
	public void setJob_land(JobLand job_land) {
		this.job_land = job_land;
	}
	public ListLable getListlable() {
		return listlable;
	}
	public void setListlable(ListLable listlable) {
		this.listlable = listlable;
	}


	
}