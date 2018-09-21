package com.zccoder.scc.service.api;

import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.po.ProjectGroupPo;
import com.zccoder.scc.domain.vo.ServiceMessage;

/**
 * 项目属组 业务功能接口
 * @Created by ZhangCheng on 2017-06-19
 *
 */
public interface IProjectGroupService {
	
	/**
	 * 项目属组查询
	 * @param projectGroupPo
	 * @return
	 */
	ServiceMessage<List<ProjectGroupPo>> listProjectGroupPo(ProjectGroupPo projectGroupPo);
	
	/**
	 * 项目属组维护
	 * @param projectGroupPo
	 * @return
	 */
	ServiceMessage<?> operProjectGroupPo(ProjectGroupPo projectGroupPo,EnumOperationType operationType);
}
