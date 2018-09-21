package com.zccoder.scc.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * urc_project 数据对象
 * @Created by ZhangCheng on 2017-06-16
 *
 */
@Entity(name = "urc_project")
public class ProjectPo {
	
	/** 项目号 */
	@Id
	@GeneratedValue
	private Long id;
	/** 项目编码 */
	@Column(nullable=false)
	private String project_code;
	/** 项目名称 */
	@Column(nullable=false)
	private String project_name;
	
	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;
	/** 创建人编码 */
	private String create_author_code;
	/** 创建人名称 */
	private String create_author_name;
	/** 最后一次修改时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_modify_time;
	/** 状态：0、无效；1、有效；2、已删除 */
	private String status;
	/** 省份编码 */
	private String province_code;
	/** 区域编码 */
	private String area_code;
	/** 分区月份 */
	private String part_month;
	
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
	
	@Override
	public String toString() {
		return "ProjectPo [id=" + id + ", project_code=" + project_code + ", project_name=" + project_name
				+ ", create_time=" + create_time + ", create_author_code=" + create_author_code
				+ ", create_author_name=" + create_author_name + ", last_modify_time=" + last_modify_time + ", status="
				+ status + ", province_code=" + province_code + ", area_code=" + area_code + ", part_month="
				+ part_month + "]";
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
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
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
	public Date getLast_modify_time() {
		return last_modify_time;
	}
	public void setLast_modify_time(Date last_modify_time) {
		this.last_modify_time = last_modify_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getPart_month() {
		return part_month;
	}
	public void setPart_month(String part_month) {
		this.part_month = part_month;
	}
	
	
}
