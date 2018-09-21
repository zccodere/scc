package com.zccoder.scc.domain.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.zccoder.scc.domain.enums.EnumReturnData;

/**
 * ReturnDataVo 数据对象
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public class ReturnDataVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 响应状态码 */
	private String respCode;
	/** 响应消息 */
	private String respMsg;
	
	private Map<String,Object> respMap;
	
	public ReturnDataVo(EnumReturnData returnData){
		this.respMap = new HashMap<String,Object>();
		this.respCode = returnData.getRespCode();
		this.respMsg = returnData.getRespMsg();
	}
	
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	public void addRespData(String key,Object value){
		this.respMap.put(key, value);
	}
	
	public Map<String, Object> getRespMap() {
		return respMap;
	}

	@Override
	public String toString() {
		return "ReturnDataVo [respCode=" + respCode + ", respMsg=" + respMsg + "]";
	}
	
}
