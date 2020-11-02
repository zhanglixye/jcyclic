package com.kaiwait.bean.mst.entity;

import java.util.Date;

/** 
* @ClassName: Commonmst 
* @Description: commonmst的实体类
* @author mayouyi 
* @date 2017年11月14日 下午2:48:49 
*  
*/
public class Commonmst {
	private String mstcd;//主表分类编号
	private String mstname;//主表分类名称
	private String itemcd;//系统编号
	private String itmname;//项目名称-简体
	private String itemname_hk;//项目名称-繁体
	private String itemname_en;//项目名称-繁体
	private String itemname_jp;//项目名称-日语
	private String itmvalue;//辅助编号
	private String orderno;//显示顺序
	private String aidcd;//数值
	private String change_utin;//换算单位
	private Date adddate;
	private Date upddate;
	private String addusercd;
	private String updusercd;
	private Integer company_cd;
	private String del_flg;//删除状态位
	private int lock_flg;//锁
	
	public int getLock_flg() {
		return lock_flg;
	}
	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
	}
	public String getMstcd() {
		return mstcd;
	}
	public void setMstcd(String mstcd) {
		this.mstcd = mstcd;
	}
	public String getMstname() {
		return mstname;
	}
	public void setMstname(String mstname) {
		this.mstname = mstname;
	}
	public String getItemcd() {
		return itemcd;
	}
	public void setItemcd(String itemcd) {
		this.itemcd = itemcd;
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
	public String getItmvalue() {
		return itmvalue;
	}
	public void setItmvalue(String itmvalue) {
		this.itmvalue = itmvalue;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getAidcd() {
		return aidcd;
	}
	public void setAidcd(String aidcd) {
		this.aidcd = aidcd;
	}
	public String getChange_utin() {
		return change_utin;
	}
	public void setChange_utin(String change_utin) {
		this.change_utin = change_utin;
	}
	public Date getAdddate() {
		return adddate;
	}
	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getAddusercd() {
		return addusercd;
	}
	public void setAddusercd(String addusercd) {
		this.addusercd = addusercd;
	}
	public String getUpdusercd() {
		return updusercd;
	}
	public void setUpdusercd(String updusercd) {
		this.updusercd = updusercd;
	}
	public Integer getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(Integer company_cd) {
		this.company_cd = company_cd;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	
	
}