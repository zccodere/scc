package com.zccoder.scc.domain.enums;

/**
 * PropertiesPo 类的 PropertiesType 字段 可选枚举对象
 * @Created by ZhangCheng on 2017-06-16
 *
 */
public enum EnumPropertiesType {
	
	SYS_CONSTANS("系统常量"),SYS_PARAM("系统参数");
	
	/** 描述 */
	private String desc;
	
	private EnumPropertiesType(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
