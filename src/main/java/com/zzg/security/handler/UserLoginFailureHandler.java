package com.zzg.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.zzg.common.Response;

/**
 * 登录失败处理类
 * 
 * @author zzg
 *
 */
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler  {
	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Response.responseJson(response, Response.error(500, "登录失败", exception.getMessage()));
	}
}
