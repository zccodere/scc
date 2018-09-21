package com.zccoder.scc.domain.vo;

/**
 * ProjectVo 数据对象
 * @Created by ZhangCheng on 2017-06-16
 *
 */
public class ProjectVo {
	
	/** 项目号 */
	private Long id;
	/** 项目编码 */
	private String project_code;
	/** 项目名称 */
	private String project_name;
	
	/** 创建时间 */
	private String create_time;
	/** 创建人编码 */
	private String create_author_code;
	/** 创建人名称 */
	private String create_author_name;
	/** 最后一次修改时间 */
	private String last_modify_time;
	/** 状态：0、无效；1、有效 */
	private String status;
	/** 状态名称：0、无效；1、有效 */
	private String status_name;
	
	/** 是否启用安全验证：0、关闭；1、启用；*/
	private String security_flag;
	/** 验证token */
	private String security_token;
	
	public String getSecurity_flag() {
		return security_flag;
	}
	public void setSecurity_flag(String security_flag) {
		this.security_flag = security_flag;
	}
	public String getSecurity_token() {
		return security_token;
	}
	public void setSecurity_token(String security_token) {
		this.security_token = security_token;
	}
	
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProject_code() {
		return project_code;
	}
	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_author_code() {
		return create_author_code;
	}
	public void setCreate_author_code(String create_author_code) {
		this.create_author_code = create_author_code;
	}
	public String getCreate_author_name() {
		return create_author_name;
	}
	public void setCreate_author_name(String create_author_name) {
		this.create_author_name = create_author_name;
	}
	public String getLast_modify_time() {
		return last_modify_time;
	}
	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProjectVo [id=" + id + ", project_code=" + project_code + ", project_name=" + project_name
				+ ", create_time=" + create_time + ", create_author_code=" + create_author_code
				+ ", create_author_name=" + create_author_name + ", last_modify_time=" + last_modify_time + ", status="
				+ status + ", status_name=" + status_name + ", security_flag=" + security_flag + ", security_token="
				+ security_token + "]";
	}
}
