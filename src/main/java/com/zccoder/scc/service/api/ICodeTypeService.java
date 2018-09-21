package com.zccoder.scc.service.api;

import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.po.CodeTypePo;
import com.zccoder.scc.domain.vo.ServiceMessage;

/**
 * 类型配置 业务功能接口
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public interface ICodeTypeService {
	
	/**
	 * 根据条件获取类型
	 */
	ServiceMessage<List<CodeTypePo>> listCodeType(CodeTypePo codeTypePo);
	
	/**
	 * 根据操作类型维护类型
	 */
	ServiceMessage<?> operCodeType(CodeTypePo codeTypePo,EnumOperationType operationType);
	
}
