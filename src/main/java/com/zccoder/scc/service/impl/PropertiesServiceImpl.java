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
import com.zccoder.scc.domain.enums.EnumPropertiesType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.enums.EnumStatus;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.domain.vo.User;
import com.zccoder.scc.repository.PropertiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zccoder.scc.domain.po.PropertiesPo;
import com.zccoder.scc.service.api.IPropertiesService;

/**
 * 项目参数 业务功能实现类
 * @Created by ZhangCheng on 2017-06-20
 *
 */
@Service
public class PropertiesServiceImpl implements IPropertiesService {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesServiceImpl.class);
	
	@Autowired
	private PropertiesRepository propertiesRepository;
	@Autowired
	private User user;
	
	@Override
	public ServiceMessage<List<PropertiesPo>> listPropertiesPo(PropertiesPo propertiesPo) {
		
		ServiceMessage<List<PropertiesPo>> respServiceMessage = new ServiceMessage<List<PropertiesPo>>(EnumReturnData.SUCCESS);
		
		final String groupId = propertiesPo.getGroup_id();
		final String propertiesName = propertiesPo.getProperties_name();
		final String propertieskey = propertiesPo.getProperties_key();
		final String status = propertiesPo.getStatus();

		// 构建查询条件
		Specification<PropertiesPo> querySpeci = new Specification<PropertiesPo>() {
			@Override
			public Predicate toPredicate(Root<PropertiesPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				// 如果 参数组号 不为空
				if ("" != groupId && null != groupId) {
					lstPredicates.add(cb.equal(root.get("group_id").as(String.class), groupId));
				}
				// 如果 参数名称 不为空
				if ("" != propertiesName && null != propertiesName) {
					lstPredicates.add(cb.like(root.get("properties_name").as(String.class), "%" + propertiesName + "%"));
				}
				// 如果 参数名称 不为空
				if ("" != propertieskey && null != propertieskey) {
					lstPredicates.add(cb.like(root.get("properties_key").as(String.class), "%" + propertieskey + "%"));
				}
				// 如果 状态 不为空
				if ("" != status && null != status) {
					lstPredicates.add(cb.equal(root.get("status").as(String.class), status));
				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		
		logger.info("[URC] query properties specification is {}",querySpeci);
		// 查找符合条件的记录
		List<PropertiesPo> listPropertiesPo = this.propertiesRepository.findAll(querySpeci);
		
		if(null == listPropertiesPo || listPropertiesPo.size() < 1){
			respServiceMessage.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		for(Iterator<PropertiesPo> it = listPropertiesPo.iterator();it.hasNext();){
			PropertiesPo poTemp = it.next();
	        if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
	        	it.remove();
	        }
	    }
		
		respServiceMessage.setRespData(listPropertiesPo);
		respServiceMessage.setSuccess(true);
		
		return respServiceMessage;
	}

	@Override
	public ServiceMessage<?> operPropertiesPo(PropertiesPo propertiesPo, EnumOperationType operationType){
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		
		if (EnumOperationType.SAVE.equals(operationType)) {
			logger.debug("[URC] oper properties add {}",propertiesPo);
			
			// 同一个参数组下参数键是否已存在
			PropertiesPo propertiesPoTempTwo = this.propertiesRepository.getPropertiesPoByCodeAndKey(propertiesPo.getGroup_id(), propertiesPo.getProperties_key());
			if(null != propertiesPoTempTwo && EnumStatus.EFFECTIVE.getCode().equals(propertiesPoTempTwo.getStatus())){
				respServiceMessage.setEnumReturnData(EnumReturnData.PROPERTIES_KEY_IS_EXIST);
				return respServiceMessage;
			}
			
			propertiesPo.setCreate_author_code(user.getAuthor_code());
			propertiesPo.setCreate_author_name(user.getAuthor_name());
			propertiesPo.setCreate_time(new Date());
			propertiesPo.setLast_modify_time(new Date());
			propertiesPo.setProperties_type(EnumPropertiesType.SYS_PARAM.getDesc());
			
			this.propertiesRepository.save(propertiesPo);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_SAVE);
			respServiceMessage.setSuccess(true);
			return respServiceMessage;
		}
		
		// 判断参数ID是否存在
		PropertiesPo propertiesPoTemp = getPropertiesExist(propertiesPo);
		if(null == propertiesPoTemp){
			respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respServiceMessage;
		}
		
		if (EnumOperationType.UPDATE.equals(operationType)) {
			logger.debug("[URC] oper properties update {}",propertiesPo);
			
			propertiesPoTemp.setGroup_id(propertiesPo.getGroup_id());
			propertiesPoTemp.setGroup_name(propertiesPo.getGroup_name());
			propertiesPoTemp.setProperties_name(propertiesPo.getProperties_name());
			propertiesPoTemp.setProperties_key(propertiesPo.getProperties_key());
			propertiesPoTemp.setProperties_value(propertiesPo.getProperties_value());
			propertiesPoTemp.setProperties_desc(propertiesPo.getProperties_desc());
			propertiesPoTemp.setLast_modify_time(new Date());
			
			this.propertiesRepository.save(propertiesPoTemp);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_UPDATE);
			respServiceMessage.setSuccess(true);
			return respServiceMessage;
			
		}

		logger.debug("[URC] oper properties remove {}",propertiesPo);
		
		propertiesPoTemp.setLast_modify_time(new Date());
		propertiesPoTemp.setStatus(EnumStatus.DELETED.getCode());
		this.propertiesRepository.save(propertiesPoTemp);
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_REMOVE);
		respServiceMessage.setSuccess(true);
		return respServiceMessage;
		
	}
	
	/**
	 * 功能：获取 Properties 根据 ID
	 */
	private PropertiesPo getPropertiesExist(PropertiesPo propertiesPo){
		PropertiesPo propertiesPoTemp = this.propertiesRepository.findOne(propertiesPo.getId());
		if(null == propertiesPoTemp){
			return null;
		}
		if(EnumStatus.DELETED.getCode().equals(propertiesPoTemp.getStatus())){
			return null;
		}
		return propertiesPoTemp;
	}
}
