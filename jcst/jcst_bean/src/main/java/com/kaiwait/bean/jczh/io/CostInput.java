package com.kaiwait.bean.jczh.io;

import java.util.Date;
import java.util.List;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.TitleMsg;
import com.kaiwait.bean.jczh.vo.CostListVo;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class CostInput extends BaseInputBean {
	private static final long serialVersionUID = 5085368117650161941L;
	private String   job_cd;
	private String   cost_no;//发注编号
	private Date   order_date;//发注日
	private String   payee_cd;//发注先
	private String   cost_name;//外发名称
	private String   cost_type;//分类项目
	private Date   plan_dlvday;//纳品预定日
	private String   cost_remark;//备考
	private String   cost_rate;//税率
	private String   cost_foreign_amt;//外货发注金额
	private String   cost_rmb;//发注金额本国货币
	private String   cost_vat_amt;//增值税
	private String   cost_pay_amt;//最终支付金额
	private String   cost_cure_code;//换算CODE
	private Date   cost_use_date;//用日适
	private String   cost_ishave;//卖上是否税入;0：税拔；1：税入；
	private String   cost_refer;//照线先
	private String   cost_foreign_type;//外货货币种类编号
	private String   invoice_type;//发票种类
	private String   invoice_text;//发票内容
	private String   input_no;//支付编号
	private String   status;//0：発注登録済、1：支払登録済、2：支払申請済、3：支払承認済
	private String   deduction_flg;
	private String   del_flg;
	private String   company_cd;
	private String   adddate;
	private String   update;
	private String   addusercd;
	private String   upusercd;
	private List<JobLableTrn>  lableList;
	private Date   invoice_date;//发票受领日
	private String   invoice_no;//发票编号
	private String   remark;//发票备注
	private CostListVo costListVo;
	private String keyword;
	private String ad;
	private int msgId;
	private int vat_change_flg;
	private int cost_pdf_addusercd;
	private String cost_pdf_adddate;
	private int lockflg;
	
	public int getLockflg() {
		return lockflg;
	}
	public void setLockflg(int lockflg) {
		this.lockflg = lockflg;
	}
	public int getCost_pdf_addusercd() {
		return cost_pdf_addusercd;
	}
	public void setCost_pdf_addusercd(int cost_pdf_addusercd) {
		this.cost_pdf_addusercd = cost_pdf_addusercd;
	}
	public String getCost_pdf_adddate() {
		return cost_pdf_adddate;
	}
	public void setCost_pdf_adddate(String string) {
		this.cost_pdf_adddate = string;
	}
	public int getVat_change_flg() {
		return vat_change_flg;
	}
	public void setVat_change_flg(int vat_change_flg) {
		this.vat_change_flg = vat_change_flg;
	}
	private List<TitleMsg> listTitleMsg;
	private JobListInput jobInput;
	
	public JobListInput getJobInput() {
		return jobInput;
	}
	public void setJobInput(JobListInput jobInput) {
		this.jobInput = jobInput;
	}
	public List<TitleMsg> getListTitleMsg() {
		return listTitleMsg;
	}
	public void setListTitleMsg(List<TitleMsg> listTitleMsg) {
		this.listTitleMsg = listTitleMsg;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public CostListVo getCostListVo() {
		return costListVo;
	}
	public void setCostListVo(CostListVo costListVo) {
		this.costListVo = costListVo;
	}
	public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}
	public String getCost_no() {
		return cost_no;
	}
	public void setCost_no(String cost_no) {
		this.cost_no = cost_no;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getPayee_cd() {
		return payee_cd;
	}
	public void setPayee_cd(String payee_cd) {
		this.payee_cd = payee_cd;
	}
	public String getCost_name() {
		return cost_name;
	}
	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}
	public String getCost_type() {
		return cost_type;
	}
	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
	}
	public Date getPlan_dlvday() {
		return plan_dlvday;
	}
	public void setPlan_dlvday(Date plan_dlvday) {
		this.plan_dlvday = plan_dlvday;
	}
	public String getCost_remark() {
		return cost_remark;
	}
	public void setCost_remark(String cost_remark) {
		this.cost_remark = cost_remark;
	}

	public String getCost_rate() {
		return cost_rate;
	}
	public void setCost_rate(String cost_rate) {
		this.cost_rate = cost_rate;
	}
	public String getCost_foreign_amt() {
		return cost_foreign_amt;
	}
	public void setCost_foreign_amt(String cost_foreign_amt) {
		this.cost_foreign_amt = cost_foreign_amt;
	}
	public String getCost_rmb() {
		return cost_rmb;
	}
	public void setCost_rmb(String cost_rmb) {
		this.cost_rmb = cost_rmb;
	}
	public String getCost_vat_amt() {
		return cost_vat_amt;
	}
	public void setCost_vat_amt(String cost_vat_amt) {
		this.cost_vat_amt = cost_vat_amt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setCost_pay_amt(String cost_pay_amt) {
		this.cost_pay_amt = cost_pay_amt;
	}
	public String getCost_cure_code() {
		return cost_cure_code;
	}
	public void setCost_cure_code(String cost_cure_code) {
		this.cost_cure_code = cost_cure_code;
	}
	public Date getCost_use_date() {
		return cost_use_date;
	}
	public void setCost_use_date(Date cost_use_date) {
		this.cost_use_date = cost_use_date;
	}
	public String getCost_ishave() {
		return cost_ishave;
	}
	public void setCost_ishave(String cost_ishave) {
		this.cost_ishave = cost_ishave;
	}
	public String getCost_refer() {
		return cost_refer;
	}
	public void setCost_refer(String cost_refer) {
		this.cost_refer = cost_refer;
	}
	public String getCost_foreign_type() {
		return cost_foreign_type;
	}
	public void setCost_foreign_type(String cost_foreign_type) {
		this.cost_foreign_type = cost_foreign_type;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_text() {
		return invoice_text;
	}
	public void setInvoice_text(String invoice_text) {
		this.invoice_text = invoice_text;
	}
	public String getInput_no() {
		return input_no;
	}
	public void setInput_no(String input_no) {
		this.input_no = input_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<JobLableTrn> getLableList() {
		return lableList;
	}
	public void setLableList(List<JobLableTrn> lableList) {
		this.lableList = lableList;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeduction_flg() {
		return deduction_flg;
	}
	public void setDeduction_flg(String deduction_flg) {
		this.deduction_flg = deduction_flg;
	}
	public String getCost_pay_amt() {
		return cost_pay_amt;
	}
		
}
