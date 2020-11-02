package com.kaiwait.bean.jczh.vo;

import java.util.List;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.Role;

public class JobLandVo {
  private List<CommonmstVo> list_comst;//共通下拉
  private List<SalemstVo> list_sale;
  private List<Lable> list_lable;//标签列表
  private List<CommonmstVo> list_tax;//卖上增值税端数flg 0052 
  private List<CommonmstVo> list_foreign_tax;//外货端数flg，0051
  private List<CommonmstVo> accountyear;
  private String costRate;//仕入增值税率
  private List<Cost> list_cost;
  //job更新时查询操作用户的权限
  private List<Role> nodeList;
  
  private String  jobIsEditMDFlg;
  private int fromJpp;


public int getFromJpp() {
	return fromJpp;
}

public void setFromJpp(int fromJpp) {
	this.fromJpp = fromJpp;
}

public List<Role> getNodeList() {
	return nodeList;
}

public void setNodeList(List<Role> nodeList) {
	this.nodeList = nodeList;
}

private JobLand jobland;
  
  public List<Cost> getList_cost() {
	return list_cost;
}

public void setList_cost(List<Cost> list_cost) {
	this.list_cost = list_cost;
}

public String getCostRate() {
	return costRate;
}

public void setCostRate(String costRate) {
	this.costRate = costRate;
}

public List<CommonmstVo> getAccountyear() {
	return accountyear;
}

public void setAccountyear(List<CommonmstVo> accountyear) {
	this.accountyear = accountyear;
}

public List<CommonmstVo> getList_foreign_tax() {
	return list_foreign_tax;
}

public void setList_foreign_tax(List<CommonmstVo> list_foreign_tax) {
	this.list_foreign_tax = list_foreign_tax;
}

public List<CommonmstVo> getList_tax() {
	return list_tax;
}

public void setList_tax(List<CommonmstVo> list_tax) {
	this.list_tax = list_tax;
}

public JobLand getJobland() {
	return jobland;
}

public void setJobland(JobLand jobland) {
	this.jobland = jobland;
}

public List<Lable> getList_lable() {
	return list_lable;
}

public void setList_lable(List<Lable> list_lable) {
	this.list_lable = list_lable;
}

private String money_code;
	public String getMoney_code() {
	return money_code;
}

public void setMoney_code(String money_code) {
	this.money_code = money_code;
}

	public List<SalemstVo> getList_sale() {
	return list_sale;
}

public void setList_sale(List<SalemstVo> list_sale) {
	this.list_sale = list_sale;
}

	public List<CommonmstVo> getList_comst() {
		return list_comst;
	}
	
	public void setList_comst(List<CommonmstVo> list_comst) {
		this.list_comst = list_comst;
	}
}
