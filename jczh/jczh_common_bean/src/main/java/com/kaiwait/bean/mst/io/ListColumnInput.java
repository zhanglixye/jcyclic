package com.kaiwait.bean.mst.io;

import java.util.List;

import com.kaiwait.bean.mst.entity.ListColumn;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class ListColumnInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	
	private ListColumn ListColumn;
	
	private List<ListColumn> ListColumnList;
	
	private List<ListColumn> ListColumnListCost;
	
	private String column_id;
	
	private String column_name;
	
	private String onflag;
	
	private String column_width;
	
	private String column_order;
	
	private String level;
	
	private String show_numbers;
	private String addusercd;
	
	private String updusercd;
	
	private String adddate;
	
	private String upddate;
	
	
	
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
	//共通表中部门list
	private List<ListColumnInput> listcolumninput;
	
	public ListColumn getListColumn() {
		return ListColumn;
	}

	public void setListColumn(ListColumn listColumn) {
		ListColumn = listColumn;
	}

	public List<ListColumn> getListColumnList() {
		return ListColumnList;
	}

	public void setListColumnList(List<ListColumn> listColumnList) {
		ListColumnList = listColumnList;
	}

	public String getColumn_id() {
		return column_id;
	}

	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getOnflag() {
		return onflag;
	}

	public void setOnflag(String onflag) {
		this.onflag = onflag;
	}

	public List<ListColumnInput> getListcolumninput() {
		return listcolumninput;
	}

	public void setListcolumninput(List<ListColumnInput> listcolumninput) {
		this.listcolumninput = listcolumninput;
	}

	public String getColumn_width() {
		return column_width;
	}

	public void setColumn_width(String column_width) {
		this.column_width = column_width;
	}

	public String getColumn_order() {
		return column_order;
	}

	public void setColumn_order(String column_order) {
		this.column_order = column_order;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getShow_numbers() {
		return show_numbers;
	}

	public void setShow_numbers(String show_numbers) {
		this.show_numbers = show_numbers;
	}

	public List<ListColumn> getListColumnListCost() {
		return ListColumnListCost;
	}

	public void setListColumnListCost(List<ListColumn> listColumnListCost) {
		ListColumnListCost = listColumnListCost;
	}

	
	
	
}