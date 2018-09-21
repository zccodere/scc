package com.zccoder.scc.util;

import java.util.UUID;

/**
* ID工具类
* @create ZhangCheng by 2017-09-21
*
*/
public class IdUtils {
	
	public static final String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
