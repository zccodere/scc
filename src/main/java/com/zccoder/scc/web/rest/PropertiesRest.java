package com.zccoder.scc.web.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.vo.PropertiesGroupVo;
import com.zccoder.scc.domain.vo.PropertiesVo;
import com.zccoder.scc.domain.vo.ReturnDataVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.IPropertiesService;
import com.zccoder.scc.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.PropertiesPo;

/**
 * 项目参数 rest服务
 * @Created by ZhangCheng on 2017-06-20
 * 
 */
@RestController
@RequestMapping("/rest/properties")
public class PropertiesRest {
	
	@Autowired
	private IPropertiesService propertiesService;
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesRest.class);
	
	/**
	 * 项目参数查询服务
	 * @param propertiesVo
	 * @return
	 */
	@PostMapping("/query")
	public ReturnDataVo queryProperties(PropertiesVo propertiesVo){
		logger.info("[URC] query properties request param is {}",propertiesVo);
		
		PropertiesPo propertiesPo = new PropertiesPo();
		BeanUtils.copyProperties(propertiesVo, propertiesPo);
		ServiceMessage<List<PropertiesPo>> result = propertiesService.listPropertiesPo(propertiesPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		if(result.isSuccess()){
			List<PropertiesPo> listPropertiesPo = result.getRespData();
			List<PropertiesVo> listPropertiesVo = new ArrayList<PropertiesVo>();
			for (PropertiesPo propertiesPoTemp : listPropertiesPo) {
				PropertiesVo propertiesVoTemp = new PropertiesVo();
				BeanUtils.copyProperties(propertiesPoTemp, propertiesVoTemp);
				propertiesVoTemp.setCreate_time(DateUtils.dateToStr(propertiesPoTemp.getCreate_time()));
				propertiesVoTemp.setLast_modify_time(DateUtils.dateToStr(propertiesPoTemp.getLast_modify_time()));
				propertiesVoTemp.setStatus_name("1".equals(propertiesVoTemp.getStatus()) ? "有效" : "无效");
				listPropertiesVo.add(propertiesVoTemp);
			}
			returnDataVo.addRespData("properties_list", listPropertiesVo);
		}
		
		logger.info("[URC] query properties response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 项目参数维护服务
	 * @param propertiesVo
	 * @param oper_type
	 * @return
	 */
	@PostMapping("/oper")
	public ReturnDataVo operProperties(PropertiesVo propertiesVo,String oper_type){
		logger.info("[URC] oper properties type is {}, request param is {}",oper_type,propertiesVo);
		
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
			logger.info("[URC] oper properties type is not support, type is {}",oper_type);
			return new ReturnDataVo(EnumReturnData.OPER_TYPE_NOT_EXIST);
		}
		
		PropertiesPo propertiesPo = new PropertiesPo();
		BeanUtils.copyProperties(propertiesVo, propertiesPo);
		ServiceMessage<?> result = this.propertiesService.operPropertiesPo(propertiesPo, operationType);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		logger.info("[URC] oper properties response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 查询已配置参数的参数组
	 * @param propertiesVo
	 * @return
	 */
	@PostMapping("/queryHasGroup")
	public ReturnDataVo queryHasPropertiesProject(){
		PropertiesPo propertiesPo = new PropertiesPo();
		ServiceMessage<List<PropertiesPo>> result = propertiesService.listPropertiesPo(propertiesPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		if(result.isSuccess()){
			List<PropertiesPo> listPropertiesPo = result.getRespData();
			Set<PropertiesGroupVo> groupList = new HashSet<PropertiesGroupVo>();
			for (PropertiesPo propertiesPoTemp : listPropertiesPo) {
				PropertiesGroupVo groupTemp = new PropertiesGroupVo();
				groupTemp.setId((Long.parseLong(propertiesPoTemp.getGroup_id())));
				groupTemp.setGroup_name(propertiesPoTemp.getGroup_name());
				
				groupList.add(groupTemp);
			}
			returnDataVo.addRespData("prop_group_list", groupList);
		}
		
		logger.info("[URC] query for project already have properties status is {}",returnDataVo);
		return returnDataVo;
	}
}
