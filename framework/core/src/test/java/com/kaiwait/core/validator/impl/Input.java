package com.kaiwait.core.validator.impl;

import java.util.List;

public class Input {
	 /** 产品名称 */
	  private String prodName;
	 /** 产品类型 */
	  private String prodType;
	 /** 仓库列表 */
	  private List<Stock> stockList;
	/**
	 * 取得产品名称
	 * @return 产品名称
	 */
	public String getProdName() {
		return prodName;
	}
	/**
	 * 设置产品名称
	 * @param name 产品名称
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	/**
	 * 取得产品类型
	 * @return 产品类型
	 */
	public String getProdType() {
		return prodType;
	}
	/**
	 * 设置产品类型
	 * @param name 产品类型
	 */
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	/**
	 * 取得仓库列表
	 * @return 仓库列表
	 */
	public List<Stock> getStockList() {
		return stockList;
	}
	/**
	 * 设置仓库列表
	 * @param name 仓库列表
	 */
	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}
}
