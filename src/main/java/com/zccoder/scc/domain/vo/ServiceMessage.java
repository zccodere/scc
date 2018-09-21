package com.zccoder.scc.domain.vo;

import java.io.Serializable;

import com.zccoder.scc.domain.enums.EnumReturnData;

/**
 * 业务层响应 数据对象
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public class ServiceMessage<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 响应消息 */
	private EnumReturnData enumReturnData;
	/** 处理结果 */
	private boolean success = false;
	/** 响应数据 */
	private T respData;
	
	public ServiceMessage(){};
	
	public ServiceMessage(EnumReturnData enumReturnData) {
		super();
		this.enumReturnData = enumReturnData;
	}

	@Override
	public String toString() {
		return "ServiceMessage [enumReturnData=" + enumReturnData + ", success=" + success + "]";
	}

	public EnumReturnData getEnumReturnData() {
		return enumReturnData;
	}

	public void setEnumReturnData(EnumReturnData enumReturnData) {
		this.enumReturnData = enumReturnData;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getRespData() {
		return respData;
	}

	public void setRespData(T respData) {
		this.respData = respData;
	}
}
