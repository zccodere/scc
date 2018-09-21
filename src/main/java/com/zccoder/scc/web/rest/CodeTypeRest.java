package com.zccoder.scc.web.rest;

import java.util.ArrayList;
import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.vo.CodeTypeVo;
import com.zccoder.scc.domain.vo.ReturnDataVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.ICodeListService;
import com.zccoder.scc.service.api.ICodeTypeService;
import com.zccoder.scc.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.CodeTypePo;

/**
 * 类型配置 rest服务
 * @Created by ZhangCheng on 2017-08-08
 * 
 */
@RestController
@RequestMapping("/rest/codeType")
public class CodeTypeRest {
	
	@Autowired
	private ICodeTypeService codeTypeService;
	@Autowired
	private ICodeListService codeListService;
	
	private static Logger logger = LoggerFactory.getLogger(CodeTypeRest.class);
	
	/**
	 * 类型配置查询服务
	 * @param codeTypeVo
	 * @return
	 */
	@PostMapping("/query")
	public Object listCodeType(CodeTypeVo codeTypeVo){
		logger.info("[URC] query codeType request param is {}",codeTypeVo);
		
		CodeTypePo codeTypePo = new CodeTypePo();
		BeanUtils.copyProperties(codeTypeVo, codeTypePo);
		ServiceMessage<List<CodeTypePo>> listCodeTypePo = codeTypeService.listCodeType(codeTypePo);
		ReturnDataVo returnDataVo = new ReturnDataVo(listCodeTypePo.getEnumReturnData());
		
		List<CodeTypePo> CodeTypePos = listCodeTypePo.getRespData();
		List<CodeTypeVo> codeTypeVos = new ArrayList<CodeTypeVo>();
		if(listCodeTypePo.isSuccess()){
			for (CodeTypePo codeTypePoTemp : CodeTypePos) {
				CodeTypeVo codeTypeVoTemp= new CodeTypeVo();
				BeanUtils.copyProperties(codeTypePoTemp, codeTypeVoTemp);
				codeTypeVoTemp.setCreate_time(DateUtils.dateToStr(codeTypePoTemp.getCreate_time()));
				codeTypeVoTemp.setLast_modify_time(DateUtils.dateToStr(codeTypePoTemp.getLast_modify_time()));
				codeTypeVoTemp.setStatus_name("1".equals(codeTypeVoTemp.getStatus()) ? "有效" : "无效");
				codeTypeVos.add(codeTypeVoTemp);
			}
			returnDataVo.addRespData("codetype_list", codeTypeVos);
		}
		
		logger.info("[URC] query codeType response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 类型配置维护服务
	 * @param codeTypeVo
	 * @param oper_type
	 * @return
	 */
	@PostMapping("/oper")
	public ReturnDataVo operCodeType(CodeTypeVo codeTypeVo,String oper_type){
		logger.info("[URC] oper codeType type is {}, request param is {}",oper_type,codeTypeVo);
		
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
		
		CodeTypePo codeTypePo = new CodeTypePo();
		BeanUtils.copyProperties(codeTypeVo, codeTypePo);
		ServiceMessage<?> result = codeTypeService.operCodeType(codeTypePo, operationType);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		logger.info("[URC] oper codeType response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 类型配置维护服务
	 * @param codeTypeVo
	 * @param oper_type
	 * @return
	 */
	@PostMapping("/refresh")
	public ReturnDataVo refreshCodeType(){
		ServiceMessage<?> result = codeListService.writeCodeListToRedis();
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		return returnDataVo;
	}
}
