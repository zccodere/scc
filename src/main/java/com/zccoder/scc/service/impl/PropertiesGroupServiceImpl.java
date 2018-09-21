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
import com.zccoder.scc.repository.PropertiesGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zccoder.scc.domain.po.PropertiesGroupPo;
import com.zccoder.scc.service.api.IPropertiesGroupService;

/**
 * 参数属组 业务功能实现类
 * @Created by ZhangCheng on 2017-08-07
 *
 */
@Service
public class PropertiesGroupServiceImpl implements IPropertiesGroupService {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesGroupServiceImpl.class);
	
	@Autowired
	private PropertiesGroupRepository propertiesGroupRepository;
	
	@Autowired
	private User user;
	
	@Override
	public ServiceMessage<List<PropertiesGroupPo>> listPropertiesGroupPo(PropertiesGroupPo propertiesGroupPo) {
		
		ServiceMessage<List<PropertiesGroupPo>> respServiceMessage = new ServiceMessage<List<PropertiesGroupPo>>(EnumReturnData.SUCCESS);
		final String id = propertiesGroupPo.getId()==null?"":propertiesGroupPo.getId().toString();
		final String group_name = propertiesGroupPo.getGroup_name();
		final String code_id = propertiesGroupPo.getCode_id();
		final String status = propertiesGroupPo.getStatus();
		
		// 构建查询条件
		Specification<PropertiesGroupPo> querySpeci = new Specification<PropertiesGroupPo>() {
			@Override
			public Predicate toPredicate(Root<PropertiesGroupPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				// 如果 参数组ID 不为空
				if ("" != id && null != id) {
					lstPredicates.add(cb.equal(root.get("id").as(String.class), id));
				}
				// 如果 参数组类型 不为空
				if ("" != code_id && null != code_id) {
					lstPredicates.add(cb.equal(root.get("code_id").as(String.class), code_id));
				}
				// 如果 参数组名称 不为空
				if ("" != group_name && null != group_name) {
					lstPredicates.add(cb.like(root.get("group_name").as(String.class), "%" + group_name + "%"));
				}
				// 如果 状态 不为空
				if ("" != status && null != status) {
					lstPredicates.add(cb.equal(root.get("status").as(String.class), status));
				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		
		logger.info("[URC] query properties group specification is {}",querySpeci);
		// 查找符合条件的记录
		List<PropertiesGroupPo> listPropertiesGroupPo = this.propertiesGroupRepository.findAll(querySpeci);
		
		if(null == listPropertiesGroupPo || listPropertiesGroupPo.size() < 1){
			respServiceMessage.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		for(Iterator<PropertiesGroupPo> it = listPropertiesGroupPo.iterator();it.hasNext();){
			PropertiesGroupPo poTemp = it.next();
	        if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
	        	it.remove();
	        }
	    }
		
		respServiceMessage.setRespData(listPropertiesGroupPo);
		respServiceMessage.setSuccess(true);
		
		return respServiceMessage;
	}

	@Override
	public ServiceMessage<?> operPropertiesGroupPo(PropertiesGroupPo propertiesGroupPo, EnumOperationType operationType) {
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		
		if (EnumOperationType.SAVE.equals(operationType)) {
			logger.debug("[URC] oper properties group add {}",propertiesGroupPo);
			
			propertiesGroupPo.setCreate_author_code(user.getAuthor_code());
			propertiesGroupPo.setCreate_author_name(user.getAuthor_name());
			propertiesGroupPo.setCreate_time(new Date());
			propertiesGroupPo.setLast_modify_time(new Date());
			
			this.propertiesGroupRepository.save(propertiesGroupPo);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_SAVE);
			respServiceMessage.setSuccess(true);
			return respServiceMessage;
		}
		
		// 判断参数组ID是否存在
		PropertiesGroupPo propertiesGroupPoTemp = getPropertiesGroupExist(propertiesGroupPo);
		if(null == propertiesGroupPoTemp){
			respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respServiceMessage;
		}
		
		if (EnumOperationType.UPDATE.equals(operationType)) {
			logger.debug("[URC] oper properties group update {}",propertiesGroupPo);
			
			propertiesGroupPo.setCreate_author_code(propertiesGroupPoTemp.getCreate_author_code());
			propertiesGroupPo.setCreate_author_name(propertiesGroupPoTemp.getCreate_author_name());
			propertiesGroupPo.setCreate_time(propertiesGroupPoTemp.getCreate_time());
			propertiesGroupPo.setLast_modify_time(new Date());
			
			this.propertiesGroupRepository.save(propertiesGroupPo);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_UPDATE);
			respServiceMessage.setSuccess(true);
			return respServiceMessage;
			
		}

		logger.debug("[URC] oper properties group remove {}",propertiesGroupPo);
		
		propertiesGroupPoTemp.setLast_modify_time(new Date());
		propertiesGroupPoTemp.setStatus(EnumStatus.DELETED.getCode());
		this.propertiesGroupRepository.save(propertiesGroupPoTemp);
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_REMOVE);
		respServiceMessage.setSuccess(true);
		return respServiceMessage;
	}
	
	/**
	 * 功能：获取 PropertiesGroupPo 根据 ID
	 */
	private PropertiesGroupPo getPropertiesGroupExist(PropertiesGroupPo propertiesGroupPo){
		PropertiesGroupPo propertiesGroupPoTemp = this.propertiesGroupRepository.findOne(propertiesGroupPo.getId());
		if(null == propertiesGroupPoTemp){
			return null;
		}
		if(EnumStatus.DELETED.getCode().equals(propertiesGroupPoTemp.getStatus())){
			return null;
		}
		return propertiesGroupPoTemp;
	}
}
