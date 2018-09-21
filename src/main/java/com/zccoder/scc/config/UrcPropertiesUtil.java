package com.zccoder.scc.config;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title 业务参数工具类
 * @Description 根据业务参数的key获取value
 * @author zc
 * @version 1.0 2017-09-20
 */
@Component
public class UrcPropertiesUtil {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UrcProperties ugcProperties;
	
	/**
	 * @title 根据业务参数的key获取value
	 * @description 对外暴露，外部服务使用该方法获取value
	 * @author zc
	 * @version 1.0 2017-09-20
	 */
	public String getValue(EnUrcProperties properties){
		
		return this.getValueFromValue(properties);
	}
	
	/**
	 * @title 根据业务参数的key获取value
	 * @description 私有，从参数实体类中获取value
	 * @author zc
	 * @version 1.0 2017-09-20
	 */
	private String getValueFromValue(EnUrcProperties properties){
		
		if(ugcProperties==null){
			throw new RuntimeException("[UGC] 获取业务参数失败");
		}
		String value = "";
		
		try {
			value = BeanUtils.getProperty(ugcProperties, properties.getStrKey());
		} catch (Exception e) {
			logger.error(e.toString());
			throw new RuntimeException("[UGC] 获取业务参数失败:" + e.getMessage());
		}
		
		logger.info("[URC] Get properties key is [{}] and value is [{}]",properties.getStrKey(),value);
		
		return value;
	}
}
