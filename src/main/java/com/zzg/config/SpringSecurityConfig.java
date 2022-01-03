package com.zzg.config;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.zzg.filter.HttpParamsFilter;

/**
 * 系统安全核心配置
 * 
 * @author zzg
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	AuthenticationProvider authenticationProvider;
	@Autowired
	SingleSignOutFilter singleSignOutFilter;
	@Autowired
	LogoutFilter logoutFilter;
	@Autowired
	CasAuthenticationFilter casAuthenticationFilter;

	 /**
     * 8.
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

	/**
	 * 安全权限配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin(); // 可以相同域名页面的frame中展示
		http.authorizeRequests()
				.antMatchers("/index","/index.html","/layui/**","/jquery/**", "/images/**").permitAll()
	            .anyRequest() .authenticated()  //匹配任意url
				.and().exceptionHandling()
	            .authenticationEntryPoint(authenticationEntryPoint)
	            .and()
	            //.addFilter(casAuthenticationFilter)
	            .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class)
	            .addFilterBefore(logoutFilter, LogoutFilter.class)
	            .logout()
	            .logoutUrl("/signout") // 退出登录的url
	            .logoutSuccessUrl("/cas_logout") // 退出登录成功跳转的url
	            .deleteCookies("JSESSIONID") // 删除名为"JSESSIONID"的cookie
	   
	   			.and()
	            .cors()
	            //.configurationSource(corsConfigurationSource())
	            .and()

	            .csrf()
	            .disable();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/static/**");
		web.ignoring().antMatchers("/index","/index.html");

	}
	
	@Bean
	public FilterRegistrationBean httpParamsFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new HttpParamsFilter());
		filterRegistrationBean.setOrder(-999);
		filterRegistrationBean.addUrlPatterns("/"); 
		return filterRegistrationBean;
	}


}
