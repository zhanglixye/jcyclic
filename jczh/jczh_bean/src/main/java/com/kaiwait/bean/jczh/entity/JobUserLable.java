package com.kaiwait.bean.jczh.entity;


public class JobUserLable {
	private String job_cd;
	private String add_date;
	private String upd_date;
	private Integer company_cd;
    private String usercd ;
    private String level_flg;
    private String juser_del_flg;
    private String user_name;
    private String itmname;
    private String itemname_jp;
    private String itemname_en;
    private String itemname_hk;
    private String depart_cd;
    private String usercolor;
    private String memberid;
    
   public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
public String getUsercolor() {
		return usercolor;
	}
	public void setUsercolor(String usercolor) {
		this.usercolor = usercolor;
	}
public String getItmname() {
		return itmname;
	}
	public void setItmname(String itmname) {
		this.itmname = itmname;
	}
	public String getItemname_jp() {
		return itemname_jp;
	}
	public void setItemname_jp(String itemname_jp) {
		this.itemname_jp = itemname_jp;
	}
	public String getItemname_en() {
		return itemname_en;
	}
	public void setItemname_en(String itemname_en) {
		this.itemname_en = itemname_en;
	}
	public String getItemname_hk() {
		return itemname_hk;
	}
	public void setItemname_hk(String itemname_hk) {
		this.itemname_hk = itemname_hk;
	}
	public String getDepart_cd() {
		return depart_cd;
	}
	public void setDepart_cd(String depart_cd) {
		this.depart_cd = depart_cd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	//JOB登録者コード
    private String add_user_cd;
    //JOB更新者コード
    private String upd_user_cd;
    public String getJob_cd() {
		return job_cd;
	}
	public void setJob_cd(String job_cd) {
		this.job_cd = job_cd;
	}

	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getUpd_date() {
		return upd_date;
	}
	public void setUpd_date(String upd_date) {
		this.upd_date = upd_date;
	}
	public Integer getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(Integer company_cd) {
		this.company_cd = company_cd;
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
	public String getJuser_del_flg() {
		return juser_del_flg;
	}
	public void setJuser_del_flg(String juser_del_flg) {
		this.juser_del_flg = juser_del_flg;
	}
	public String getUsercd() {
		return usercd;
	}
	public void setUser_cd(String usercd) {
		this.usercd = usercd;
	}
	public String getLevel_flg() {
		return level_flg;
	}
	public void setLevel_flg(String level_flg) {
		this.level_flg = level_flg;
	}
	
}
