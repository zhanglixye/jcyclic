/**
 * 
 */
package com.kaiwait.core.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


/**
 * @author wings
 *
 */
public class JcUploadCsvFilter extends OncePerRequestFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		/*******************************/
		String[] allowDomain = { "http://localhost","http://192.168.0.37","http://127.0.0.1","http://192.168.0.101","http://54.223.184.157",
				"https://www.j-cyclic.cn","https://j-cyclic.cn","https://www.j-cyclic.com","https://j-cyclic.com","https://demo.j-cyclic.cn",
				"https://test.j-cyclic.cn","https://www.j-cyclic.com.cn","https://j-cyclic.com.cn","http://39.97.20.124"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set allowedOrigins= new HashSet(Arrays.asList(allowDomain));
		String originHeader = request.getHeader("Origin");
		// 设置允许跨域访问的域，*表示支持所有的来源
        //response.setHeader("Access-Control-Allow-Origin", "*");
		if (allowedOrigins.contains(originHeader)) {
			response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setContentType("application/json;charset=UTF-8");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type,requestID,requestName");
			//response.setHeader("Access-Control-Allow-Origin", "*");
		}
			filterChain.doFilter(request, response);
	}

	

}
