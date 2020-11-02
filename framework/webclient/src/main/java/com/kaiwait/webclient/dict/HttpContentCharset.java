/**
 * 
 */
package com.kaiwait.webclient.dict;

/**
 * @author wanght
 *
 */
public enum HttpContentCharset {
	UTF8("UTF-8"), GBK("GBK"), GB2312("gb2312"), ISO("iso-8859-1");

	private final String value;

	private HttpContentCharset(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
