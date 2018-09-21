package com.zccoder.scc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.zccoder.scc.repository.CodeListRepository;
import com.zccoder.scc.repository.CodeTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.enums.EnumStatus;
import com.zccoder.scc.domain.po.CodeListPo;
import com.zccoder.scc.domain.po.CodeTypePo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.domain.vo.User;
import com.zccoder.scc.service.api.ICodeListService;

/**
 * 类型参数 业务功能实现类
 * @Created by ZhangCheng on 2017-08-08
 *
 */
@Service
public class CodeListServiceImpl implements ICodeListService{
	
	private static Logger logger = LoggerFactory.getLogger(CodeListServiceImpl.class);
	
	@Autowired
	private CodeListRepository codeListRepository;
	@Autowired
	private CodeTypeRepository codeTypeRepository;
	@Autowired
	private User user;
	
	@Override
	public ServiceMessage<List<CodeListPo>> listCodeList(CodeListPo codeListPo) {
		final String type_code = codeListPo.getType_code();
		final String code_name = codeListPo.getCode_name();
		final String status = codeListPo.getStatus();

		// 构建查询条件
		Specification<CodeListPo> querySpeci = new Specification<CodeListPo>() {
			@Override
			public Predicate toPredicate(Root<CodeListPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				// 如果 类型编码 不为空
				if ("" != type_code && null != type_code) {
					lstPredicates.add(cb.equal(root.get("type_code").as(String.class), type_code));
				}
				// 如果 参数名称 不为空
				if ("" != code_name && null != code_name) {
					lstPredicates.add(cb.like(root.get("code_name").as(String.class), "%" + code_name + "%"));
				}
				// 如果 状态 不为空
				if ("" != status && null != status) {
					lstPredicates.add(cb.equal(root.get("status").as(String.class), status));
				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		
		logger.info("[URC] Query code list specification is {}",querySpeci);
		// 查找符合条件的记录
		List<CodeListPo> listProjectPo = codeListRepository.findAll(querySpeci);
		
		if(null == listProjectPo || listProjectPo.size() < 1){
			ServiceMessage<List<CodeListPo>> respServiceMessage = new ServiceMessage<List<CodeListPo>>(EnumReturnData.DATA_IS_EMPTY); 
			return respServiceMessage;
		}
		
		for(Iterator<CodeListPo> it = listProjectPo.iterator();it.hasNext();){
			CodeListPo poTemp = it.next();
	        if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
	        	it.remove();
	        }
	    }
		
		ServiceMessage<List<CodeListPo>> respServiceMessage = new ServiceMessage<List<CodeListPo>>(EnumReturnData.SUCCESS_QUERY);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setRespData(listProjectPo);
		
		return respServiceMessage;
	}

	@Override
	public ServiceMessage<?> operCodeList(CodeListPo codeListPo, EnumOperationType operationType) {
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		
		if (EnumOperationType.SAVE.equals(operationType)) {
			logger.info("[URC] oper code list add {}",codeListPo);
			
			// 判断类型编码是否存在
			CodeTypePo codeTypePo = codeTypeRepository.getEffectiveCodeTypeByTypeCode(codeListPo.getType_code());
			if(codeTypePo == null){
				respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
				return respServiceMessage;
			}
			
			codeListPo.setCreate_author_code(user.getAuthor_code());
			codeListPo.setCreate_author_name(user.getAuthor_name());
			codeListPo.setCreate_time(new Date());
			codeListPo.setLast_modify_time(new Date());
			
			codeListRepository.save(codeListPo);
			respServiceMessage.setSuccess(true);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_SAVE);
			return respServiceMessage;
		}
		
		// 判断项目ID是否存在
		CodeListPo codeListPoTemp = getCodeListExist(codeListPo);
		if(null == codeListPoTemp){
			respServiceMessage.setEnumReturnData(EnumReturnData.PARAM_NOT_EXIST);
			return respServiceMessage;
		}
		
		if (EnumOperationType.UPDATE.equals(operationType)) {
			logger.info("[URC] Oper list type update {}",codeListPo);
			
			codeListPo.setLast_modify_time(new Date());
			codeListPo.setCreate_author_code(codeListPoTemp.getCreate_author_code());
			codeListPo.setCreate_author_name(codeListPoTemp.getCreate_author_name());
			codeListPo.setCreate_time(codeListPoTemp.getCreate_time());
			
			codeListRepository.save(codeListPo);
			respServiceMessage.setSuccess(true);
			respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_UPDATE);
			return respServiceMessage;
		}

		logger.info("[URC] Oper code list remove {}",codeListPo);
		
		codeListPoTemp.setLast_modify_time(new Date());
		codeListPoTemp.setStatus(EnumStatus.DELETED.getCode());
		codeListRepository.save(codeListPoTemp);
		respServiceMessage.setSuccess(true);
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_REMOVE);
		return respServiceMessage;
	}
	
	/**
	 * 功能：获取 CodeListPo 根据 ID
	 */
	private CodeListPo getCodeListExist(CodeListPo po){
		CodeListPo poTemp = codeListRepository.findOne(po.getId());
		if(null == poTemp){
			return null;
		}
		if(EnumStatus.DELETED.getCode().equals(poTemp.getStatus())){
			return null;
		}
		return poTemp;
	}

	@Override
	public ServiceMessage<?> writeCodeListToRedis() {
		ServiceMessage<?> respServiceMessage = new ServiceMessage<>();
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS);
		
		List<CodeListPo> list = codeListRepository.findAll();
		if(null != list && list.size() > 0){
//			redisService.saveObject("codelist", list);
		}
		return respServiceMessage;
	}

	@Override
	public ServiceMessage<List<CodeListPo>> listCodeListByRedis(CodeListPo codeListPo) {
		ServiceMessage<List<CodeListPo>> respServiceMessage = new ServiceMessage<>();
		respServiceMessage.setEnumReturnData(EnumReturnData.SUCCESS_QUERY);
		
		@SuppressWarnings("unchecked")
//		List<CodeListPo> list = (List<CodeListPo>)redisService.getObject("codelist");
		List<CodeListPo> list = null;

		if(list == null || list.size() == 0){
			respServiceMessage.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY);
			respServiceMessage.setSuccess(false);
			return respServiceMessage;
		}
		
		for(Iterator<CodeListPo> it = list.iterator();it.hasNext();){
			CodeListPo poTemp = it.next();
	        if( (!Objects.equals(poTemp.getType_code(), codeListPo.getType_code())) || 
	        	(EnumStatus.DELETED.getCode().equals(poTemp.getStatus()))){
	        	it.remove();
	        }
	    }
		
		if(list == null || list.size() == 0){
			respServiceMessage.setEnumReturnData(EnumReturnData.DATA_IS_EMPTY);
			respServiceMessage.setSuccess(false);
			return respServiceMessage;
		}
		
		respServiceMessage.setSuccess(true);
		respServiceMessage.setRespData(list);
		return respServiceMessage;
	}
}
