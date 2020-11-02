package com.kaiwait.bean.jczh.entity;

import java.util.List;

import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.bean.jczh.vo.SalemstVo;

public class SaleAdmin {
	 private String job_cd;//
	 private String job_name;//件名
	 private String sale_no;//卖上编号
	 private String  dlvday;//记上日
	 private String plan_req_date;//预计申请日
	 private String  sale_foreign_code;//卖上外货code
	 private List<CommonmstVo> sale_foreign;//外货下拉
	 private String sale_amt;//卖上金额
	 private String  sale_cure_code;//汇率
	 private String  sale_use_date;//卖上适用日
	 private String sale_refer;//参照先
	 private String  sale_foreign_amt;//卖上外货金额
	 private String sale_vat_amt;//卖上税金
	 private String req_amt;//请求金额
	 private int saleishave;//税入税拔
	 private String sale_remark;//卖上登录备考
	 private String sale_admit_remark;//卖上承认备考
	 private String saleupdusercd;//更新者者cd
	 private String saleuper;//更新者姓名
	 private String saleupddate;//更新日期
	 //预计
	 private String plan_sale_foreign_type;//预计卖上外货code
	 private List<CommonmstVo> plan_sale_foreign;//预计卖上外货下拉
	 private String plan_sale;//预计卖上金额
	 private String plan_sale_cure_code;//汇率
	 private String plan_sale_use_date;//适用日
	 private String plan_sale_refer;//参照先
	 private String  plan_sale_foreign_amt;//外货金额
	 private String plantax;//税金
	 private String plan_req_amt;//请求金额
	 private int  planishave;//税入税拔
	 private String  upddate;//更新日期
	 private String updusercd;//更新者cd
	 private String uper;//更新者日期
	 private String  remark;//备考
	 private String divname_full;//得意先全名
	 private String  divnm;//得意先名
	 //卖上承认日
	 private String saleadmitdate;//承认日
	 private String saleadmitusercd;//承认者cd
	 private List<Cost> cost;//原价信息
	 //job原价
	  private String plancostamt;//原价金额
	  private String planvatamt;//卖上增值税
	  private String planpayamt;//支付金额
	 //卖上种目
	  private SalemstVo salemstvo;//
	  //成本完了flg
      private int costfinishflg;//
      //条数
      private int costnum;//
      //是否承认flg
      private int isAdmin;//前台0 显示承认，1 却下
      //计算用flg
      private List<CommonmstVo> list_tax;
      private List<CommonmstVo> List_foreign_tax;
      
    //joblabletrn
      //lable 编号
      private List<JobLableTrn>  jlTrn;
      private List<Lable> list_lable;//标签列表
      
      private  List<CommonmstVo> outFlg_list;
  //
      private  String salecolor;
      private String jobcolor;
      private int saleLockFlg;
      private JobList  JobList; //存储否终了及税金信息
      private String fromjpp;
      private String plansaletaxtotal; 
      private String vatamt;
      private String jobendflg;
      

	
	public String getJobendflg() {
		return jobendflg;
	}
	public void setJobendflg(String jobendflg) {
		this.jobendflg = jobendflg;
	}
	public String getVatamt() {
		return vatamt;
	}
	public void setVatamt(String vatamt) {
		this.vatamt = vatamt;
	}
	public String getPlansaletaxtotal() {
		return plansaletaxtotal;
	}
	public void setPlansaletaxtotal(String plansaletaxtotal) {
		this.plansaletaxtotal = plansaletaxtotal;
	}
	public String getFromjpp() {
		return fromjpp;
	}
	public void setFromjpp(String fromjpp) {
		this.fromjpp = fromjpp;
	}
	public JobList getJobList() {
		return JobList;
	}
	public void setJobList(JobList jobList) {
		JobList = jobList;
	}
	public int getSaleLockFlg() {
		return saleLockFlg;
	}
	public void setSaleLockFlg(int saleLockFlg) {
		this.saleLockFlg = saleLockFlg;
	}
	public String getSalecolor() {
		return salecolor;
	}
	public void setSalecolor(String salecolor) {
		this.salecolor = salecolor;
	}
	public String getJobcolor() {
		return jobcolor;
	}
	public void setJobcolor(String jobcolor) {
		this.jobcolor = jobcolor;
	}
	public List<CommonmstVo> getOutFlg_list() {
		return outFlg_list;
	}
	public void setOutFlg_list(List<CommonmstVo> outFlg_list) {
		this.outFlg_list = outFlg_list;
	}
	public List<JobLableTrn> getJlTrn() {
		return jlTrn;
	}
	public void setJlTrn(List<JobLableTrn> jlTrn) {
		this.jlTrn = jlTrn;
	}

	public List<Lable> getList_lable() {
		return list_lable;
	}
	public void setList_lable(List<Lable> list_lable) {
		this.list_lable = list_lable;
	}
	public List<CommonmstVo> getList_tax() {
		return list_tax;
	}
	public void setList_tax(List<CommonmstVo> list_tax) {
		this.list_tax = list_tax;
	}
	public List<CommonmstVo> getList_foreign_tax() {
		return List_foreign_tax;
	}
	public void setList_foreign_tax(List<CommonmstVo> list_foreign_tax) {
		List_foreign_tax = list_foreign_tax;
	}
	public String getSale_admit_remark() {
		return sale_admit_remark;
	}
	public void setSale_admit_remark(String sale_admit_remark) {
		this.sale_admit_remark = sale_admit_remark;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
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
	public String getSale_no() {
		return sale_no;
	}
	public void setSale_no(String sale_no) {
		this.sale_no = sale_no;
	}
	public String getDlvday() {
		return dlvday;
	}
	public void setDlvday(String dlvday) {
		this.dlvday = dlvday;
	}
	public String getPlan_req_date() {
		return plan_req_date;
	}
	public void setPlan_req_date(String plan_req_date) {
		this.plan_req_date = plan_req_date;
	}
	public String getSale_foreign_code() {
		return sale_foreign_code;
	}
	public void setSale_foreign_code(String sale_foreign_code) {
		this.sale_foreign_code = sale_foreign_code;
	}
	public List<CommonmstVo> getSale_foreign() {
		return sale_foreign;
	}
	public void setSale_foreign(List<CommonmstVo> sale_foreign) {
		this.sale_foreign = sale_foreign;
	}
	public String getSale_amt() {
		return sale_amt;
	}
	public void setSale_amt(String sale_amt) {
		this.sale_amt = sale_amt;
	}
	public String getSale_cure_code() {
		return sale_cure_code;
	}
	public void setSale_cure_code(String sale_cure_code) {
		this.sale_cure_code = sale_cure_code;
	}
	public String getSale_use_date() {
		return sale_use_date;
	}
	public void setSale_use_date(String sale_use_date) {
		this.sale_use_date = sale_use_date;
	}
	public String getSale_refer() {
		return sale_refer;
	}
	public void setSale_refer(String sale_refer) {
		this.sale_refer = sale_refer;
	}
	public String getSale_foreign_amt() {
		return sale_foreign_amt;
	}
	public void setSale_foreign_amt(String sale_foreign_amt) {
		this.sale_foreign_amt = sale_foreign_amt;
	}
	public String getSale_vat_amt() {
		return sale_vat_amt;
	}
	public void setSale_vat_amt(String sale_vat_amt) {
		this.sale_vat_amt = sale_vat_amt;
	}
	public String getReq_amt() {
		return req_amt;
	}
	public void setReq_amt(String req_amt) {
		this.req_amt = req_amt;
	}
	public int getSaleishave() {
		return saleishave;
	}
	public void setSaleishave(int saleishave) {
		this.saleishave = saleishave;
	}
	public String getSale_remark() {
		return sale_remark;
	}
	public void setSale_remark(String sale_remark) {
		this.sale_remark = sale_remark;
	}
	public String getSaleupdusercd() {
		return saleupdusercd;
	}
	public void setSaleupdusercd(String saleupdusercd) {
		this.saleupdusercd = saleupdusercd;
	}
	public String getSaleuper() {
		return saleuper;
	}
	public void setSaleuper(String saleuper) {
		this.saleuper = saleuper;
	}
	public String getSaleupddate() {
		return saleupddate;
	}
	public void setSaleupddate(String saleupddate) {
		this.saleupddate = saleupddate;
	}
	public String getPlan_sale_foreign_type() {
		return plan_sale_foreign_type;
	}
	public void setPlan_sale_foreign_type(String plan_sale_foreign_type) {
		this.plan_sale_foreign_type = plan_sale_foreign_type;
	}
	public List<CommonmstVo> getPlan_sale_foreign() {
		return plan_sale_foreign;
	}
	public void setPlan_sale_foreign(List<CommonmstVo> plan_sale_foreign) {
		this.plan_sale_foreign = plan_sale_foreign;
	}
	public String getPlan_sale() {
		return plan_sale;
	}
	public void setPlan_sale(String plan_sale) {
		this.plan_sale = plan_sale;
	}
	public String getPlan_sale_cure_code() {
		return plan_sale_cure_code;
	}
	public void setPlan_sale_cure_code(String plan_sale_cure_code) {
		this.plan_sale_cure_code = plan_sale_cure_code;
	}
	public String getPlan_sale_use_date() {
		return plan_sale_use_date;
	}
	public void setPlan_sale_use_date(String plan_sale_use_date) {
		this.plan_sale_use_date = plan_sale_use_date;
	}
	public String getPlan_sale_refer() {
		return plan_sale_refer;
	}
	public void setPlan_sale_refer(String plan_sale_refer) {
		this.plan_sale_refer = plan_sale_refer;
	}
	public String getPlan_sale_foreign_amt() {
		return plan_sale_foreign_amt;
	}
	public void setPlan_sale_foreign_amt(String plan_sale_foreign_amt) {
		this.plan_sale_foreign_amt = plan_sale_foreign_amt;
	}
	public String getPlantax() {
		return plantax;
	}
	public void setPlantax(String plantax) {
		this.plantax = plantax;
	}
	public String getPlan_req_amt() {
		return plan_req_amt;
	}
	public void setPlan_req_amt(String plan_req_amt) {
		this.plan_req_amt = plan_req_amt;
	}
	public int getPlanishave() {
		return planishave;
	}
	public void setPlanishave(int planishave) {
		this.planishave = planishave;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getUpdusercd() {
		return updusercd;
	}
	public void setUpdusercd(String updusercd) {
		this.updusercd = updusercd;
	}
	public String getUper() {
		return uper;
	}
	public void setUper(String uper) {
		this.uper = uper;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDivname_full() {
		return divname_full;
	}
	public void setDivname_full(String divname_full) {
		this.divname_full = divname_full;
	}
	public String getDivnm() {
		return divnm;
	}
	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}
	public String getSaleadmitdate() {
		return saleadmitdate;
	}
	public void setSaleadmitdate(String saleadmitdate) {
		this.saleadmitdate = saleadmitdate;
	}
	public String getSaleadmitusercd() {
		return saleadmitusercd;
	}
	public void setSaleadmitusercd(String saleadmitusercd) {
		this.saleadmitusercd = saleadmitusercd;
	}
	public List<Cost> getCost() {
		return cost;
	}
	public void setCost(List<Cost> cost) {
		this.cost = cost;
	}
	public String getPlancostamt() {
		return plancostamt;
	}
	public void setPlancostamt(String plancostamt) {
		this.plancostamt = plancostamt;
	}
	public String getPlanvatamt() {
		return planvatamt;
	}
	public void setPlanvatamt(String planvatamt) {
		this.planvatamt = planvatamt;
	}
	public String getPlanpayamt() {
		return planpayamt;
	}
	public void setPlanpayamt(String planpayamt) {
		this.planpayamt = planpayamt;
	}

	public SalemstVo getSalemstvo() {
		return salemstvo;
	}
	public void setSalemstvo(SalemstVo salemstvo) {
		this.salemstvo = salemstvo;
	}
	public int getCostfinishflg() {
		return costfinishflg;
	}
	public void setCostfinishflg(int costfinishflg) {
		this.costfinishflg = costfinishflg;
	}
	public int getCostnum() {
		return costnum;
	}
	public void setCostnum(int costnum) {
		this.costnum = costnum;
	}
	
	 
}
