package com.kaiwait.bean.jczh.entity;

import java.util.List;

public class ListLable {
  private List<BindLable> bindlable;
  private Lable lable;
  private List<Lable> listlableid;
 private List<Lable> listdellable;
 
public List<Lable> getListdellable() {
	return listdellable;
}
public void setListdellable(List<Lable> listdellable) {
	this.listdellable = listdellable;
}
public List<Lable> getListlableid() {
	return listlableid;
}
public void setListlableid(List<Lable> listlableid) {
	this.listlableid = listlableid;
}
public List<BindLable> getBindlable() {
	return bindlable;
}
public void setBindlable(List<BindLable> bindlable) {
	this.bindlable = bindlable;
}
public Lable getLable() {
	return lable;
}
public void setLable(Lable lable) {
	this.lable = lable;
}

}
