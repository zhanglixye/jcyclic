package com.kaiwait.bean.mst.io;



import java.util.List;

import com.kaiwait.bean.mst.entity.LabelMst;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class LabelInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	

	private LabelMst labelmst;
	
	private String label_id;
	
	private String label_type;
	
	private List<LabelMst> LableList;
	
	private String label_level;
	
	private String userid;
	
	private List<Role> roleList;
	
	private int lock_flg;
	

	public int getLock_flg() {
		return lock_flg;
	}

	public void setLock_flg(int lock_flg) {
		this.lock_flg = lock_flg;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLabel_level() {
		return label_level;
	}

	public void setLabel_level(String label_level) {
		this.label_level = label_level;
	}

	public LabelMst getLabelmst() {
		return labelmst;
	}

	public void setLabelmst(LabelMst labelmst) {
		this.labelmst = labelmst;
	}

	public List<LabelMst> getLableList() {
		return LableList;
	}

	public void setLableList(List<LabelMst> lableList) {
		LableList = lableList;
	}

	public String getLabel_id() {
		return label_id;
	}

	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}

	public String getLabel_type() {
		return label_type;
	}

	public void setLabel_type(String label_type) {
		this.label_type = label_type;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	
	
}