package com.kaiwait.bean.mst.io;


import java.util.List;

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.entity.Taxtype;
import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class ClmstAddInput extends BaseInputBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5085368117650161941L;


	private Clmst clmst;
	//社員番号
	private String user_cd;
	//会社名称(略称
	private String company_name;
	//氏名
	private String user_name;
	//业务级别
	private String level;	
	private int cldivcd;	
	//税者纳种类list
	private List<CommonmstVo> taxtypeList;
	//共通表中语言list
	private List<ClmstAddInput> wedgemembersleft;
	//共通表中部门list
	private List<ClmstAddInput> wedgemembersright;
	//共通表中部门list
	private List<ClmstAddInput> taxtypeListtwo;
	
	private List<Taxtype> taxtypeListthree;
	//取引先修改时返回参数
	private List<Clmst> clmstList;
	//添加者历史列表
	private List<User> userHistoryByInsert;
	//更新着历史列表
	private List<User> userHistoryByUp;
	//权限
	private List<Role> roleList;
	
	private String account_cd;

	
	private String divnm;
	
	private String divnm_en;
	
	private String divname_full;

	private String divadd;
	
	private String divadd1;
	
	private String divadd2;

	private String div_tel;
	
	private String contacts_name;
	
	private String contacts_name_en;
	
	private String contacts_name_jp;
	
	private String contacts_name_hk;
	
	private String self_company_name;
	
	private String self_company_name_en;
	
	private String self_company_name_jp;
	
	private String self_company_name_hk;
	
	private String contacts_address;
	
	private String contacts_address_en;
	
	private String contacts_address_jp;
	
	private String contacts_address_hk;
	
	private String tel_number;
	
	private String tel_number_en;
	
	private String tel_number_jp;
	
	private String tel_number_hk;
	
	private int Date_auto_setting;
	
	private int auto_month;
	
	private int auto_day;

	private String contra_contacts_name;
	
	private String contra_contacts_name_en;
	
	private String contra_contacts_name_jp;
	
	private String contra_contacts_name_hk;
	
	private String contra_self_company_name;
	
	private String contra_self_company_name_en;
	
	private String contra_self_company_name_jp;
	
	private String contra_self_company_name_hk;
	
	private String contra_address;
	
	private String contra_address_en;
	
	private String contra_address_jp;
	
	private String contra_address_hk;
	
	private String contra_address1;
	
	private String contra_address_en1;
	
	private String contra_address_jp1;
	
	private String contra_address_hk1;
	
	private String contra_address2;
	
	private String contra_address_en2;
	
	private String contra_address_jp2;
	
	private String contra_address_hk2;
	
	private String contra_tel;
	
	private String contra_tel_en;
	
	private String contra_tel_jp;
	
	private String contra_tel_hk;
	
	private int pay_auto_setting;

	private int pay_Date_flg;

	private int pay_auto_month;

	private int pay_auto_day;

	private String bank_into_one;

	private String bank_info_two;

	private String note;

	private String pay_info;

	private String other_note;
	
	private String other_note_en;
	
	public String getOther_note_en() {
		return other_note_en;
	}

	public void setOther_note_en(String other_note_en) {
		this.other_note_en = other_note_en;
	}

	public String getOther_note_jp() {
		return other_note_jp;
	}

	public void setOther_note_jp(String other_note_jp) {
		this.other_note_jp = other_note_jp;
	}

	public String getOther_note_hk() {
		return other_note_hk;
	}

	public void setOther_note_hk(String other_note_hk) {
		this.other_note_hk = other_note_hk;
	}

	private String other_note_jp;
	
	private String other_note_hk;

	private String other_note1;

	private String other_note2;
		
	private String addDate;
	
	private String upDate;

	private String addusercd;

	private String updusercd;

	private String client_flg;

	private String contra_flg;

	private String pay_flg;

	private String hdy_flg;

	private int company_cd;

	private int del_flg;
	
	private int lock_flg;
	
	private String fileData;
	private String member_id;
	
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public int getLock_flg() {
		return lock_flg;
	}

	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
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

	public String getDivadd() {
		return divadd;
	}

	public void setDivadd(String divadd) {
		this.divadd = divadd;
	}

	public String getDivadd1() {
		return divadd1;
	}

	public void setDivadd1(String divadd1) {
		this.divadd1 = divadd1;
	}

	public String getDivadd2() {
		return divadd2;
	}

	public void setDivadd2(String divadd2) {
		this.divadd2 = divadd2;
	}

	public String getDiv_tel() {
		return div_tel;
	}

	public void setDiv_tel(String div_tel) {
		this.div_tel = div_tel;
	}

	public String getContacts_name() {
		return contacts_name;
	}

	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}

	public String getContacts_name_en() {
		return contacts_name_en;
	}

	public void setContacts_name_en(String contacts_name_en) {
		this.contacts_name_en = contacts_name_en;
	}

	public String getContacts_name_jp() {
		return contacts_name_jp;
	}

	public void setContacts_name_jp(String contacts_name_jp) {
		this.contacts_name_jp = contacts_name_jp;
	}

	public String getContacts_name_hk() {
		return contacts_name_hk;
	}

	public void setContacts_name_hk(String contacts_name_hk) {
		this.contacts_name_hk = contacts_name_hk;
	}

	public String getSelf_company_name() {
		return self_company_name;
	}

	public void setSelf_company_name(String self_company_name) {
		this.self_company_name = self_company_name;
	}

	public String getSelf_company_name_en() {
		return self_company_name_en;
	}

	public void setSelf_company_name_en(String self_company_name_en) {
		this.self_company_name_en = self_company_name_en;
	}

	public String getSelf_company_name_jp() {
		return self_company_name_jp;
	}

	public void setSelf_company_name_jp(String self_company_name_jp) {
		this.self_company_name_jp = self_company_name_jp;
	}

	public String getSelf_company_name_hk() {
		return self_company_name_hk;
	}

	public void setSelf_company_name_hk(String self_company_name_hk) {
		this.self_company_name_hk = self_company_name_hk;
	}

	public String getContacts_address() {
		return contacts_address;
	}

	public void setContacts_address(String contacts_address) {
		this.contacts_address = contacts_address;
	}

	public String getContacts_address_en() {
		return contacts_address_en;
	}

	public void setContacts_address_en(String contacts_address_en) {
		this.contacts_address_en = contacts_address_en;
	}

	public String getContacts_address_jp() {
		return contacts_address_jp;
	}

	public void setContacts_address_jp(String contacts_address_jp) {
		this.contacts_address_jp = contacts_address_jp;
	}

	public String getContacts_address_hk() {
		return contacts_address_hk;
	}

	public void setContacts_address_hk(String contacts_address_hk) {
		this.contacts_address_hk = contacts_address_hk;
	}

	public String getTel_number() {
		return tel_number;
	}

	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}

	public String getTel_number_en() {
		return tel_number_en;
	}

	public void setTel_number_en(String tel_number_en) {
		this.tel_number_en = tel_number_en;
	}

	public String getTel_number_jp() {
		return tel_number_jp;
	}

	public void setTel_number_jp(String tel_number_jp) {
		this.tel_number_jp = tel_number_jp;
	}

	public String getTel_number_hk() {
		return tel_number_hk;
	}

	public void setTel_number_hk(String tel_number_hk) {
		this.tel_number_hk = tel_number_hk;
	}

	public int getDate_auto_setting() {
		return Date_auto_setting;
	}

	public void setDate_auto_setting(int date_auto_setting) {
		Date_auto_setting = date_auto_setting;
	}

	public int getAuto_month() {
		return auto_month;
	}

	public void setAuto_month(int auto_month) {
		this.auto_month = auto_month;
	}

	public int getAuto_day() {
		return auto_day;
	}

	public void setAuto_day(int auto_day) {
		this.auto_day = auto_day;
	}

	public String getContra_contacts_name() {
		return contra_contacts_name;
	}

	public void setContra_contacts_name(String contra_contacts_name) {
		this.contra_contacts_name = contra_contacts_name;
	}

	public String getContra_contacts_name_en() {
		return contra_contacts_name_en;
	}

	public void setContra_contacts_name_en(String contra_contacts_name_en) {
		this.contra_contacts_name_en = contra_contacts_name_en;
	}

	public String getContra_contacts_name_jp() {
		return contra_contacts_name_jp;
	}

	public void setContra_contacts_name_jp(String contra_contacts_name_jp) {
		this.contra_contacts_name_jp = contra_contacts_name_jp;
	}

	public String getContra_contacts_name_hk() {
		return contra_contacts_name_hk;
	}

	public void setContra_contacts_name_hk(String contra_contacts_name_hk) {
		this.contra_contacts_name_hk = contra_contacts_name_hk;
	}

	public String getContra_self_company_name() {
		return contra_self_company_name;
	}

	public void setContra_self_company_name(String contra_self_company_name) {
		this.contra_self_company_name = contra_self_company_name;
	}

	public String getContra_self_company_name_en() {
		return contra_self_company_name_en;
	}

	public void setContra_self_company_name_en(String contra_self_company_name_en) {
		this.contra_self_company_name_en = contra_self_company_name_en;
	}

	public String getContra_self_company_name_jp() {
		return contra_self_company_name_jp;
	}

	public void setContra_self_company_name_jp(String contra_self_company_name_jp) {
		this.contra_self_company_name_jp = contra_self_company_name_jp;
	}

	public String getContra_self_company_name_hk() {
		return contra_self_company_name_hk;
	}

	public void setContra_self_company_name_hk(String contra_self_company_name_hk) {
		this.contra_self_company_name_hk = contra_self_company_name_hk;
	}

	public String getContra_address() {
		return contra_address;
	}

	public void setContra_address(String contra_address) {
		this.contra_address = contra_address;
	}

	public String getContra_address_en() {
		return contra_address_en;
	}

	public void setContra_address_en(String contra_address_en) {
		this.contra_address_en = contra_address_en;
	}

	public String getContra_address_jp() {
		return contra_address_jp;
	}

	public void setContra_address_jp(String contra_address_jp) {
		this.contra_address_jp = contra_address_jp;
	}

	public String getContra_address_hk() {
		return contra_address_hk;
	}

	public void setContra_address_hk(String contra_address_hk) {
		this.contra_address_hk = contra_address_hk;
	}

	public String getContra_address1() {
		return contra_address1;
	}

	public void setContra_address1(String contra_address1) {
		this.contra_address1 = contra_address1;
	}

	public String getContra_address_en1() {
		return contra_address_en1;
	}

	public void setContra_address_en1(String contra_address_en1) {
		this.contra_address_en1 = contra_address_en1;
	}

	public String getContra_address_jp1() {
		return contra_address_jp1;
	}

	public void setContra_address_jp1(String contra_address_jp1) {
		this.contra_address_jp1 = contra_address_jp1;
	}

	public String getContra_address_hk1() {
		return contra_address_hk1;
	}

	public void setContra_address_hk1(String contra_address_hk1) {
		this.contra_address_hk1 = contra_address_hk1;
	}

	public String getContra_address2() {
		return contra_address2;
	}

	public void setContra_address2(String contra_address2) {
		this.contra_address2 = contra_address2;
	}

	public String getContra_address_en2() {
		return contra_address_en2;
	}

	public void setContra_address_en2(String contra_address_en2) {
		this.contra_address_en2 = contra_address_en2;
	}

	public String getContra_address_jp2() {
		return contra_address_jp2;
	}

	public void setContra_address_jp2(String contra_address_jp2) {
		this.contra_address_jp2 = contra_address_jp2;
	}

	public String getContra_address_hk2() {
		return contra_address_hk2;
	}

	public void setContra_address_hk2(String contra_address_hk2) {
		this.contra_address_hk2 = contra_address_hk2;
	}

	public String getContra_tel() {
		return contra_tel;
	}

	public void setContra_tel(String contra_tel) {
		this.contra_tel = contra_tel;
	}

	public String getContra_tel_en() {
		return contra_tel_en;
	}

	public void setContra_tel_en(String contra_tel_en) {
		this.contra_tel_en = contra_tel_en;
	}

	public String getContra_tel_jp() {
		return contra_tel_jp;
	}

	public void setContra_tel_jp(String contra_tel_jp) {
		this.contra_tel_jp = contra_tel_jp;
	}

	public String getContra_tel_hk() {
		return contra_tel_hk;
	}

	public void setContra_tel_hk(String contra_tel_hk) {
		this.contra_tel_hk = contra_tel_hk;
	}

	public int getPay_auto_setting() {
		return pay_auto_setting;
	}

	public void setPay_auto_setting(int pay_auto_setting) {
		this.pay_auto_setting = pay_auto_setting;
	}

	public int getPay_Date_flg() {
		return pay_Date_flg;
	}

	public void setPay_Date_flg(int pay_Date_flg) {
		this.pay_Date_flg = pay_Date_flg;
	}

	public int getPay_auto_month() {
		return pay_auto_month;
	}

	public void setPay_auto_month(int pay_auto_month) {
		this.pay_auto_month = pay_auto_month;
	}

	public int getPay_auto_day() {
		return pay_auto_day;
	}

	public void setPay_auto_day(int pay_auto_day) {
		this.pay_auto_day = pay_auto_day;
	}

	public String getBank_into_one() {
		return bank_into_one;
	}

	public void setBank_into_one(String bank_into_one) {
		this.bank_into_one = bank_into_one;
	}

	public String getBank_info_two() {
		return bank_info_two;
	}

	public void setBank_info_two(String bank_info_two) {
		this.bank_info_two = bank_info_two;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPay_info() {
		return pay_info;
	}

	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}

	public String getOther_note() {
		return other_note;
	}

	public void setOther_note(String other_note) {
		this.other_note = other_note;
	}

	public String getOther_note1() {
		return other_note1;
	}

	public void setOther_note1(String other_note1) {
		this.other_note1 = other_note1;
	}

	public String getOther_note2() {
		return other_note2;
	}

	public void setOther_note2(String other_note2) {
		this.other_note2 = other_note2;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getUpDate() {
		return upDate;
	}

	public void setUpDate(String upDate) {
		this.upDate = upDate;
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

	public String getClient_flg() {
		return client_flg;
	}

	public void setClient_flg(String client_flg) {
		this.client_flg = client_flg;
	}

	public String getContra_flg() {
		return contra_flg;
	}

	public void setContra_flg(String contra_flg) {
		this.contra_flg = contra_flg;
	}

	public String getPay_flg() {
		return pay_flg;
	}

	public void setPay_flg(String pay_flg) {
		this.pay_flg = pay_flg;
	}

	public String getHdy_flg() {
		return hdy_flg;
	}

	public void setHdy_flg(String hdy_flg) {
		this.hdy_flg = hdy_flg;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Clmst getClmst() {
		return clmst;
	}

	public void setClmst(Clmst clmst) {
		this.clmst = clmst;
	}

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	public List<ClmstAddInput> getWedgemembersleft() {
		return wedgemembersleft;
	}

	public void setWedgemembersleft(List<ClmstAddInput> wedgemembersleft) {
		this.wedgemembersleft = wedgemembersleft;
	}

	public List<ClmstAddInput> getWedgemembersright() {
		return wedgemembersright;
	}

	public void setWedgemembersright(List<ClmstAddInput> wedgemembersright) {
		this.wedgemembersright = wedgemembersright;
	}
	
	public int getCldivcd() {
		return cldivcd;
	}

	public void setCldivcd(int cldivcd) {
		this.cldivcd = cldivcd;
	}


	public List<ClmstAddInput> getTaxtypeListtwo() {
		return taxtypeListtwo;
	}

	public void setTaxtypeListtwo(List<ClmstAddInput> taxtypeListtwo) {
		this.taxtypeListtwo = taxtypeListtwo;
	}

	public List<CommonmstVo> getTaxtypeList() {
		return taxtypeList;
	}

	public void setTaxtypeList(List<CommonmstVo> taxtypeList) {
		this.taxtypeList = taxtypeList;
	}

	public List<Taxtype> getTaxtypeListthree() {
		return taxtypeListthree;
	}

	public void setTaxtypeListthree(List<Taxtype> taxtypeListthree) {
		this.taxtypeListthree = taxtypeListthree;
	}
	public List<Clmst> getClmstList() {
		return clmstList;
	}

	public void setClmstList(List<Clmst> clmstList) {
		this.clmstList = clmstList;
	}
	public List<User> getUserHistoryByInsert() {
		return userHistoryByInsert;
	}

	public void setUserHistoryByInsert(List<User> userHistoryByInsert) {
		this.userHistoryByInsert = userHistoryByInsert;
	}

	public List<User> getUserHistoryByUp() {
		return userHistoryByUp;
	}

	public void setUserHistoryByUp(List<User> userHistoryByUp) {
		this.userHistoryByUp = userHistoryByUp;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getAccount_cd() {
		return account_cd;
	}

	public void setAccount_cd(String account_cd) {
		this.account_cd = account_cd;
	}
	
}
