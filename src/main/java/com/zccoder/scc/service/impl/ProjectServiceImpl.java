package com.zccoder.scc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.enums.EnumStatus;
import com.zccoder.scc.domain.vo.ProjectVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.domain.vo.User;
import com.zccoder.scc.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zccoder.scc.domain.po.ProjectGroupPo;
import com.zccoder.scc.domain.po.ProjectPo;
import com.zccoder.scc.domain.po.PropertiesGroupPo;
import com.zccoder.scc.domain.po.PropertiesPo;
import com.zccoder.scc.service.api.IProjectGroupService;
import com.zccoder.scc.service.api.IProjectService;
import com.zccoder.scc.service.api.IPropertiesGroupService;
import com.zccoder.scc.service.api.IPropertiesService;
import com.zccoder.scc.util.IdUtils;

/**
 * 项目配置 业务功能实现类
 * @Created by ZhangCheng on 2017-06-19
 *
 */
@Service
public class ProjectServiceImpl implements IProjectService {

	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private IProjectGroupService projectGroupService;
	
	@Autowired
	private IPropertiesGroupService propertiesGroupService;
	
	@Autowired
	private IPropertiesService propertiesService;
	
	@Autowired
	private User user;
	
	@Override
	public ServiceMessage<List<ProjectPo>> listProjectPo(ProjectPo projectPo){

		final String projectCode = projectPo.getProject_code();
		final String projectName = projectPo.getProject_name();
		final String status = projectPo.getStatus();

		// 构建查询条件
		Specification<ProjectPo> querySpeci = new Specification<ProjectPo>() {
			@Override
			public Predicate toPredicate(Root<ProjectPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				// 如果 项目编码 不为空
				if ("" != projectCode && null != projectCode) {
					lstPredicates.add(cb.equal(root.get("project_code").as(String.class), projectCode));
				}
				// 如果 项目名称 不为空
				if ("" != projectName && null != projectName) {
					lstPredicates.add(cb.like(root.get("project_name").as(String.class), "%" + projectName + "%"));
				}
				// 如果 状态 不为空
				if ("" != status && null != status) {
					lstPredicates.add(cb.equal(root.get("status").as(String.class), status));
				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		
		logger.info("[URC] query project specification is {}",querySpeci);
		// 查找符合条件的记录
		List<ProjectPo> listProjectPo = this.projectRepository.findAll(querySpeci);
		
		if(null == listProjectPo || listProjectPo.size() < 1){
			ServiceMessage<List<ProjectPo>> respServiceMessage = new ServiceMessage<List<ProjectPo>>(EnumReturnData.DATA_IS_EMPTY);
			return respServiceMessage;
		}
		
		for(Iterator<ProjectPo> it = listProjectPo.iterator();it.hasNext();){
			ProjectPo poTemp = it.next();
	        if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
	        	it.remove();
	        }
	    }
		
		if(null == listProjectPo || listProjectPo.size() < 1){
			ServiceMessage<List<ProjectPo>> respServiceMessage = new ServiceMessage<List<ProjectPo>>(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		ServiceMessage<List<ProjectPo>> respServiceMessage = new ServiceMessage<List<ProjectPo>>(EnumReturnData.SUCCESS_QUERY);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setRespData(listProjectPo);
		
		return respServiceMessage;
	}
	
	@Override
	public ServiceMessage<ProjectPo> findOne(ProjectPo projectPo) {
		ProjectPo rspPo = projectRepository.findOne(projectPo.getId());
		ServiceMessage<ProjectPo> respServiceMessage = new ServiceMessage<ProjectPo>(EnumReturnData.SUCCESS_QUERY);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setRespData(rspPo);
		return respServiceMessage;
	}
	
	@Override
	public ServiceMessage<?> operProjectPo(ProjectPo projectPo, EnumOperationType operationType){
		
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		
		if (EnumOperationType.SAVE.equals(operationType)) {
			
			ProjectPo projectPoTempTwo = this.projectRepository.getEffectiveProjectByProjectCode(projectPo.getProject_code());
			if ( projectPoTempTwo != null) {
				respServiceMessage.setEnumReturnData(EnumReturnData.PROJECT_CODE_IS_EXIST);
				return respServiceMessage;
			}
			
			logger.info("[URC] oper project add {}",projectPo);
			
			projectPo.setSecurity_flag("1");
			projectPo.setSecurity_token(IdUtils.getUuid());
			projectPo.setCreate_author_code(user.getAuthor_code());
			projectPo.setCreate_author_name(user.getAuthor_name());
			projectPo.setCreate_time(new Date());
			projectPo.setLast_modify_time(new Date());
			
			this.projectRepository.save(projectPo);
			respServiceMessage.setSuccess(true);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_SAVE);
			return respServiceMessage;
		}
		
		// 判断项目ID是否存在
		ProjectPo projectPoTemp = getProjectExist(projectPo);
		if(null == projectPoTemp){
			respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respServiceMessage;
		}
		
		if (EnumOperationType.UPDATE.equals(operationType)) {
			logger.info("[URC] oper project update {}",projectPo);
			
			projectPo.setLast_modify_time(new Date());
			projectPo.setCreate_author_code(projectPoTemp.getCreate_author_code());
			projectPo.setCreate_author_name(projectPoTemp.getCreate_author_name());
			projectPo.setCreate_time(projectPoTemp.getCreate_time());
			this.projectRepository.save(projectPo);
			respServiceMessage.setSuccess(true);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_UPDATE);
			return respServiceMessage;
		}

		logger.info("[URC] oper project remove {}",projectPo);
		
		projectPoTemp.setLast_modify_time(new Date());
		projectPoTemp.setStatus(EnumStatus.DELETED.getCode());
		// 删除项目记录
		projectRepository.save(projectPoTemp);
		// 删除项目属组
		removeProjectGroup(projectPoTemp);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_REMOVE);
		return respServiceMessage;
		
	}
	
	/**
	 * 功能：获取 ProjectPo 根据 ID
	 */
	private ProjectPo getProjectExist(ProjectPo projectPo){
		ProjectPo projectPoTemp = projectRepository.findOne(projectPo.getId());
		if(null == projectPoTemp){
			return null;
		}
		if(EnumStatus.DELETED.getCode().equals(projectPoTemp.getStatus())){
			return null;
		}
		return projectPoTemp;
	}

	@Override
	public ServiceMessage<List<PropertiesPo>> listProperties(ProjectPo projectPo) {
		ServiceMessage<List<PropertiesPo>> respProperties = new ServiceMessage<List<PropertiesPo>>(EnumReturnData.SYS_EXCEPTION);
		respProperties.setSuccess(false);
		
		// 查询有效的项目
		projectPo.setStatus(EnumStatus.EFFECTIVE.getCode());
		ServiceMessage<List<ProjectPo>> respServiceMessage = listProjectPo(projectPo);
		if(!respServiceMessage.isSuccess()){
			logger.info("[URC] This project code {} is not exist or project status is invalid .",projectPo.getProject_code());
			respProperties.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respProperties;
		}
		
		// 查询有效的项目属组
		List<ProjectGroupPo> respProGroups = listProjectGroup(projectPo);
		if(null == respProGroups || respProGroups.size() < 1){
			logger.info("[URC] This project code {} is not properties group or properties group status is invalid .",projectPo.getProject_code());
			respProperties.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respProperties;
		}
		
		// 查询有效的参数
		List<PropertiesPo> propertiess = new ArrayList<PropertiesPo>();
		for (ProjectGroupPo projectGroupTemp : respProGroups) {
			// 判断当前属组是否有效
			PropertiesGroupPo reqPropertiesGroupPo = new PropertiesGroupPo();
			reqPropertiesGroupPo.setId(Long.valueOf(projectGroupTemp.getGroup_id()));
			reqPropertiesGroupPo.setStatus(EnumStatus.EFFECTIVE.getCode());
			if(!propertiesGroupService.listPropertiesGroupPo(reqPropertiesGroupPo).isSuccess()){
				continue;
			};
			
			PropertiesPo reqTempPo = new PropertiesPo();
			reqTempPo.setGroup_id(projectGroupTemp.getGroup_id());
			reqTempPo.setStatus(EnumStatus.EFFECTIVE.getCode());
			ServiceMessage<List<PropertiesPo>> propertiesListTemp = propertiesService.listPropertiesPo(reqTempPo);
			if(propertiesListTemp.isSuccess()){
				propertiess.addAll(propertiesListTemp.getRespData());
			}
		}
		
		if(null == propertiess || propertiess.size() < 1){
			respProperties.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY);
			return respProperties;
		}
		
		respProperties.setEnumReturnData(EnumReturnData.SUCCESS_QUERY);
		respProperties.setRespData(propertiess);
		respProperties.setSuccess(true);
		return respProperties;
	}
	
	/**
	 * 功能：删除该项目编码对应的项目属组数据记录
	 */
	private void removeProjectGroup(ProjectPo projectPo){
		List<ProjectGroupPo> respProGroups = listProjectGroup(projectPo);
		if(null == respProGroups || respProGroups.size() < 1){
			return;
		}
		for (ProjectGroupPo projectGroupPoTemp : respProGroups) {
			projectGroupService.operProjectGroupPo(projectGroupPoTemp, EnumOperationType.REMOVE);
		}
	}
	
	/**
	 * 功能：获取该项目编码对应的项目属组有效数据记录
	 */
	private List<ProjectGroupPo> listProjectGroup(ProjectPo projectPo){
		ProjectGroupPo projectGroupPo = new ProjectGroupPo();
		projectGroupPo.setProject_code(projectPo.getProject_code());
		projectGroupPo.setStatus(EnumStatus.EFFECTIVE.getCode());
		ServiceMessage<List<ProjectGroupPo>> list = projectGroupService.listProjectGroupPo(projectGroupPo);
		if(list.isSuccess()){
			return list.getRespData();
		}
		return null;
	}

	@Override
	public void saveSecurity(ProjectVo projectVo) {
		ProjectPo tempPo = projectRepository.findOne(projectVo.getId());
		tempPo.setSecurity_flag(projectVo.getSecurity_flag());
		tempPo.setSecurity_token(projectVo.getSecurity_token());
		tempPo.setLast_modify_time(new Date());
		projectRepository.save(tempPo);
	}
}
