package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class Lendtrn {
	
    private String job_cd;
    
    private String input_no;
    
    private String company_cd;
    
    private String lend_date;
    
    private String item_code;
    
    private String lend_name;
    
    private String lend_user;
    
    private String isdeduction;
    
    private String remark;
    
    private Double lend_amt;
    
    private Double lend_foreign_amt;
    
    private Double lend_vat_amt;
    
    private Double lend_pay_amt;
    
    private String ishave;
    
    private Double lend_cure_code;
    
    private String lend_use_date;
    
    private String lend_refer;
    
    private String lend_foreign_type;
    
    private String adddate;
    
    private String update;
    
    private String addusercd;
    
    private String upusercd;
    
    private String status;
    
    private String del_flg;
    private String username;
    private String adduser;
    private String upduser;
    private String adminuserlend;
    private String canceluserlend;
    private String cancel_confirm_date;
    private String confirmdate;
    
    private String cancelconfirmdate;//立替取消日期
    private String cancelconfirmemp;//立替取消者
    
    private String blflg;
    
    private String costfinishflg;
    
    private String vat_change_flg;
    private String memberid;
    private String colorv;
    private String departcd;
    
    private String lendusedate;
    private String departname;
    private String departnamehk;
    private String departnameen;
    private String departnamejp;
    
    private String usercd;
    
    private String foreignlen;
    private String payforeigntype;
    
    private String pdfflagpro;
    private String pdfflagcri;
    private String itmname;
    private String itemname_hk;
    private String itemname_en;
    private String itemname_jp;
    private String itemnamehk;
    private String itemnameen;
    private String itemnamejp;
    private String addusername;
    
    private String upusername;
    private String addusernamecolor;
    
    private String upusernamecolor;
    
    private String adminuserlendcolor;
    private String canceluserlenddcolor;
    private String itemname;
    
    private int lock_flg;
    private String new_input_no;
    private String old_input_no;
    
    private String vat_rate; 
    
    private String  payforeigntypeen;
    
    private String  payforeigntypejp;
    private int lendFromJpp;
    
    
    
    public int getLendFromJpp() {
		return lendFromJpp;
	}

	public void setLendFromJpp(int lendFromJpp) {
		this.lendFromJpp = lendFromJpp;
	}

	public String getPayforeigntypeen() {
		return payforeigntypeen;
	}

	public void setPayforeigntypeen(String payforeigntypeen) {
		this.payforeigntypeen = payforeigntypeen;
	}

	public String getPayforeigntypejp() {
		return payforeigntypejp;
	}

	public void setPayforeigntypejp(String payforeigntypejp) {
		this.payforeigntypejp = payforeigntypejp;
	}

	public String getPayforeigntypehk() {
		return payforeigntypehk;
	}

	public void setPayforeigntypehk(String payforeigntypehk) {
		this.payforeigntypehk = payforeigntypehk;
	}

	private String  payforeigntypehk;
	public String getVat_rate() {
		return vat_rate;
	}

	public void setVat_rate(String vat_rate) {
		this.vat_rate = vat_rate;
	}

	public String getNew_input_no() {
		return new_input_no;
	}

	public void setNew_input_no(String new_input_no) {
		this.new_input_no = new_input_no;
	}

	public String getOld_input_no() {
		return old_input_no;
	}

	public void setOld_input_no(String old_input_no) {
		this.old_input_no = old_input_no;
	}

	public int getLock_flg() {
		return lock_flg;
	}

	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
	}
    public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemnamehk() {
		return itemnamehk;
	}

	public void setItemnamehk(String itemnamehk) {
		this.itemnamehk = itemnamehk;
	}

	public String getItemnameen() {
		return itemnameen;
	}

	public void setItemnameen(String itemnameen) {
		this.itemnameen = itemnameen;
	}

	public String getItemnamejp() {
		return itemnamejp;
	}

	public void setItemnamejp(String itemnamejp) {
		this.itemnamejp = itemnamejp;
	}

	public String getAdminuserlendcolor() {
		return adminuserlendcolor;
	}

	public void setAdminuserlendcolor(String adminuserlendcolor) {
		this.adminuserlendcolor = adminuserlendcolor;
	}

	public String getCanceluserlenddcolor() {
		return canceluserlenddcolor;
	}

	public void setCanceluserlenddcolor(String canceluserlenddcolor) {
		this.canceluserlenddcolor = canceluserlenddcolor;
	}

	public String getAddusernamecolor() {
		return addusernamecolor;
	}

	public void setAddusernamecolor(String addusernamecolor) {
		this.addusernamecolor = addusernamecolor;
	}

	public String getUpusernamecolor() {
		return upusernamecolor;
	}

	public void setUpusernamecolor(String upusernamecolor) {
		this.upusernamecolor = upusernamecolor;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	public String getUpusername() {
		return upusername;
	}

	public void setUpusername(String upusername) {
		this.upusername = upusername;
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

	public String getPdfflagpro() {
		return pdfflagpro;
	}

	public void setPdfflagpro(String pdfflagpro) {
		this.pdfflagpro = pdfflagpro;
	}

	public String getPdfflagcri() {
		return pdfflagcri;
	}

	public void setPdfflagcri(String pdfflagcri) {
		this.pdfflagcri = pdfflagcri;
	}

	public String getPayforeigntype() {
		return payforeigntype;
	}

	public void setPayforeigntype(String payforeigntype) {
		this.payforeigntype = payforeigntype;
	}

	public String getForeignlen() {
		return foreignlen;
	}

	public void setForeignlen(String foreignlen) {
		this.foreignlen = foreignlen;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getDepartnamehk() {
		return departnamehk;
	}

	public void setDepartnamehk(String departnamehk) {
		this.departnamehk = departnamehk;
	}

	public String getDepartnameen() {
		return departnameen;
	}

	public void setDepartnameen(String departnameen) {
		this.departnameen = departnameen;
	}

	public String getDepartnamejp() {
		return departnamejp;
	}

	public void setDepartnamejp(String departnamejp) {
		this.departnamejp = departnamejp;
	}

	public String getUsercd() {
		return usercd;
	}

	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}

	public String getLendusedate() {
		return lendusedate;
	}

	public void setLendusedate(String lendusedate) {
		this.lendusedate = lendusedate;
	}

	public String getDepartcd() {
		return departcd;
	}

	public void setDepartcd(String departcd) {
		this.departcd = departcd;
	}

	public String getColorv() {
		return colorv;
	}

	public void setColorv(String colorv) {
		this.colorv = colorv;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getVat_change_flg() {
		return vat_change_flg;
	}

	public void setVat_change_flg(String vat_change_flg) {
		this.vat_change_flg = vat_change_flg;
	}

	public String getBlflg() {
		return blflg;
	}

	public void setBlflg(String blflg) {
		this.blflg = blflg;
	}

	public String getCancelconfirmdate() {
		return cancelconfirmdate;
	}

	public void setCancelconfirmdate(String cancelconfirmdate) {
		this.cancelconfirmdate = cancelconfirmdate;
	}

	public String getCancelconfirmemp() {
		return cancelconfirmemp;
	}

	public void setCancelconfirmemp(String cancelconfirmemp) {
		this.cancelconfirmemp = cancelconfirmemp;
	}

	public String getCancel_confirm_date() {
		return cancel_confirm_date;
	}

	public void setCancel_confirm_date(String cancel_confirm_date) {
		this.cancel_confirm_date = cancel_confirm_date;
	}


	public String getConfirmdate() {
		return confirmdate;
	}

	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
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

	public String getAdminuserlend() {
		return adminuserlend;
	}

	public void setAdminuserlend(String adminuserlend) {
		this.adminuserlend = adminuserlend;
	}

	public String getCanceluserlend() {
		return canceluserlend;
	}

	public void setCanceluserlend(String canceluserlend) {
		this.canceluserlend = canceluserlend;
	}

	private List<Lable> Lendlabellist;

    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJob_cd() {
		return job_cd;
	}

	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}

	public String getInput_no() {
		return input_no;
	}

	public void setInput_no(String input_no) {
		this.input_no = input_no;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getLend_date() {
		return lend_date;
	}

	public void setLend_date(String lend_date) {
		this.lend_date = lend_date;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getLend_name() {
		return lend_name;
	}

	public void setLend_name(String lend_name) {
		this.lend_name = lend_name;
	}

	public String getLend_user() {
		return lend_user;
	}

	public void setLend_user(String lend_user) {
		this.lend_user = lend_user;
	}

	public String getIsdeduction() {
		return isdeduction;
	}

	public void setIsdeduction(String isdeduction) {
		this.isdeduction = isdeduction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getLend_amt() {
		return lend_amt;
	}

	public void setLend_amt(Double lend_amt) {
		this.lend_amt = lend_amt;
	}

	public Double getLend_foreign_amt() {
		return lend_foreign_amt;
	}

	public void setLend_foreign_amt(Double lend_foreign_amt) {
		this.lend_foreign_amt = lend_foreign_amt;
	}

	public Double getLend_vat_amt() {
		return lend_vat_amt;
	}

	public void setLend_vat_amt(Double lend_vat_amt) {
		this.lend_vat_amt = lend_vat_amt;
	}

	public Double getLend_pay_amt() {
		return lend_pay_amt;
	}

	public void setLend_pay_amt(Double lend_pay_amt) {
		this.lend_pay_amt = lend_pay_amt;
	}

	public String getIshave() {
		return ishave;
	}

	public void setIshave(String ishave) {
		this.ishave = ishave;
	}

	public Double getLend_cure_code() {
		return lend_cure_code;
	}

	public void setLend_cure_code(Double lend_cure_code) {
		this.lend_cure_code = lend_cure_code;
	}

	public String getLend_use_date() {
		return lend_use_date;
	}

	public void setLend_use_date(String lend_use_date) {
		this.lend_use_date = lend_use_date;
	}

	public String getLend_refer() {
		return lend_refer;
	}

	public void setLend_refer(String lend_refer) {
		this.lend_refer = lend_refer;
	}

	public String getLend_foreign_type() {
		return lend_foreign_type;
	}

	public void setLend_foreign_type(String lend_foreign_type) {
		this.lend_foreign_type = lend_foreign_type;
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

	public List<Lable> getLendlabellist() {
		return Lendlabellist;
	}

	public void setLendlabellist(List<Lable> lendlabellist) {
		Lendlabellist = lendlabellist;
	}

	public String getCostfinishflg() {
		return costfinishflg;
	}

	public void setCostfinishflg(String costfinishflg) {
		this.costfinishflg = costfinishflg;
	}

	

}
