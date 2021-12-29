package com.zzg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
/**
 * MyBatisPlus 配置类
 * @author Administrator
 *
 */
@Configuration
public class MyBatisPlusConfig {
	 /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


}
