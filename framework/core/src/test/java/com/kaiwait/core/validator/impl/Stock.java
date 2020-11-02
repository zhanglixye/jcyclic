package com.kaiwait.core.validator.impl;

public class Stock {
	 /** 仓库名称 */
	  private String name;
	 /** 仓库地址 */
	  private String address;
	/**
	 * 取得仓库名称
	 * @return 仓库名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置仓库名称
	 * @param name 仓库名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 取得仓库地址
	 * @return 仓库地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置仓库地址
	 * @param name 仓库地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
