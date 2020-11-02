package com.kaiwait.bean.mst.entity;


/**
 * 
 * @ClassName: ListColumn
 * @Description: TODO(标签实体类)
 * @author fqq
 * @date 2018年6月25日
 *
 */

public class ListColumn {
	private String list_column_id;
	// 0:JOB;1:cost
	private String level;
	// 每一块代表的值
	private String column_value;
	// 显示名
	private String column_show_name;
	// 显示名
	private String column_show_name_en;
	// 显示名
	private String column_show_name_jp;
	// 显示名
	private String column_show_name_hk;
	// 默认宽度
	private String default_wide;
	// 默认宽度
	private String onflag;
	// 每页行数
	private String show_numbers;
	
	private String ison;

	public String getIson() {
		return ison;
	}

	public void setIson(String ison) {
		this.ison = ison;
	}

	public String getList_column_id() {
		return list_column_id;
	}

	public void setList_column_id(String list_column_id) {
		this.list_column_id = list_column_id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getColumn_value() {
		return column_value;
	}
	public void setColumn_value(String column_value) {
		this.column_value = column_value;
	}
	public String getColumn_show_name() {
		return column_show_name;
	}

	public void setColumn_show_name(String column_show_name) {
		this.column_show_name = column_show_name;
	}

	public String getDefault_wide() {
		return default_wide;
	}

	public void setDefault_wide(String default_wide) {
		this.default_wide = default_wide;
	}

	public String getOnflag() {
		return onflag;
	}

	public void setOnflag(String onflag) {
		this.onflag = onflag;
	}

	public String getColumn_show_name_en() {
		return column_show_name_en;
	}

	public void setColumn_show_name_en(String column_show_name_en) {
		this.column_show_name_en = column_show_name_en;
	}

	public String getColumn_show_name_jp() {
		return column_show_name_jp;
	}

	public void setColumn_show_name_jp(String column_show_name_jp) {
		this.column_show_name_jp = column_show_name_jp;
	}

	public String getColumn_show_name_hk() {
		return column_show_name_hk;
	}
	public void setColumn_show_name_hk(String column_show_name_hk) {
		this.column_show_name_hk = column_show_name_hk;
	}

	public String getShow_numbers() {
		return show_numbers;
	}

	public void setShow_numbers(String show_numbers) {
		this.show_numbers = show_numbers;
	}

}
