package com.kaiwait.bean.jczh.entity;

import java.util.List;

import com.kaiwait.bean.jczh.vo.CommonmstVo;

public class JobLand {
//jobtrn 表
	//job编号
   private String job_cd;
   //件名
   private String job_name;
   //売上种目
   private String sale_typ;
   private String saleName;
   //得意先
   private String cldiv_cd;
   private String clName;
   private int divDelFlg;
   //请求先
   private String payer_cd;
   private String payername;
   private String payernameen;
   private String payernamefull;
   private int payDelFlg;
   //相守G公司
   private String g_company;
   private String gcompanyname;
   private String gcompanynameen;
   private String gcompanynamefull;
   private int gDelFlg;
   
   //计预终了日
   private String plan_dalday;
   private String dlvday;
   //预计卖上金额
   private String plan_sale;
   private String planSaleVat;
   //预计卖上金额外货
   private String plan_sale_foreign_amt;
   //卖上换算CODE
   private String plan_sale_cure_code;
   //卖上适用日
   private String plan_sale_use_date;
   //卖上是否税入;0：税拔；1：税入；
   private int plan_sale_ishave;
   //参照先
   private String  plan_sale_refer;
   //金税1
   private String plan_sale_tax1;
   //税金2
   private String plan_sale_tax2;
   //税金合计
   private String plansale_tax_total;
   //预计仕入增值税
   private String plancost_tax;
   //外货CODE
   private String plansale_foreign_code;
   //预计成本
   private String plancostamt;
   //预计成本适用日
   private String plan_cost_use_date;
   //预计成本参照先
   private String plan_cost_refer;
   //预计换算code
   private String plan_cost_cure_code=null;
   //预计成本外货
   private String plan_cost_foreign_amt;
   //预计成本外货CODE
   private String plan_cost_foreign_code;
   //预计成本是否税入;0：税拔；1：税入；
   private int plan_cost_ishave;
   //JOB登録日時
   private String adddate;
   //JOB更新日時
   private String upddate;
   //JOB登録者コード
   private String addusercd;
   private String addUserName;
   //JOB更新者コード
   private String updusercd;
   private String upUserName;
   //job登陆者姓名
   private String adduser;
   //job 更新这 姓名
   private String upduser;
   private String addcolor;
   private String updcolor;
   //备考
   private String remark;
   //公司ID
   private int company_cd;
   //0：正常、1：削除
   private int del_flg;
//joblabletrn
   //lable 编号
   private List<JobLableTrn>  jlTrn;
   private List<JobUserLable> julable;
   //删除的卡片
   private  List<JobUserLable> delcard;
 //更新job时增加的卡片
   private  List<JobUserLable> cardnew;
//lablemst
   //标签编号
   private String lable_id;
   //标签文本
   private String lable_text;
   //标签等级
   private int lable_level;
   // 请求金额
   private String  plan_req_amt;
   //卖上增值税
   private String plan_vat_amt;
   //在支付金额
   private String plan_pay_amt;
   //得意先 名
   private String divnm;
   private String divnm_en;
   private String divname_full;
   //卖上承认Flg
   private int confirmFlg;
   //记上FLg
   private int dlvFlg;
   //原价完了Flg
   private int costFinshFlg;
   private String costFinishUser;
   private String costFinishDate;
   //Job终了Flg
   private int jobEndFlg;
   //job结账flg
   private int accountFlg;
   //跳过
   private Skip skip;
   //卖上登录完了flg
   private int isSaleFinishFlg;
   //割当flg
   private int assignflg;
   //张票出力标识
   private List<CommonmstVo> outFlg_list;
   //卖上增值锐改变flg
   private int saleVatChangeFlg;
   //仕入增值税flg
   private int costVatChangeFlg;
   //乐观锁FLG
   private int lockFlg;
   

public int getDivDelFlg() {
	return divDelFlg;
}
public void setDivDelFlg(int divDelFlg) {
	this.divDelFlg = divDelFlg;
}
public int getPayDelFlg() {
	return payDelFlg;
}
public void setPayDelFlg(int payDelFlg) {
	this.payDelFlg = payDelFlg;
}
public int getgDelFlg() {
	return gDelFlg;
}
public void setgDelFlg(int gDelFlg) {
	this.gDelFlg = gDelFlg;
}
public int getLockFlg() {
	return lockFlg;
}
public void setLockFlg(int lockFlg) {
	this.lockFlg = lockFlg;
}
public String getAddcolor() {
	return addcolor;
}
public void setAddcolor(String addcolor) {
	this.addcolor = addcolor;
}
public String getUpdcolor() {
	return updcolor;
}
public void setUpdcolor(String updcolor) {
	this.updcolor = updcolor;
}
public int getAccountFlg() {
	return accountFlg;
}
public void setAccountFlg(int accountFlg) {
	this.accountFlg = accountFlg;
}
public int getSaleVatChangeFlg() {
	return saleVatChangeFlg;
}
public void setSaleVatChangeFlg(int saleVatChangeFlg) {
	this.saleVatChangeFlg = saleVatChangeFlg;
}
public int getCostVatChangeFlg() {
	return costVatChangeFlg;
}
public void setCostVatChangeFlg(int costVatChangeFlg) {
	this.costVatChangeFlg = costVatChangeFlg;
}
public List<JobUserLable> getDelcard() {
	return delcard;
}
public void setDelcard(List<JobUserLable> delcard) {
	this.delcard = delcard;
}
public List<JobUserLable> getCardnew() {
	return cardnew;
}
public void setCardnew(List<JobUserLable> cardnew) {
	this.cardnew = cardnew;
}
public List<CommonmstVo> getOutFlg_list() {
	return outFlg_list;
}
public void setOutFlg_list(List<CommonmstVo> outFlg_list) {
	this.outFlg_list = outFlg_list;
}
public String getPlan_pay_amt() {
	return plan_pay_amt;
}
public void setPlan_pay_amt(String plan_pay_amt) {
	this.plan_pay_amt = plan_pay_amt;
}
public String getPayername() {
	return payername;
}
public void setPayername(String payername) {
	this.payername = payername;
}
public String getPayernameen() {
	return payernameen;
}
public void setPayernameen(String payernameen) {
	this.payernameen = payernameen;
}
public String getPayernamefull() {
	return payernamefull;
}
public void setPayernamefull(String payernamefull) {
	this.payernamefull = payernamefull;
}
public String getGcompanyname() {
	return gcompanyname;
}
public void setGcompanyname(String gcompanyname) {
	this.gcompanyname = gcompanyname;
}
public String getGcompanynameen() {
	return gcompanynameen;
}
public void setGcompanynameen(String gcompanynameen) {
	this.gcompanynameen = gcompanynameen;
}
public String getGcompanynamefull() {
	return gcompanynamefull;
}
public void setGcompanynamefull(String gcompanynamefull) {
	this.gcompanynamefull = gcompanynamefull;
}
public String getCostFinishDate() {
	return costFinishDate;
}
public void setCostFinishDate(String costFinishDate) {
	this.costFinishDate = costFinishDate;
}
public int getAssignflg() {
	return assignflg;
}
public void setAssignflg(int assignflg) {
	this.assignflg = assignflg;
}
public int getIsSaleFinishFlg() {
	return isSaleFinishFlg;
}
public void setIsSaleFinishFlg(int isSaleFinishFlg) {
	this.isSaleFinishFlg = isSaleFinishFlg;
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
public String getSaleName() {
	return saleName;
}
public void setSaleName(String saleName) {
	this.saleName = saleName;
}
public String getCldiv_cd() {
	return cldiv_cd;
}
public void setCldiv_cd(String cldiv_cd) {
	this.cldiv_cd = cldiv_cd;
}
public String getClName() {
	return clName;
}
public void setClName(String clName) {
	this.clName = clName;
}
public String getPayer_cd() {
	return payer_cd;
}
public void setPayer_cd(String payer_cd) {
	this.payer_cd = payer_cd;
}

public String getG_company() {
	return g_company;
}
public void setG_company(String g_company) {
	this.g_company = g_company;
}

public String getPlan_dalday() {
	return plan_dalday;
}
public void setPlan_dalday(String plan_dalday) {
	this.plan_dalday = plan_dalday;
}
public String getDlvday() {
	return dlvday;
}
public void setDlvday(String dlvday) {
	this.dlvday = dlvday;
}
public String getPlan_sale() {
	return plan_sale;
}
public void setPlan_sale(String plan_sale) {
	this.plan_sale = plan_sale;
}
public String getPlanSaleVat() {
	return planSaleVat;
}
public void setPlanSaleVat(String planSaleVat) {
	this.planSaleVat = planSaleVat;
}
public String getPlan_sale_foreign_amt() {
	return plan_sale_foreign_amt;
}
public void setPlan_sale_foreign_amt(String plan_sale_foreign_amt) {
	this.plan_sale_foreign_amt = plan_sale_foreign_amt;
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
public int getPlan_sale_ishave() {
	return plan_sale_ishave;
}
public void setPlan_sale_ishave(int plan_sale_ishave) {
	this.plan_sale_ishave = plan_sale_ishave;
}
public String getPlan_sale_refer() {
	return plan_sale_refer;
}
public void setPlan_sale_refer(String plan_sale_refer) {
	this.plan_sale_refer = plan_sale_refer;
}
public String getPlan_sale_tax1() {
	return plan_sale_tax1;
}
public void setPlan_sale_tax1(String plan_sale_tax1) {
	this.plan_sale_tax1 = plan_sale_tax1;
}
public String getPlan_sale_tax2() {
	return plan_sale_tax2;
}
public void setPlan_sale_tax2(String plan_sale_tax2) {
	this.plan_sale_tax2 = plan_sale_tax2;
}
public String getPlansale_tax_total() {
	return plansale_tax_total;
}
public void setPlansale_tax_total(String plansale_tax_total) {
	this.plansale_tax_total = plansale_tax_total;
}
public String getPlancost_tax() {
	return plancost_tax;
}
public void setPlancost_tax(String plancost_tax) {
	this.plancost_tax = plancost_tax;
}
public String getPlansale_foreign_code() {
	return plansale_foreign_code;
}
public void setPlansale_foreign_code(String plansale_foreign_code) {
	this.plansale_foreign_code = plansale_foreign_code;
}

public String getPlancostamt() {
	return plancostamt;
}
public void setPlancostamt(String plancostamt) {
	this.plancostamt = plancostamt;
}
public String getPlan_cost_use_date() {
	return plan_cost_use_date;
}
public void setPlan_cost_use_date(String plan_cost_use_date) {
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
public String getPlan_cost_foreign_amt() {
	return plan_cost_foreign_amt;
}
public void setPlan_cost_foreign_amt(String plan_cost_foreign_amt) {
	this.plan_cost_foreign_amt = plan_cost_foreign_amt;
}
public String getPlan_cost_foreign_code() {
	return plan_cost_foreign_code;
}
public void setPlan_cost_foreign_code(String plan_cost_foreign_code) {
	this.plan_cost_foreign_code = plan_cost_foreign_code;
}
public int getPlan_cost_ishave() {
	return plan_cost_ishave;
}
public void setPlan_cost_ishave(int plan_cost_ishave) {
	this.plan_cost_ishave = plan_cost_ishave;
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
public String getAddUserName() {
	return addUserName;
}
public void setAddUserName(String addUserName) {
	this.addUserName = addUserName;
}
public String getUpdusercd() {
	return updusercd;
}
public void setUpdusercd(String updusercd) {
	this.updusercd = updusercd;
}
public String getUpUserName() {
	return upUserName;
}
public void setUpUserName(String upUserName) {
	this.upUserName = upUserName;
}
public String getAdduser() {
	return adduser;
}
public void setAdduser(String adduser) {
	this.adduser = adduser;
}
public String getUpduser() {
	return upduser;
}
public void setUpduser(String upduser) {
	this.upduser = upduser;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public int getCompany_cd() {
	return company_cd;
}
public void setCompany_cd(int company_cd) {
	this.company_cd = company_cd;
}
public int getDel_flg() {
	return del_flg;
}
public void setDel_flg(int del_flg) {
	this.del_flg = del_flg;
}
public List<JobLableTrn> getJlTrn() {
	return jlTrn;
}
public void setJlTrn(List<JobLableTrn> jlTrn) {
	this.jlTrn = jlTrn;
}
public List<JobUserLable> getJulable() {
	return julable;
}
public void setJulable(List<JobUserLable> julable) {
	this.julable = julable;
}
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
public int getLable_level() {
	return lable_level;
}
public void setLable_level(int lable_level) {
	this.lable_level = lable_level;
}
public String getPlan_req_amt() {
	return plan_req_amt;
}
public void setPlan_req_amt(String plan_req_amt) {
	this.plan_req_amt = plan_req_amt;
}
public String getPlan_vat_amt() {
	return plan_vat_amt;
}
public void setPlan_vat_amt(String plan_vat_amt) {
	this.plan_vat_amt = plan_vat_amt;
}
public String getDivnm() {
	return divnm;
}
public void setDivnm(String divnm) {
	this.divnm = divnm;
}
public String getDivnm_en() {
	return divnm_en;
}
public void setDivnm_en(String divnm_en) {
	this.divnm_en = divnm_en;
}
public String getDivname_full() {
	return divname_full;
}
public void setDivname_full(String divname_full) {
	this.divname_full = divname_full;
}
public int getConfirmFlg() {
	return confirmFlg;
}
public void setConfirmFlg(int confirmFlg) {
	this.confirmFlg = confirmFlg;
}
public int getDlvFlg() {
	return dlvFlg;
}
public void setDlvFlg(int dlvFlg) {
	this.dlvFlg = dlvFlg;
}
public int getCostFinshFlg() {
	return costFinshFlg;
}
public void setCostFinshFlg(int costFinshFlg) {
	this.costFinshFlg = costFinshFlg;
}

public String getCostFinishUser() {
	return costFinishUser;
}
public void setCostFinishUser(String costFinishUser) {
	this.costFinishUser = costFinishUser;
}
public int getJobEndFlg() {
	return jobEndFlg;
}
public void setJobEndFlg(int jobEndFlg) {
	this.jobEndFlg = jobEndFlg;
}
public Skip getSkip() {
	return skip;
}
public void setSkip(Skip skip) {
	this.skip = skip;
}
   

}
