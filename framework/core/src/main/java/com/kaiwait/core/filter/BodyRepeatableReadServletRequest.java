/**
 * 
 */
package com.kaiwait.core.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author wings
 *
 */
public class BodyRepeatableReadServletRequest extends HttpServletRequestWrapper {

	private BodyRepeatabledReadServletInputStream inputStream;

	public BodyRepeatableReadServletRequest(HttpServletRequest request, byte[] body) throws IOException {
		super(request);
		this.inputStream = new BodyRepeatabledReadServletInputStream(body);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.inputStream));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return this.inputStream;
	}

}
