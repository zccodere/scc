package com.zccoder.scc.web.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.vo.ProjectCodeNameVo;
import com.zccoder.scc.domain.vo.ProjectGroupVo;
import com.zccoder.scc.domain.vo.ReturnDataVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.IProjectGroupService;
import com.zccoder.scc.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.ProjectGroupPo;

/**
 * 项目属组 rest服务
 * @Created by ZhangCheng on 2017-08-14
 * 
 */
@RestController
@RequestMapping("/rest/projectgroup")
public class ProjectGroupRest {
	
	@Autowired
	private IProjectGroupService projectGroupService;
	
	private static Logger logger = LoggerFactory.getLogger(ProjectGroupRest.class);
	
	/**
	 * 参数属组查询服务
	 * @param projectGroupVo
	 * @return
	 */
	@PostMapping("/query")
	public ReturnDataVo queryProjectGroup(ProjectGroupVo projectGroupVo){
		logger.info("[URC] query properties group request param is {}",projectGroupVo);
		
		ProjectGroupPo projectGroupPo = new ProjectGroupPo();
		BeanUtils.copyProperties(projectGroupVo, projectGroupPo);
		ServiceMessage<List<ProjectGroupPo>> result = projectGroupService.listProjectGroupPo(projectGroupPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		if(result.isSuccess()){
			List<ProjectGroupPo> listProjectGroupPo = result.getRespData();
			List<ProjectGroupVo> listProjectGroupVo = new ArrayList<ProjectGroupVo>();
			for (ProjectGroupPo projectGroupPoTemp : listProjectGroupPo) {
				ProjectGroupVo projectGroupVoTemp = new ProjectGroupVo();
				BeanUtils.copyProperties(projectGroupPoTemp, projectGroupVoTemp);
				projectGroupVoTemp.setCreate_time(DateUtils.dateToStr(projectGroupPoTemp.getCreate_time()));
				projectGroupVoTemp.setLast_modify_time(DateUtils.dateToStr(projectGroupPoTemp.getLast_modify_time()));
				projectGroupVoTemp.setStatus_name("1".equals(projectGroupVoTemp.getStatus()) ? "有效" : "无效");
				listProjectGroupVo.add(projectGroupVoTemp);
			}
			returnDataVo.addRespData("project_group_list", listProjectGroupVo);
		}
		
		logger.info("[URC] query properties group response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 参数属组维护服务
	 * @param propertiesVo
	 * @param oper_type
	 * @return
	 */
	@PostMapping("/oper")
	public ReturnDataVo operProjectGroup(ProjectGroupVo projectGroupVo,String oper_type){
		logger.info("[URC] oper properties group type is {}, request param is {}",oper_type,projectGroupVo);
		
		EnumOperationType operationType = null;
		
		if(EnumOperationType.SAVE.getCode().equals(oper_type)){
			operationType = EnumOperationType.SAVE;
		}
		if(EnumOperationType.UPDATE.getCode().equals(oper_type)){
			operationType = EnumOperationType.UPDATE;
		}
		if(EnumOperationType.REMOVE.getCode().equals(oper_type)){
			operationType = EnumOperationType.REMOVE;
		}
		if(null == operationType){
			logger.info("[URC] oper properties group type is not support, type is {}",oper_type);
			return new ReturnDataVo(EnumReturnData.OPER_TYPE_NOT_EXIST);
		}
		
		ProjectGroupPo projectGroupPo = new ProjectGroupPo();
		BeanUtils.copyProperties(projectGroupVo, projectGroupPo);
		ServiceMessage<?> result = this.projectGroupService.operProjectGroupPo(projectGroupPo, operationType);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		logger.info("[URC] oper properties group response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 查询已配置属组的项目
	 * @param projectGroupVo
	 * @return
	 */
	@PostMapping("/has")
	public ReturnDataVo queryHas(ProjectGroupVo projectGroupVo){
		logger.info("[URC] query properties group request param is {}",projectGroupVo);
		
		ProjectGroupPo projectGroupPo = new ProjectGroupPo();
		BeanUtils.copyProperties(projectGroupVo, projectGroupPo);
		ServiceMessage<List<ProjectGroupPo>> result = projectGroupService.listProjectGroupPo(projectGroupPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		Set<ProjectCodeNameVo> setVos = new HashSet<ProjectCodeNameVo>();
		
		if(result.isSuccess()){
			List<ProjectGroupPo> listProjectGroupPo = result.getRespData();
			for (ProjectGroupPo projectGroupPoTemp : listProjectGroupPo) {
				ProjectCodeNameVo projectCodeNameVoTemp = new ProjectCodeNameVo();
				BeanUtils.copyProperties(projectGroupPoTemp, projectCodeNameVoTemp);
				setVos.add(projectCodeNameVoTemp);
			}
			returnDataVo.addRespData("project_has_list", setVos);
		}
		
		logger.info("[URC] query properties group response status is {}",returnDataVo);
		return returnDataVo;
	}
}
