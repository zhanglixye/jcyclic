package com.kaiwait.bean.jczh.io;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.PayDeal;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class PayInput extends BaseInputBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String   job_cd;
	private String   cost_no;//发注编号
	private Date   pay_hope_date;//支付希望日
	private String   payee_cd;//发注先
	private Date   pay_dlyday;//纳品预定日
	private BigDecimal   pay_rate;//税率
	private BigDecimal   pay_foreign_amt;//外货发注金额
	private BigDecimal   pay_rmb;//发注金额本国货币
	private BigDecimal   pay_vat_amt;//增值税
	private String   pay_cure_code;//换算CODE
	private Date   pay_use_date;//用日适
	private int   pay_ishave;//卖上是否税入;0：税拔；1：税入；
	private String   pay_refer;//照线先
	private String   pay_foreign_type;//外货货币种类编号
	private String   invoice_type;//发票种类
	private String   invoice_text;//发票内容
	private String   input_no;//支付编号
	private int   status;//0：発注登録済、1：支払登録済、2：支払申請済、3：支払承認済
	private String   deduction_flg;
	private String   del_flg;
	private String   company_cd;
	private String   adddate;
	private String   update;
	private String   addusercd;
	private String   upusercd;
	private Date   invoice_date;//发票受领日
	private String   invoice_no;//发票编号
	private String   remark;//发票备注
	private String   payremark;//支付备注
	private String   pay_amt;
	private int   payflg;//支付状态
	private int   paycanceldateflg;//支付处理取消日期b标识
	private String   paycanceldate;//支付处理取消日期
	private String   paydate;//支付处理 日
	private List<Prooftrn> list_rooftrn;//凭证
	private List<PayDeal> payInput;
	private int   vat_change_flg;
	private int   lockflg;
	private String   newinput_no;//支付编号
	
	public String getNewinput_no() {
		return newinput_no;
	}
	public void setNewinput_no(String newinput_no) {
		this.newinput_no = newinput_no;
	}
	public int getLockflg() {
		return lockflg;
	}
	public void setLockflg(int lockflg) {
		this.lockflg = lockflg;
	}
	public int getVat_change_flg() {
		return vat_change_flg;
	}
	public void setVat_change_flg(int vat_change_flg) {
		this.vat_change_flg = vat_change_flg;
	}
	public String getPay_amt() {
		return pay_amt;
	}
	public void setPay_amt(String pay_amt) {
		this.pay_amt = pay_amt;
	}
	public List<Prooftrn> getList_rooftrn() {
		return list_rooftrn;
	}
	public void setList_rooftrn(List<Prooftrn> list_rooftrn) {
		this.list_rooftrn = list_rooftrn;
	}
	public List<PayDeal> getPayInput() {
		return payInput;
	}
	public void setPayInput(List<PayDeal> payInput) {
		this.payInput = payInput;
	}
	public int getPaycanceldateflg() {
		return paycanceldateflg;
	}
	public void setPaycanceldateflg(int paycanceldateflg) {
		this.paycanceldateflg = paycanceldateflg;
	}
	public int getPayflg() {
		return payflg;
	}
	public void setPayflg(int payflg) {
		this.payflg = payflg;
	}
	public String getPaycanceldate() {
		return paycanceldate;
	}
	public void setPaycanceldate(String paycanceldate) {
		this.paycanceldate = paycanceldate;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	private List<JobLableTrn>  lableList;
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

	public Date getPay_hope_date() {
		return pay_hope_date;
	}
	public void setPay_hope_date(Date pay_hope_date) {
		this.pay_hope_date = pay_hope_date;
	}
	public String getPayee_cd() {
		return payee_cd;
	}
	public void setPayee_cd(String payee_cd) {
		this.payee_cd = payee_cd;
	}
	public BigDecimal getPay_rate() {
		return pay_rate;
	}
	public void setPay_rate(BigDecimal pay_rate) {
		this.pay_rate = pay_rate;
	}
	public BigDecimal getPay_foreign_amt() {
		return pay_foreign_amt;
	}
	public void setPay_foreign_amt(BigDecimal pay_foreign_amt) {
		this.pay_foreign_amt = pay_foreign_amt;
	}
	public BigDecimal getPay_rmb() {
		return pay_rmb;
	}
	public void setPay_rmb(BigDecimal pay_rmb) {
		this.pay_rmb = pay_rmb;
	}
	public BigDecimal getPay_vat_amt() {
		return pay_vat_amt;
	}
	public void setPay_vat_amt(BigDecimal pay_vat_amt) {
		this.pay_vat_amt = pay_vat_amt;
	}
	public String getPay_cure_code() {
		return pay_cure_code;
	}
	public void setPay_cure_code(String pay_cure_code) {
		this.pay_cure_code = pay_cure_code;
	}
	public int getPay_ishave() {
		return pay_ishave;
	}
	public void setPay_ishave(int pay_ishave) {
		this.pay_ishave = pay_ishave;
	}
	public String getPay_refer() {
		return pay_refer;
	}
	public void setPay_refer(String pay_refer) {
		this.pay_refer = pay_refer;
	}
	public String getPay_foreign_type() {
		return pay_foreign_type;
	}
	public void setPay_foreign_type(String pay_foreign_type) {
		this.pay_foreign_type = pay_foreign_type;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDeduction_flg() {
		return deduction_flg;
	}
	public void setDeduction_flg(String deduction_flg) {
		this.deduction_flg = deduction_flg;
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
	public String getPayremark() {
		return payremark;
	}
	public void setPayremark(String payremark) {
		this.payremark = payremark;
	}
	public List<JobLableTrn> getLableList() {
		return lableList;
	}
	public void setLableList(List<JobLableTrn> lableList) {
		this.lableList = lableList;
	}
	public Date getPay_dlyday() {
		return pay_dlyday;
	}
	public void setPay_dlyday(Date pay_dlyday) {
		this.pay_dlyday = pay_dlyday;
	}
	public Date getPay_use_date() {
		return pay_use_date;
	}
	public void setPay_use_date(Date pay_use_date) {
		this.pay_use_date = pay_use_date;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	
	
}
