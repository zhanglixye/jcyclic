package com.kaiwait.bean.mst.vo;

import java.util.List;

/**
 * 
* @ClassName: SalesList  
* @Description: TODO(売上種目实体类)  
* @author mayouyi  
* @date 2018年5月2日  
*
 */
public class SalemstVo{

	private List<CommonmstVo> commontypeList;
	private String sale_cd;// 业务类别编号（売上種目コード）
	private String sale_account_cd;// 业务类别财务系统编号(売上種目会計コード)
	private String sale_name;// 业务类别名称(種目名称)
	private String tran_lend;// 代垫和转账处理(立替振替)
	private String cost_reduction;// 扣除成本(原価控除)
	private String common_type;
	private String start_date;// 税率开始时间
	private String end_date;// 税率结束时间
	private String rate1;// 税金1
	private String rate2;// 税金2
	private String rate3;// 税金3
	private String add_date;//税金表增加时间
	private String add_user;
	private String vat_rate;
	private String del_flg;
	private String vat_end_date;
	private String vat_start_date;
	private String var_add_date;//增值税表增加时间
	private String itemname_en;
	private String itemname_jp;
	private String itemname_zc;
	private String itemname_zt;
	private String company_cd;
	private String tax2Namezc;
	private String tax2Nameen;
	private String tax2Namezt;
	private String tax2Namejp;
	private String tax3Namezc;
	private String tax3Nameen;
	private String tax3Namezt;
	private String tax3Namejp;
	private int lock_flg;
	
	
	
	public String getTax2Namezt() {
		return tax2Namezt;
	}
	public void setTax2Namezt(String tax2Namezt) {
		this.tax2Namezt = tax2Namezt;
	}
	public String getTax3Namezt() {
		return tax3Namezt;
	}
	public void setTax3Namezt(String tax3Namezt) {
		this.tax3Namezt = tax3Namezt;
	}
	public int getLock_flg() {
		return lock_flg;
	}
	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
	}
	public String getTax2Namezc() {
		return tax2Namezc;
	}
	public void setTax2Namezc(String tax2Namezc) {
		this.tax2Namezc = tax2Namezc;
	}
	public String getTax2Nameen() {
		return tax2Nameen;
	}
	public void setTax2Nameen(String tax2Nameen) {
		this.tax2Nameen = tax2Nameen;
	}
	public String getTax2Namejp() {
		return tax2Namejp;
	}
	public void setTax2Namejp(String tax2Namejp) {
		this.tax2Namejp = tax2Namejp;
	}
	public String getTax3Namezc() {
		return tax3Namezc;
	}
	public void setTax3Namezc(String tax3Namezc) {
		this.tax3Namezc = tax3Namezc;
	}
	public String getTax3Nameen() {
		return tax3Nameen;
	}
	public void setTax3Nameen(String tax3Nameen) {
		this.tax3Nameen = tax3Nameen;
	}
	public String getTax3Namejp() {
		return tax3Namejp;
	}
	public void setTax3Namejp(String tax3Namejp) {
		this.tax3Namejp = tax3Namejp;
	}
	public String getVar_add_date() {
		return var_add_date;
	}
	public void setVar_add_date(String var_add_date) {
		this.var_add_date = var_add_date;
	}
	public String getVat_end_date() {
		return vat_end_date;
	}
	public void setVat_end_date(String vat_end_date) {
		this.vat_end_date = vat_end_date;
	}
	public String getVat_start_date() {
		return vat_start_date;
	}
	public void setVat_start_date(String vat_start_date) {
		this.vat_start_date = vat_start_date;
	}
	public List<CommonmstVo> getCommontypeList() {
		return commontypeList;
	}
	public void setCommontypeList(List<CommonmstVo> commontypeList) {
		this.commontypeList = commontypeList;
	}
	public String getVat_rate() {
		return vat_rate;
	}
	public void setVat_rate(String vat_rate) {
		this.vat_rate = vat_rate;
	}
	public String getSale_cd() {
		return sale_cd;
	}
	public void setSale_cd(String sale_cd) {
		this.sale_cd = sale_cd;
	}
	public String getSale_account_cd() {
		return sale_account_cd;
	}
	public void setSale_account_cd(String sale_account_cd) {
		this.sale_account_cd = sale_account_cd;
	}
	public String getSale_name() {
		return sale_name;
	}
	public void setSale_name(String sale_name) {
		this.sale_name = sale_name;
	}
	public String getTran_lend() {
		return tran_lend;
	}
	public void setTran_lend(String tran_lend) {
		this.tran_lend = tran_lend;
	}
	public String getCost_reduction() {
		return cost_reduction;
	}
	public void setCost_reduction(String cost_reduction) {
		this.cost_reduction = cost_reduction;
	}
	
	public String getCommon_type() {
		return common_type;
	}
	public void setCommon_type(String common_type) {
		this.common_type = common_type;
	}

	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getRate1() {
		return rate1;
	}
	public void setRate1(String rate1) {
		this.rate1 = rate1;
	}
	public String getRate2() {
		return rate2;
	}
	public void setRate2(String rate2) {
		this.rate2 = rate2;
	}
	public String getRate3() {
		return rate3;
	}
	public void setRate3(String rate3) {
		this.rate3 = rate3;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getAdd_user() {
		return add_user;
	}
	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
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
	public String getItemname_zc() {
		return itemname_zc;
	}
	public void setItemname_zc(String itemname_zc) {
		this.itemname_zc = itemname_zc;
	}
	public String getItemname_zt() {
		return itemname_zt;
	}
	public void setItemname_zt(String itemname_zt) {
		this.itemname_zt = itemname_zt;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	
}