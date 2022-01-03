package com.zzg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpParamsFilter implements Filter {
	public static String REQUESTED_URL = "CasRequestedUrl";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		HttpSession session = httpServletRequest.getSession();
		String requestPath = this.getFullPath(httpServletRequest);
		session.setAttribute(REQUESTED_URL, requestPath);
		chain.doFilter(request, response);
	}
	
	public  String getFullPath(HttpServletRequest request) {
        if (request.getQueryString() != null)
            return request.getRequestURL().toString() + "?" + request.getQueryString();
        else return request.getRequestURL().toString();
    }
}
