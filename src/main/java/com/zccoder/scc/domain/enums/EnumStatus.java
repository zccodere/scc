package com.zccoder.scc.domain.enums;

/**
 * 数据记录状态类型  可选枚举对象
 * @Created by ZhangCheng on 2017-08-02
 *
 */
public enum EnumStatus {
	
	/** 1：有效 */
	EFFECTIVE("1"),
	/** 0：无效 */
	INVALID("0"),
	/** 2：已删除 */
	DELETED("2");
	
	/** 状态编码 */
	private String code;
	
	private EnumStatus(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
