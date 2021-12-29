package com.zzg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zzg.security.handler.UserAccessDeniedHandler;
import com.zzg.security.handler.UserLoginFailureHandler;
import com.zzg.security.handler.UserLoginSuccessHandler;
import com.zzg.security.handler.UserLogoutSuccessHandler;
import com.zzg.security.handler.UserNotLoginHandler;
import com.zzg.security.provider.UserAuthenticationProvider;

/**
 * 系统安全核心配置
 * 
 * @author zzg
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * 无权限处理类
	 */
	@Autowired
	private UserAccessDeniedHandler userAccessDeniedHandler;

	/**
	 * 用户未登录处理类
	 */
	@Autowired
	private UserNotLoginHandler userNotLoginHandler;

	/**
	 * 用户登录成功处理类
	 */
	@Autowired
	private UserLoginSuccessHandler userLoginSuccessHandler;

	/**
	 * 用户登录失败处理类
	 */
	@Autowired
	private UserLoginFailureHandler userLoginFailureHandler;

	/**
	 * 用户登出成功处理类
	 */
	@Autowired
	private UserLogoutSuccessHandler userLogoutSuccessHandler;

	/**
	 * 用户登录验证
	 */
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;

	/**
	 * 加密方式
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 用户登录验证
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(userAuthenticationProvider);
	}

	/**
	 * 安全权限配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin() // 可以相同域名页面的frame中展示
				.and().authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")//只有管理员才能访问
				      .antMatchers("/login", "/logout", "/layui/**","/jquery/**", "/images/**").permitAll()
				      .anyRequest().authenticated()//其他路径必须验证身份
				.and().httpBasic().authenticationEntryPoint(userNotLoginHandler) // 配置未登录处理类
				.and().formLogin().loginPage("/login").loginProcessingUrl("/api/login") // 配置登录URL
				.successHandler(userLoginSuccessHandler) // 配置登录成功处理类
				.failureHandler(userLoginFailureHandler) // 配置登录失败处理类
				.and().exceptionHandling().accessDeniedHandler(userAccessDeniedHandler)// 配置没有权限处理类
				.and().csrf().disable(); // 禁用跨站请求伪造防护
				
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring()
			.antMatchers("/login")
			.antMatchers("/logout")
			.antMatchers("/static/**");
		
	}
	

}
