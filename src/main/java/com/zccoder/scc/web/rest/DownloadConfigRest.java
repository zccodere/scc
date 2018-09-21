package com.zccoder.scc.web.rest;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.List;

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
 * 项目参数下载 rest服务
 * @Created by ZhangCheng on 2017-08-15
 * 
 */
@RestController
@RequestMapping("/download")
public class DownloadConfigRest {
	
	private static Logger logger = LoggerFactory.getLogger(DownloadConfigRest.class);
	
	@Autowired
	private IProjectService projectsService;
	
	@GetMapping("/{project_code}")
	public void download(@PathVariable("project_code") String project_code,
			HttpServletResponse response) throws Exception{
		
		logger.info("[URC] Project code is {} get project config properties.",project_code);
		
		ProjectPo projectPo = new ProjectPo();
		projectPo.setProject_code(project_code);
		ServiceMessage<List<PropertiesPo>> respMessage = projectsService.listProperties(projectPo);
		
		if(respMessage.isSuccess()){
			List<PropertiesPo> listProperties = respMessage.getRespData();
			String fileName = project_code+".properties";
//			String rootPath = "d:/";
			String rootPath = getClass().getClassLoader().getResource("").getPath();
			String tempPath = rootPath+"/temp";
			File tempDir = new File(tempPath);
			if(!tempDir.exists()){
				tempDir.mkdirs();
			}
			String filePath = tempDir+"/"+fileName;
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
			
			System.out.println("FilePaht:"+filePath);
			
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter body = new BufferedWriter(fw);
//			try {
				for (PropertiesPo propertiesPoTemp : listProperties) {
					StringBuffer line = new StringBuffer();
					line.append(propertiesPoTemp.getProperties_key()+" = "+propertiesPoTemp.getProperties_value()+"\r\n");
					body.append(line);
				}
//			}catch (IOException e) {
//				logger.error("[URC] write project config properties error {}",e.getMessage());
//			}
			body.flush();
			body.close();
			
			byte[] buff = new byte[1024];
			BufferedInputStream bis = null;
			OutputStream os = null;
			os = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
			
			bis.close();
		}
	}
}
