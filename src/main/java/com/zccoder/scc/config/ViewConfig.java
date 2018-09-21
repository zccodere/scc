package com.zccoder.scc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 视图控制配置类
 * @create ZhangCheng by 2017-08-02
 *
 */
@Configuration
public class ViewConfig extends WebMvcConfigurerAdapter{
	
	/**
     * 配置ViewController点击链接直接跳转页面
     * @param registry
     */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("").setViewName("main");
		registry.addViewController("/").setViewName("main");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/home").setViewName("home");
        
        registry.addViewController("/codetype").setViewName("codetype");
        registry.addViewController("/codelist").setViewName("codelist");
        registry.addViewController("/project").setViewName("project");
        registry.addViewController("/projectgroup").setViewName("projectgroup");
        registry.addViewController("/propertiesgroup").setViewName("propertiesgroup");
        registry.addViewController("/properties").setViewName("properties");
        registry.addViewController("/shortUrl").setViewName("shortUrl");
	}
}
