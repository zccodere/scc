package com.zccoder.scc.service.api;

import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.po.PropertiesPo;
import com.zccoder.scc.domain.vo.ServiceMessage;

/**
 * 项目参数 业务功能接口
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public interface IPropertiesService {
	
	/**
	 * 根据条件获取项目参数
	 * @param projectPo
	 * @return
	 */
	ServiceMessage<List<PropertiesPo>> listPropertiesPo(PropertiesPo propertiesPo);
	
	/**
	 * 根据操作类型维护项目参数
	 * @param projectPo
	 * @return
	 */
	ServiceMessage<?> operPropertiesPo(PropertiesPo propertiesPo,EnumOperationType operationType);
}
