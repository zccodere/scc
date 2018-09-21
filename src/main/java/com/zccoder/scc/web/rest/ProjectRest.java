package com.zccoder.scc.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.zccoder.scc.domain.enums.EnumOperationType;
import com.zccoder.scc.domain.enums.EnumReturnData;
import com.zccoder.scc.domain.vo.ProjectVo;
import com.zccoder.scc.domain.vo.ReturnDataVo;
import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.IProjectService;
import com.zccoder.scc.util.DateUtils;
import com.zccoder.scc.util.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.ProjectPo;
import com.zccoder.scc.domain.po.PropertiesPo;

/**
 * 项目配置 rest服务
 * @Created by ZhangCheng on 2017-06-16
 * 
 */
@RestController
@RequestMapping("/project")
public class ProjectRest {
	
	@Autowired
	private IProjectService projectService;

	private static Logger logger = LoggerFactory.getLogger(ProjectRest.class);
	
	/**
	 * 项目配置查询服务
	 * @param projectVo
	 * @return
	 */
	@PostMapping("/query")
	public Object listProject(ProjectVo projectVo){
		logger.info("[URC] query project request param is {}",projectVo);
		
		ProjectPo projectPo = new ProjectPo();
		BeanUtils.copyProperties(projectVo, projectPo);
		ServiceMessage<List<ProjectPo>> listProjectPo = projectService.listProjectPo(projectPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(listProjectPo.getEnumReturnData());
		
		List<ProjectPo> ProjectPos = listProjectPo.getRespData();
		List<ProjectVo> projectVos = new ArrayList<ProjectVo>();
		if(listProjectPo.isSuccess()){
			for (ProjectPo projectPoTemp : ProjectPos) {
				ProjectVo projectVoTemp= new ProjectVo();
				BeanUtils.copyProperties(projectPoTemp, projectVoTemp);
				projectVoTemp.setCreate_time(DateUtils.dateToStr(projectPoTemp.getCreate_time()));
				projectVoTemp.setLast_modify_time(DateUtils.dateToStr(projectPoTemp.getLast_modify_time()));
				projectVoTemp.setStatus_name("1".equals(projectVoTemp.getStatus()) ? "有效" : "无效");
				projectVos.add(projectVoTemp);
			}
			returnDataVo.addRespData("project_list", projectVos);
		}
		
		logger.info("[URC] query project response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 项目配置查询服务
	 * @param projectVo
	 * @return
	 */
	@PostMapping("/findone")
	public Object findone(ProjectVo projectVo){
		logger.info("[URC] query project request param is {}",projectVo);
		ProjectPo projectPo = new ProjectPo();
		BeanUtils.copyProperties(projectVo, projectPo);
		ServiceMessage<ProjectPo> listProjectPo = projectService.findOne(projectPo);
		ReturnDataVo returnDataVo = new ReturnDataVo(listProjectPo.getEnumReturnData());
		
		if(listProjectPo.isSuccess()){
			ProjectPo projectPoTemp = listProjectPo.getRespData();
			ProjectVo projectVoTemp= new ProjectVo();
			BeanUtils.copyProperties(projectPoTemp, projectVoTemp);
			projectVoTemp.setCreate_time(DateUtils.dateToStr(projectPoTemp.getCreate_time()));
			projectVoTemp.setLast_modify_time(DateUtils.dateToStr(projectPoTemp.getLast_modify_time()));
			projectVoTemp.setStatus_name("1".equals(projectVoTemp.getStatus()) ? "有效" : "无效");
			returnDataVo.addRespData("project", projectVoTemp);
		}
		
		logger.info("[URC] query project response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	// 重置token
	@PostMapping("/resettoken")
	public Object resetToken(){
		ReturnDataVo returnDataVo = new ReturnDataVo(EnumReturnData.SUCCESS);
		returnDataVo.addRespData("token", IdUtils.getUuid());
		return returnDataVo;
	}
	
	// 保存token
	@PostMapping("/savesecurity")
	public Object saveSecurity(ProjectVo projectVo){
		
		System.out.println(projectVo);
		projectService.saveSecurity(projectVo);
		ReturnDataVo returnDataVo = new ReturnDataVo(EnumReturnData.SUCCESS);
		
		return returnDataVo;
	}
	
	@GetMapping("/config/{project_code}")
	public void getConfig(@PathVariable("project_code") String project_code,
			HttpServletResponse response){
		
		logger.info("[URC] Project code is {} get project config properties.",project_code);
		
		ProjectPo projectPo = new ProjectPo();
		projectPo.setProject_code(project_code);
		ServiceMessage<List<PropertiesPo>> respMessage = projectService.listProperties(projectPo);
		
		if(respMessage.isSuccess()){
			List<PropertiesPo> listProperties = respMessage.getRespData();
			try {
				for (PropertiesPo propertiesPoTemp : listProperties) {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().println(propertiesPoTemp.getProperties_key()+" = "+propertiesPoTemp.getProperties_value());
				}
			}catch (IOException e) {
				logger.error("[URC] write project config properties error {}",e.getMessage());
			}
		}
	}
	
	/**
	 * 项目配置维护服务
	 * @param projectVo
	 * @param oper_type
	 * @return
	 */
	@PostMapping("/oper")
	public ReturnDataVo operProject(ProjectVo projectVo,String oper_type){
		logger.info("[URC] oper project type is {}, request param is {}",oper_type,projectVo);
		
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
		
		ProjectPo projectPo = new ProjectPo();
		BeanUtils.copyProperties(projectVo, projectPo);
		ServiceMessage<?> result = projectService.operProjectPo(projectPo, operationType);
		ReturnDataVo returnDataVo = new ReturnDataVo(result.getEnumReturnData());
		
		logger.info("[URC] oper project response status is {}",returnDataVo);
		return returnDataVo;
	}
	
	/**
	 * 将业务参数写入redis缓存
	 * @param projectVo
	 * @return
	 */
	@PostMapping("/refresh")
	public ReturnDataVo writeBusinessPropertirsToRedis(ProjectVo projectVo){
		
		ProjectPo projectPo = new ProjectPo();
		BeanUtils.copyProperties(projectVo, projectPo);
		ServiceMessage<List<PropertiesPo>> result = projectService.listProperties(projectPo);
		
		if(!result.isSuccess()){
			return new ReturnDataVo(EnumReturnData.SYS_EXCEPTION);
		}
		
		Map<String,Object> propMap = new HashMap<String,Object>();
		
		for (PropertiesPo propertiesPoTemp : result.getRespData()) {
			propMap.put(propertiesPoTemp.getProperties_key(), propertiesPoTemp.getProperties_value());
		}
		
//		redisService.saveMap("uc_config_"+projectVo.getProject_code(), propMap);
//
//		Map<String, Object> map = redisService.getMap("uc_config_"+projectVo.getProject_code());
//		logger.info("参数：{}",map);
		
		return new ReturnDataVo(EnumReturnData.SUCCESS);
	}
}
