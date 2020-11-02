package com.kaiwait.bean.mst.io;



import java.util.List;

import com.kaiwait.bean.mst.entity.Msgtrn;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class MsgtrnInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	

	private Msgtrn Msgtrn;
	
	private String company_cd;
	
	private String manageflag;
	
	private String companylevel;
	 
	//留言板
	private List<Msgtrn> msgtrnList;
	private List<Role> roleList;
private String num;
	
	//0:个人留言1：系统消息
	private String msg_level;
	
	private String msg_title;
	
	private String msg_text;
	
	private String adduser;
	
	private String upuser;
	
	private String adddate;
	
	private String update;
	
	private String del_flg;
	
	private String dateadd;
	
	private String addtime;
	
	private int id;
	
	private String roletype;
	
	private String msglevel;
	
	private String msgtitle;
	
	private String msgtext;
	private String companycd;
	private String delflg;
	private int lock_flg;
	

	public int getLock_flg() {
		return lock_flg;
	}

	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
	}
	
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMsg_level() {
		return msg_level;
	}
	public void setMsg_level(String msg_level) {
		this.msg_level = msg_level;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getMsg_text() {
		return msg_text;
	}
	public void setMsg_text(String msg_text) {
		this.msg_text = msg_text;
	}
	public String getAdduser() {
		return adduser;
	}
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}
	public String getUpuser() {
		return upuser;
	}
	public void setUpuser(String upuser) {
		this.upuser = upuser;
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
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getDateadd() {
		return dateadd;
	}
	public void setDateadd(String dateadd) {
		this.dateadd = dateadd;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	public String getMsglevel() {
		return msglevel;
	}
	public void setMsglevel(String msglevel) {
		this.msglevel = msglevel;
	}
	public String getMsgtitle() {
		return msgtitle;
	}
	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}
	public String getMsgtext() {
		return msgtext;
	}
	public void setMsgtext(String msgtext) {
		this.msgtext = msgtext;
	}
	public String getCompanycd() {
		return companycd;
	}
	public void setCompanycd(String companycd) {
		this.companycd = companycd;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Msgtrn getMsgtrn() {
		return Msgtrn;
	}
	public void setMsgtrn(Msgtrn msgtrn) {
		Msgtrn = msgtrn;
	}
	public List<Msgtrn> getMsgtrnList() {
		return msgtrnList;
	}
	public void setMsgtrnList(List<Msgtrn> msgtrnList) {
		this.msgtrnList = msgtrnList;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String getManageflag() {
		return manageflag;
	}
	public void setManageflag(String manageflag) {
		this.manageflag = manageflag;
	}
	public String getCompanylevel() {
		return companylevel;
	}
	public void setCompanylevel(String companylevel) {
		this.companylevel = companylevel;
	}
	
}