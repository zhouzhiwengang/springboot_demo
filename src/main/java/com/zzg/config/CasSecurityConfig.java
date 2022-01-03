package com.zzg.config;

import java.util.ArrayList;
import java.util.List;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.zzg.security.handler.MyUrlAuthenticationSuccessHandler;
import com.zzg.security.service.MyUserDetailService;

@Configuration
public class CasSecurityConfig {


	@Autowired
	MyUserDetailService myUserDetailService;
	
	@Autowired
	MyUrlAuthenticationSuccessHandler myUrlAuthenticationSuccessHandler;

	/**
	 * 1.处理 CAS 验证逻辑
	 * 
	 * @return
	 */
	@Bean
	CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider provider = new CasAuthenticationProvider();
		provider.setServiceProperties(serviceProperties());
		provider.setTicketValidator(ticketValidator());
		provider.setUserDetailsService(myUserDetailService);
		provider.setKey("aaaaa"); // 不知道具体作用，目前看来使用上没有影响
		return provider;
	}

	/**
	 * 2.配置一下 Client 的登录地址; 在 CAS Server 上登录成功后，重定向的地址
	 * 
	 * @return
	 */
	@Bean
	ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService("http://127.0.0.1:8084/demo/index"); // 配置 Client 的登录地址
		return serviceProperties;
	}

	/**
	 * 3.配置 ticket 校验地址，CAS Client 拿到 ticket 要去 CAS Server 上校验，
	 * 默认校验地址是：${cas.server.prefix}/proxyValidate?ticket=xxx
	 * 
	 * @return
	 */
	@Bean
	TicketValidator ticketValidator() {
		return new Cas20ProxyTicketValidator("http://l127.0.0.1:8084/cas");
	}

	/**
	 * 4.CAS 认证的过滤器，过滤器将请求拦截下来之后，交由 CasAuthenticationProvider 来做具体处理
	 * 
	 * @param authenticationProvider
	 * @return
	 */
	@Bean
	CasAuthenticationFilter casAuthenticationFilter() {
		CasAuthenticationFilter filter = new CasAuthenticationFilter();
		filter.setServiceProperties(serviceProperties());
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(casAuthenticationProvider());
		filter.setAuthenticationManager(new ProviderManager(providers));
		filter.setAuthenticationSuccessHandler(myUrlAuthenticationSuccessHandler);
		return filter;
	}

	/**
	 * 5.表示接受 CAS Server 发出的注销请求，所有的注销请求都将从 CAS Client 转发到 CAS Server，CAS Server
	 * 处理完后，会通知所有的 CAS Client 注销登录。
	 * 
	 * @return
	 */
	@Bean
	SingleSignOutFilter singleSignOutFilter() {
		SingleSignOutFilter sign = new SingleSignOutFilter();
		sign.setIgnoreInitConfiguration(true);
		return sign;
	}

	/**
	 * 6.配置将注销请求转发到 CAS Server。
	 * 
	 * @return
	 */
	@Bean
	LogoutFilter logoutFilter() {
		LogoutFilter filter = new LogoutFilter("http://127.0.0.1:8084/cas/logout", new SecurityContextLogoutHandler());
		filter.setFilterProcessesUrl("/mylogout");
		return filter;
	}

	/**
	 * 7.CAS 验证的入口
	 * 
	 * @return
	 */
	@Bean
	@Primary
	AuthenticationEntryPoint authenticationEntryPoint() {
		CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
		entryPoint.setLoginUrl("http://127.0.0.1:8084/cas/login"); // 设置 CAS Server 的登录地址
		entryPoint.setServiceProperties(serviceProperties());

		return entryPoint;
	}

}
