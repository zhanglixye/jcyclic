package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class Trantrn {
	
    private String job_cd;

    private String input_no;

    private String company_cd;

    private String tran_date;

    private String item_code;

    private String tran_amt;

    private String tran_name;

    private String remark;

    private String add_date;

    private String up_date;

    private String add_usercd;

    private String up_usercd;

    private String tran_status;
    
    private String itmname;
    
    private String itemname_hk;
    
    private String itemname_en;
    
    private String itemname_jp;
    
    private String itemnamehk;
    
    private String itemnameen;
    
    private String itemnamejp;
    
    private List<Lable> Trantrnlabellist;
    
    private String confirusername;
    private String  cancelUserName;
    private String confirmdate;
    private String  cancel_confirm_date;
    
    private String  updateuser;
    private String   addusername;
    private String adddate;
    private String upddate;
    
    private String cancelconfirmdate;//取消日期
    private String cancelconfirmemp;//取消者
    
    private String costfinishflg;
    
    private String upusername;
    private String addusernamecolor;
    
    private String upusernamecolor;
    private String  confirusernamecolor;
    private String  cancelUserNamecolor;
    
    private String old_input_no;
    
    private String itemname;
    private int lock_flg;
    private String new_input_no; 
    private int tranFromJpp;
    
 	public int getTranFromJpp() {
		return tranFromJpp;
	}

	public void setTranFromJpp(int tranFromJpp) {
		this.tranFromJpp = tranFromJpp;
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

	public String getConfirusernamecolor() {
		return confirusernamecolor;
	}

	public void setConfirusernamecolor(String confirusernamecolor) {
		this.confirusernamecolor = confirusernamecolor;
	}

	public String getCancelUserNamecolor() {
		return cancelUserNamecolor;
	}

	public void setCancelUserNamecolor(String cancelUserNamecolor) {
		this.cancelUserNamecolor = cancelUserNamecolor;
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

	public String getUpusername() {
		return upusername;
	}

	public void setUpusername(String upusername) {
		this.upusername = upusername;
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

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	

	public String getConfirusername() {
		return confirusername;
	}

	public void setConfirusername(String confirusername) {
		this.confirusername = confirusername;
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getConfirmdate() {
		return confirmdate;
	}

	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}

	public String getCancel_confirm_date() {
		return cancel_confirm_date;
	}

	public void setCancel_confirm_date(String cancel_confirm_date) {
		this.cancel_confirm_date = cancel_confirm_date;
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

	public String getTran_date() {
		return tran_date;
	}

	public void setTran_date(String tran_date) {
		this.tran_date = tran_date;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getTran_amt() {
		return tran_amt;
	}

	public void setTran_amt(String tran_amt) {
		this.tran_amt = tran_amt;
	}

	public String getTran_name() {
		return tran_name;
	}

	public void setTran_name(String tran_name) {
		this.tran_name = tran_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getUp_date() {
		return up_date;
	}

	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}

	public String getAdd_usercd() {
		return add_usercd;
	}

	public void setAdd_usercd(String add_usercd) {
		this.add_usercd = add_usercd;
	}

	public String getUp_usercd() {
		return up_usercd;
	}

	public void setUp_usercd(String up_usercd) {
		this.up_usercd = up_usercd;
	}

	public String getTran_status() {
		return tran_status;
	}

	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
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

	public List<Lable> getTrantrnlabellist() {
		return Trantrnlabellist;
	}

	public void setTrantrnlabellist(List<Lable> trantrnlabellist) {
		Trantrnlabellist = trantrnlabellist;
	}

	public String getCostfinishflg() {
		return costfinishflg;
	}

	public void setCostfinishflg(String costfinishflg) {
		this.costfinishflg = costfinishflg;
	}
    

}
