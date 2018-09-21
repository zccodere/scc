package com.zccoder.scc.service.api;

import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.po.ProjectPo;
import com.zccoder.scc.domain.po.PropertiesPo;
import com.zccoder.scc.domain.vo.ProjectVo;
import com.zccoder.scc.domain.vo.ServiceMessage;

/**
 * 项目配置 业务功能接口
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public interface IProjectService {
	
	/**
	 * 根据条件获取项目
	 */
	ServiceMessage<List<ProjectPo>> listProjectPo(ProjectPo projectPo);
	
	/**
	 * 根据项目编号获取项目
	 */
	ServiceMessage<ProjectPo> findOne(ProjectPo projectPo);
	
	/**
	 * 根据操作类型维护项目
	 */
	ServiceMessage<?> operProjectPo(ProjectPo projectPo,EnumOperationType operationType);
	
	/**
	 * 根据项目编码获取参数-返回有效的参数
	 */
	ServiceMessage<List<PropertiesPo>> listProperties(ProjectPo projectPo);

	void saveSecurity(ProjectVo projectVo);
	
}
