package com.zccoder.scc.config;

/**
 * @Title 业务参数枚举类
 * @Description 描述业务参数的key等
 * @author zc
 * @version 1.0 2017-09-20
 */
public enum EnUrcProperties {
	
	/** 20170920  认证中心rest地址 */
	UAC_REST_HOST("uac.rest.host","uac_rest_host");
	
	private String key;
	
	private String strKey;
	
	private EnUrcProperties(String key,String strKey){
		this.key = key;
		this.strKey = strKey;
	}

	public String getKey() {
		return key;
	}

	public String getStrKey() {
		return strKey;
	}
	
}
