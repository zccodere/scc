package com.zccoder.scc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title 业务参数实体类
 * @Description 通过@Value注入参数值
 * @author zc
 * @version 1.0 2017-09-19
 */
@Component
public class URLProperties {
	
	@Value("${url.domain.prefix}")
	private String domain_prefix;
	
	@Value("${url.generate.mode}")
	private String generate_mode;
	
	public String getGenerate_mode() {
		return generate_mode;
	}
	
	public String getDomain_prefix() {
		return domain_prefix;
	}
}
