package com.zccoder.scc.domain.vo;

import org.springframework.stereotype.Component;

/**
 * User 数据对象 当前登录的用户
 * @Created by ZhangCheng on 2017-06-23
 *
 */
@Component
public class User {
	
	/** 用户编码 */
	private String author_code;
	/** 用户名称 */
	private String author_name;
	/** 登录 jsession_id */
	private String jsession_id;

	@Override
	public String toString() {
		return "User [author_code=" + author_code + ", author_name=" + author_name + ", jsession_id=" + jsession_id
				+ "]";
	}

	public String getAuthor_code() {
		return author_code;
	}

	public void setAuthor_code(String author_code) {
		this.author_code = author_code;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getJsession_id() {
		return jsession_id;
	}

	public void setJsession_id(String jsession_id) {
		this.jsession_id = jsession_id;
	}
}
