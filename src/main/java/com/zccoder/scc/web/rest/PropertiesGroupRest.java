package com.zccoder.scc.web.rest;

import java.util.ArrayList;
import java.util.List;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.vo.PropertiesGroupVo;
import com.zccoder.scc.domain.vo.ReturnDataVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.IPropertiesGroupService;
import com.zccoder.scc.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.PropertiesGroupPo;

/**
 * 参数属组 rest服务
 * @Created by ZhangCheng on 2017-08-07
 * 
 */
@RestController
@RequestMapping("/rest/propertiesgroup")
public class PropertiesGroupRest {
	
	@Autowired
	private IPropertiesGroupService propertiesGroupService;
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesGroupRest.class);
	
	/**
	 * 参数属组查询服务
	 * @param propertiesGroupVo
	 * @return
	 */
	@PostMapping("/query")
	public ReturnDataVo queryPropertiesGroup(PropertiesGroupVo propertiesGroupVo){
		logger.info("[URC] query properties group request param is {}",propertiesGroupVo);
		
		PropertiesGroupPo propertiesGroupPo = new PropertiesGroupPo();
		BeanUtils.copyProperties(propertiesGroupVo, propertiesGroupPo);
		ServiceMessage<List<PropertiesGroupPo>> result = propertiesGroupService.listPropertiesGroupPo(propertiesGroupPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		if(result.isSuccess()){
			List<PropertiesGroupPo> listPropertiesGroupPo = result.getRespData();
			List<PropertiesGroupVo> listPropertiesGroupVo = new ArrayList<PropertiesGroupVo>();
			for (PropertiesGroupPo propertiesGroupPoTemp : listPropertiesGroupPo) {
				PropertiesGroupVo propertiesGroupVoTemp = new PropertiesGroupVo();
				BeanUtils.copyProperties(propertiesGroupPoTemp, propertiesGroupVoTemp);
				propertiesGroupVoTemp.setCreate_time(DateUtils.dateToStr(propertiesGroupPoTemp.getCreate_time()));
				propertiesGroupVoTemp.setLast_modify_time(DateUtils.dateToStr(propertiesGroupPoTemp.getLast_modify_time()));
				propertiesGroupVoTemp.setStatus_name("1".equals(propertiesGroupVoTemp.getStatus()) ? "有效" : "无效");
				listPropertiesGroupVo.add(propertiesGroupVoTemp);
			}
			returnDataVo.addRespData("properties_group_list", listPropertiesGroupVo);
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
	public ReturnDataVo operPropertiesGroup(PropertiesGroupVo propertiesGroupVo,String oper_type){
		logger.info("[URC] oper properties group type is {}, request param is {}",oper_type,propertiesGroupVo);
		
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
		
		PropertiesGroupPo propertiesGroupPo = new PropertiesGroupPo();
		BeanUtils.copyProperties(propertiesGroupVo, propertiesGroupPo);
		ServiceMessage<?> result = this.propertiesGroupService.operPropertiesGroupPo(propertiesGroupPo, operationType);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		logger.info("[URC] oper properties group response status is {}",returnDataVo);
		return returnDataVo;
	}
}
