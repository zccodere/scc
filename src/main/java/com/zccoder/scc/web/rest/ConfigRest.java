package com.zccoder.scc.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import com.zccoder.scc.domain.vo.ServiceMessage;
import com.zccoder.scc.service.api.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zccoder.scc.domain.po.ProjectPo;
import com.zccoder.scc.domain.po.PropertiesPo;

/**
 * 项目参数获取 rest服务
 * @Created by ZhangCheng on 2017-06-22
 * 
 */
@RestController
@RequestMapping("/config")
public class ConfigRest {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigRest.class);
	
	@Autowired
	private IProjectService projectService;
	
	@GetMapping("/{project_code}")
	public void getConfig(
			@PathVariable("project_code") String project_code,String token,
			HttpServletResponse response){
		
		logger.info("[URC] Project code is {} get project config properties.",project_code);
		
		ProjectPo projectPo = new ProjectPo();
		projectPo.setProject_code(project_code);
		
		ServiceMessage<List<ProjectPo>> projectMsg = projectService.listProjectPo(projectPo);
		
		if(!projectMsg.isSuccess()){
			logger.warn("[URC] This project [{}] is not exist",project_code);
			return;
		}
		
		String flag = projectMsg.getRespData().get(0).getSecurity_flag();
		String security_token = projectMsg.getRespData().get(0).getSecurity_token();
		
		if("1".equals(flag)){
			if(!Objects.equals(token, security_token)){
				logger.warn("[URC] This project [{}] need token, but the request not provide",project_code);
				return;
			}
		}
		
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
}
