package com.zccoder.scc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 项目启动入口
 * @Created by ZhangCheng on 2017-06-16
 * 
 */
@SpringBootApplication
public class SccApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SccApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SccApplication.class);
    }
}
