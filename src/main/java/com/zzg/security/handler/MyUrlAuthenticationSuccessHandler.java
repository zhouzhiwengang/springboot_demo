package com.zzg.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zzg.filter.HttpParamsFilter;

@Component("myUrlAuthenticationSuccessHandler")
public class MyUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public MyUrlAuthenticationSuccessHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyUrlAuthenticationSuccessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		if (isAlwaysUseDefaultTargetUrl()) {
			return this.getDefaultTargetUrl();
		}
		String targetUrl = null;
		if (this.getTargetUrlParameter() != null) {
			targetUrl = request.getParameter(this.getTargetUrlParameter());
			if (StringUtils.isNotEmpty(targetUrl)) {
				return targetUrl;
			}
		}
		
		if (!StringUtils.isNotEmpty(targetUrl)) {
			HttpSession session = request.getSession();
			targetUrl = (String) session.getAttribute(HttpParamsFilter.REQUESTED_URL);
		}
		
		if (!StringUtils.isNotEmpty(targetUrl)) {
			targetUrl = this.getDefaultTargetUrl();
		}

		return targetUrl;
	}
	
	

}
