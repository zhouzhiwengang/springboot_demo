package com.zzg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.zzg.mapper")
@EnableConfigurationProperties
public class DemoApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(DemoApplication.class, args);
	}

}
