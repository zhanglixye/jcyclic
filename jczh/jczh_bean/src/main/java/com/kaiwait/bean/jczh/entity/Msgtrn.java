package com.kaiwait.bean.jczh.entity;


/**
 * 
* @ClassName: Msgtrn  
* @Description: TODO(留言板实体类)  
* @author fqq  
* @date 2018年6月25日  
*
 */
public class Msgtrn {
	

	private String num;
	
	//0:个人留言1：系统消息
	private String msg_level;
	
	private String msg_title;
	
	private String msg_text;
	
	private String company_cd;
	
	private String adduser;
	
	private String upuser;
	
	private String adddate;
	
	private String update;
	
	private String del_flg;
	
	private String dateadd;
	
	private String addtime;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
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

	public String getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
}