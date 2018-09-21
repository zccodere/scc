package com.zccoder.scc.domain.enums;

/**
 * rest接口的 响应消息  可选枚举对象
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public enum EnumReturnData {
	
	/** 通用枚举对象 */
	SUCCESS("200","成功"),
	SUCCESS_QUERY("200","查询成功"),
	SUCCESS_SAVE("200","保存成功"),
	SUCCESS_UPDATE("200","修改成功"),
	SUCCESS_REMOVE("200","删除成功"),
	
	PARAM_ERROR("400","参数错误"),
	PARAM_NOT_EXIST("401","参数不存在"),
	OPER_TYPE_NOT_EXIST("402","操作类型错误"),
	SYS_EXCEPTION("500","系统异常"),
	DATA_IS_EMPTY("501","数据为空"),
	
	/** 特定用途 */
	PROJECT_CODE_IS_EXIST("2000","项目编码已存在，请重新输入"),
	PROPERTIES_KEY_IS_EXIST("2001","参数键已存在，请重新输入"),
	PROPERTIES_GROUP_IS_EXIST("2002","项目已添加改参数组，请勿重复添加");
	
	/** 操作类型描述 */
	private String respCode;
	/** 操作类型编码 */
	private String respMsg;
	
	private EnumReturnData(String respCode,String respMsg){
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}
}
