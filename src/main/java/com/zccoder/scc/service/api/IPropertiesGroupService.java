package com.zccoder.scc.service.api;

import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.po.PropertiesGroupPo;
import com.zccoder.scc.domain.vo.ServiceMessage;

/**
 * 参数组 业务功能接口
 * @Created by ZhangCheng on 2017-08-02
 *
 */
public interface IPropertiesGroupService {
	
	/**
	 * 根据条件获取参数组
	 * @param projectPo
	 * @return
	 */
	ServiceMessage<List<PropertiesGroupPo>> listPropertiesGroupPo(PropertiesGroupPo propertiesGroupPo);
	
	/**
	 * 根据操作类型维护项目组
	 * @param projectPo
	 * @return
	 */
	ServiceMessage<?> operPropertiesGroupPo(PropertiesGroupPo propertiesGroupPo,EnumOperationType operationType);
	
}
