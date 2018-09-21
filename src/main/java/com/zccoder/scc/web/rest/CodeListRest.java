package com.zccoder.scc.web.rest;

import java.util.ArrayList;
import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.vo.CodeListVo;
import com.zccoder.scc.domain.vo.ReturnDataVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.ICodeListService;
import com.zccoder.scc.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.CodeListPo;

/**
 * 类型参数配置 rest服务
 * @Created by ZhangCheng on 2017-06-16
 * 
 */
@RestController
@RequestMapping("/rest/codelist")
public class CodeListRest {
	
	@Autowired
	private ICodeListService codelistService;
	
	private static Logger logger = LoggerFactory.getLogger(CodeListRest.class);
	
	/**
	 * 类型参数配置查询服务（从redis获取）
	 */
	@PostMapping("/querybyredis")
	public ReturnDataVo listCodeListByRedis(CodeListVo codelistVo){
		CodeListPo codelistPo = new CodeListPo();
		BeanUtils.copyProperties(codelistVo, codelistPo);
		ServiceMessage<List<CodeListPo>> listCodeListPo = codelistService.listCodeListByRedis(codelistPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(listCodeListPo.getEnumReturnData());
		
		List<CodeListPo> CodeListPos = listCodeListPo.getRespData();
		List<CodeListVo> codelistVos = new ArrayList<CodeListVo>();
		if(listCodeListPo.isSuccess()){
			for (CodeListPo codelistPoTemp : CodeListPos) {
				CodeListVo codelistVoTemp= new CodeListVo();
				BeanUtils.copyProperties(codelistPoTemp, codelistVoTemp);
				codelistVoTemp.setCreate_time(DateUtils.dateToStr(codelistPoTemp.getCreate_time()));
				codelistVoTemp.setLast_modify_time(DateUtils.dateToStr(codelistPoTemp.getLast_modify_time()));
				codelistVoTemp.setStatus_name("1".equals(codelistVoTemp.getStatus()) ? "有效" : "无效");
				codelistVos.add(codelistVoTemp);
			}
			returnDataVo.addRespData("codelist_list", codelistVos);
		}
		logger.info("[URC] query codelist byredis response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 类型参数配置查询服务
	 * @param codelistVo
	 * @return
	 */
	@PostMapping("/query")
	public ReturnDataVo listCodeList(CodeListVo codelistVo){
		logger.info("[URC] query codelist request param is {}",codelistVo);
		
		CodeListPo codelistPo = new CodeListPo();
		BeanUtils.copyProperties(codelistVo, codelistPo);
		ServiceMessage<List<CodeListPo>> listCodeListPo = codelistService.listCodeList(codelistPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(listCodeListPo.getEnumReturnData());
		
		List<CodeListPo> CodeListPos = listCodeListPo.getRespData();
		List<CodeListVo> codelistVos = new ArrayList<CodeListVo>();
		if(listCodeListPo.isSuccess()){
			for (CodeListPo codelistPoTemp : CodeListPos) {
				CodeListVo codelistVoTemp= new CodeListVo();
				BeanUtils.copyProperties(codelistPoTemp, codelistVoTemp);
				codelistVoTemp.setCreate_time(DateUtils.dateToStr(codelistPoTemp.getCreate_time()));
				codelistVoTemp.setLast_modify_time(DateUtils.dateToStr(codelistPoTemp.getLast_modify_time()));
				codelistVoTemp.setStatus_name("1".equals(codelistVoTemp.getStatus()) ? "有效" : "无效");
				codelistVos.add(codelistVoTemp);
			}
			returnDataVo.addRespData("codelist_list", codelistVos);
		}
		logger.info("[URC] query codelist response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 类型参数配置维护服务
	 * @param codelistVo
	 * @param oper_type
	 * @return
	 */
	@PostMapping("/oper")
	public ReturnDataVo operCodeList(CodeListVo codelistVo,String oper_type){
		logger.info("[URC] oper codelist type is {}, request param is {}",oper_type,codelistVo);
		
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
			logger.info("[URC] oper priject type is not support, type is {}",oper_type);
			return new ReturnDataVo(EnumReturnData.OPER_TYPE_NOT_EXIST);
		}
		
		CodeListPo codelistPo = new CodeListPo();
		BeanUtils.copyProperties(codelistVo, codelistPo);
		ServiceMessage<?> result = codelistService.operCodeList(codelistPo, operationType);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		logger.info("[URC] oper codelist response status is {}",returnDataVo);
		return returnDataVo;
	}
}
