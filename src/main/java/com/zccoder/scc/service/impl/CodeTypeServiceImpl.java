package com.zccoder.scc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.zccoder.scc.repository.CodeTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.enums.EnumStatus;
import com.zccoder.scc.domain.po.CodeTypePo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.domain.vo.User;
import com.zccoder.scc.service.api.ICodeTypeService;

/**
 * 类型配置 业务功能实现类
 * @Created by ZhangCheng on 2017-08-08
 *
 */
@Service
public class CodeTypeServiceImpl implements ICodeTypeService {
	
	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Autowired
	private CodeTypeRepository codeTypeRepository;
	@Autowired
	private User user;
	
	@Override
	public ServiceMessage<List<CodeTypePo>> listCodeType(CodeTypePo codeTypePo) {
		
		final String type_code = codeTypePo.getType_code();
		final String type_name = codeTypePo.getType_name();
		final String status = codeTypePo.getStatus();

		// 构建查询条件
		Specification<CodeTypePo> querySpeci = new Specification<CodeTypePo>() {
			@Override
			public Predicate toPredicate(Root<CodeTypePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				// 如果 类型编码 不为空
				if ("" != type_code && null != type_code) {
					lstPredicates.add(cb.equal(root.get("type_code").as(String.class), type_code));
				}
				// 如果 类型名称 不为空
				if ("" != type_name && null != type_name) {
					lstPredicates.add(cb.like(root.get("type_name").as(String.class), "%" + type_name + "%"));
				}
				// 如果 状态 不为空
				if ("" != status && null != status) {
					lstPredicates.add(cb.equal(root.get("status").as(String.class), status));
				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		
		logger.info("[URC] Query code type specification is {}",querySpeci);
		// 查找符合条件的记录
		List<CodeTypePo> listProjectPo = this.codeTypeRepository.findAll(querySpeci);
		
		if(null == listProjectPo || listProjectPo.size() < 1){
			ServiceMessage<List<CodeTypePo>> respServiceMessage = new ServiceMessage<List<CodeTypePo>>(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		for(Iterator<CodeTypePo> it = listProjectPo.iterator();it.hasNext();){
			CodeTypePo poTemp = it.next();
	        if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
	        	it.remove();
	        }
	        
	    }
		
		ServiceMessage<List<CodeTypePo>> respServiceMessage = new ServiceMessage<List<CodeTypePo>>(EnumReturnData.SUCCESS_QUERY);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setRespData(listProjectPo);
		
		return respServiceMessage;
	}

	@Override
	public ServiceMessage<?> operCodeType(CodeTypePo codeTypePo, EnumOperationType operationType) {
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		
		if (EnumOperationType.SAVE.equals(operationType)) {
			
			CodeTypePo codeTypePoTempTwo = codeTypeRepository.getEffectiveCodeTypeByTypeCode(codeTypePo.getType_code());
			if ( codeTypePoTempTwo != null) {
				respServiceMessage.setEnumReturnData(EnumReturnData.PROJECT_CODE_IS_EXIST);
				return respServiceMessage;
			}
			
			logger.info("[URC] oper code type add {}",codeTypePo);
			
			codeTypePo.setCreate_author_code(user.getAuthor_code());
			codeTypePo.setCreate_author_name(user.getAuthor_name());
			codeTypePo.setCreate_time(new Date());
			codeTypePo.setLast_modify_time(new Date());
			
			codeTypeRepository.save(codeTypePo);
			respServiceMessage.setSuccess(true);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_SAVE);
			return respServiceMessage;
		}
		
		// 判断项目ID是否存在
		CodeTypePo codeTypePoTemp = getCodeTypeExist(codeTypePo);
		if(null == codeTypePoTemp){
			respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respServiceMessage;
		}
		
		if (EnumOperationType.UPDATE.equals(operationType)) {
			logger.info("[URC] Oper code type update {}",codeTypePo);
			
			codeTypePo.setLast_modify_time(new Date());
			codeTypePo.setCreate_author_code(codeTypePoTemp.getCreate_author_code());
			codeTypePo.setCreate_author_name(codeTypePoTemp.getCreate_author_name());
			codeTypePo.setCreate_time(codeTypePoTemp.getCreate_time());
			
			codeTypeRepository.save(codeTypePo);
			respServiceMessage.setSuccess(true);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_UPDATE);
			return respServiceMessage;
		}

		logger.info("[URC] Oper code type remove {}",codeTypePo);
		
		codeTypePoTemp.setLast_modify_time(new Date());
		codeTypePoTemp.setStatus(EnumStatus.DELETED.getCode());
		codeTypeRepository.save(codeTypePoTemp);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_REMOVE);
		return respServiceMessage;
	}
	
	/**
	 * 功能：获取 CodeTypePo 根据 ID
	 */
	private CodeTypePo getCodeTypeExist(CodeTypePo po){
		CodeTypePo poTemp = codeTypeRepository.findOne(po.getId());
		if(null == poTemp){
			return null;
		}
		if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
			return null;
		}
		return poTemp;
	}
}
