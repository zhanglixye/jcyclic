package com.kaiwait.bean.jczh.vo;

import java.util.List;

public class JobInitSelectVo {
  //売上种目
 private List<SalemstVo> saleSelect;
 //请求先
 private List<RequestorVo> requestSelect;
 //得意先
 private List<CustomerVo>  customerSelect;
 //相手先G会社
 private List<G_CompanyVo> GSelect;
public List<SalemstVo> getSaleSelect() {
	return saleSelect;
}
public void setSaleSelect(List<SalemstVo> saleSelect) {
	this.saleSelect = saleSelect;
}
public List<RequestorVo> getRequestSelect() {
	return requestSelect;
}
public void setRequestSelect(List<RequestorVo> requestSelect) {
	this.requestSelect = requestSelect;
}
public List<CustomerVo> getCustomerSelect() {
	return customerSelect;
}
public void setCustomerSelect(List<CustomerVo> customerSelect) {
	this.customerSelect = customerSelect;
}
public List<G_CompanyVo> getGSelect() {
	return GSelect;
}
public void setGSelect(List<G_CompanyVo> gSelect) {
	GSelect = gSelect;
}
 
 
}
