/**
 * 
 */
package com.kaiwait.webclient.dict;

/**
 * @author wanght
 *
 */
public enum HttpContentType {
	XML("application/xml"), JSON("application/json"), PLAIN_TEXT("text/plain"), HTML("text/html");

	private final String value;

	private HttpContentType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
