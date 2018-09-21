package com.zccoder.scc.domain.enums;

/**
 * 操作类型  可选枚举对象
 * @Created by ZhangCheng on 2017-06-16
 *
 */
public enum EnumOperationType {
	
	QUERY("查询","100"),SAVE("新增","101"),UPDATE("修改","102"),REMOVE("删除","103");
	
	/** 操作类型描述 */
	private String desc;
	/** 操作类型编码 */
	private String code;
	
	private EnumOperationType(String desc,String code){
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
	
}
