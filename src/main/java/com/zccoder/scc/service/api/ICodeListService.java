package com.zccoder.scc.service.api;

import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.po.CodeListPo;
import com.zccoder.scc.domain.vo.ServiceMessage;

/**
 * 编码参数 业务功能接口
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public interface ICodeListService {

	/**
	 * 根据条件获取类型值
	 */
	ServiceMessage<List<CodeListPo>> listCodeList(CodeListPo codeListPo);
	
	/**
	 * 根据操作类型维护类型值
	 */
	ServiceMessage<?> operCodeList(CodeListPo codeListPo,EnumOperationType operationType);
	
	/**
	 * 将所有类型值写入redis
	 */
	ServiceMessage<?> writeCodeListToRedis();
	
	/**
	 * 从redis中获取类型值
	 */
	ServiceMessage<List<CodeListPo>> listCodeListByRedis(CodeListPo codeListPo);
}
