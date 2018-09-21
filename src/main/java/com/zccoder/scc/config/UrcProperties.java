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
public class UrcProperties {
	
	@Value("${uac.rest.host}")
	private String uac_rest_host;

	public String getUac_rest_host() {
		return uac_rest_host;
	}
}
