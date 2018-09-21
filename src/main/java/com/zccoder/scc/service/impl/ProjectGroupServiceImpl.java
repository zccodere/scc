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
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.domain.vo.User;
import com.zccoder.scc.repository.ProjectGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zccoder.scc.domain.po.ProjectGroupPo;
import com.zccoder.scc.service.api.IProjectGroupService;

/**
 * 项目属组 业务功能实现类
 * @Created by ZhangCheng on 2017-08-14
 *
 */
@Service
public class ProjectGroupServiceImpl implements IProjectGroupService {
	
	private static Logger logger = LoggerFactory.getLogger(ProjectGroupServiceImpl.class);
	
	@Autowired
	private ProjectGroupRepository projectGroupRepository;
	
	@Autowired
	private User user;
	
	@Override
	public ServiceMessage<List<ProjectGroupPo>> listProjectGroupPo(ProjectGroupPo projectGroupPo) {
		
		ServiceMessage<List<ProjectGroupPo>> respServiceMessage = new ServiceMessage<List<ProjectGroupPo>>(EnumReturnData.SUCCESS);
		final String project_code = projectGroupPo.getProject_code();
		final String group_id = projectGroupPo.getGroup_id();
		final String status = projectGroupPo.getStatus();
		
		// 构建查询条件
		Specification<ProjectGroupPo> querySpeci = new Specification<ProjectGroupPo>() {
			@Override
			public Predicate toPredicate(Root<ProjectGroupPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				// 如果 参数组ID 不为空
				if ("" != project_code && null != project_code) {
					lstPredicates.add(cb.equal(root.get("project_code").as(String.class), project_code));
				}
				// 如果 参数组名称 不为空
				if ("" != group_id && null != group_id) {
					lstPredicates.add(cb.equal(root.get("group_id").as(String.class),group_id));
				}
				// 如果 状态 不为空
				if ("" != status && null != status) {
					lstPredicates.add(cb.equal(root.get("status").as(String.class), status));
				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		
		// 查找符合条件的记录
		List<ProjectGroupPo> listProjectGroupPo = this.projectGroupRepository.findAll(querySpeci);
		
		if(null == listProjectGroupPo || listProjectGroupPo.size() < 1){
			respServiceMessage.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		for(Iterator<ProjectGroupPo> it = listProjectGroupPo.iterator();it.hasNext();){
			ProjectGroupPo poTemp = it.next();
	        if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
	        	it.remove();
	        }
	    }
		
		if(null == listProjectGroupPo || listProjectGroupPo.size() < 1){
			respServiceMessage.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		respServiceMessage.setRespData(listProjectGroupPo);
		respServiceMessage.setSuccess(true);
		
		return respServiceMessage;
	}

	@Override
	public ServiceMessage<?> operProjectGroupPo(ProjectGroupPo projectGroupPo, EnumOperationType operationType) {
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		
		if (EnumOperationType.SAVE.equals(operationType)) {
			logger.debug("[URC] oper properties group add {}",projectGroupPo);
			
			ProjectGroupPo temp = new ProjectGroupPo();
			temp.setProject_code(projectGroupPo.getProject_code());
			temp.setGroup_id(projectGroupPo.getGroup_id());
			if(listProjectGroupPo(temp).isSuccess()){
				respServiceMessage.setEnumReturnData(EnumReturnData.PROPERTIES_GROUP_IS_EXIST);
				return respServiceMessage;
			}
			
			projectGroupPo.setCreate_author_code(user.getAuthor_code());
			projectGroupPo.setCreate_author_name(user.getAuthor_name());
			projectGroupPo.setCreate_time(new Date());
			projectGroupPo.setLast_modify_time(new Date());
			
			this.projectGroupRepository.save(projectGroupPo);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_SAVE);
			respServiceMessage.setSuccess(true);
			return respServiceMessage;
		}
		
		// 判断参数组ID是否存在
		ProjectGroupPo projectGroupPoTemp = getProjectGroupExist(projectGroupPo);
		if(null == projectGroupPoTemp){
			respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respServiceMessage;
		}
		
		if (EnumOperationType.UPDATE.equals(operationType)) {
			logger.debug("[URC] oper properties group update {}",projectGroupPo);
			
			projectGroupPo.setCreate_author_code(projectGroupPoTemp.getCreate_author_code());
			projectGroupPo.setCreate_author_name(projectGroupPoTemp.getCreate_author_name());
			projectGroupPo.setCreate_time(projectGroupPoTemp.getCreate_time());
			projectGroupPo.setLast_modify_time(new Date());
			
			this.projectGroupRepository.save(projectGroupPo);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_UPDATE);
			respServiceMessage.setSuccess(true);
			return respServiceMessage;
			
		}

		logger.debug("[URC] oper properties group remove {}",projectGroupPo);
		
		projectGroupPoTemp.setLast_modify_time(new Date());
		projectGroupPoTemp.setStatus(EnumStatus.DELETED.getCode());
		this.projectGroupRepository.save(projectGroupPoTemp);
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_REMOVE);
		respServiceMessage.setSuccess(true);
		return respServiceMessage;
	}
	
	/**
	 * 功能：获取 ProjectGroupPo 根据 ID
	 */
	private ProjectGroupPo getProjectGroupExist(ProjectGroupPo projectGroupPo){
		ProjectGroupPo projectGroupPoTemp = this.projectGroupRepository.findOne(projectGroupPo.getId());
		if(null == projectGroupPoTemp){
			return null;
		}
		if(EnumStatus.DELETED.getCode().equals(projectGroupPoTemp.getStatus())){
			return null;
		}
		return projectGroupPoTemp;
	}
}
