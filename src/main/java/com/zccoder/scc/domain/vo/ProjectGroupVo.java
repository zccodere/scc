package com.zccoder.scc.domain.vo;

/**
 * ProjectGroupVo 数据对象
 * @Created by ZhangCheng on 2017-08-14
 *
 */
public class ProjectGroupVo {
	
	/** 项目参数组号 */
	private Long id;
	
	/** 项目编码 */
	private String project_code;
	/** 项目名称 */
	private String project_name;
	/** 参数组号 */
	private String group_id;
	/** 参数组名称 */
	private String group_name;
	
	/** 创建时间 */
	private String create_time;
	/** 创建人编码 */
	private String create_author_code;
	/** 创建人名称 */
	private String create_author_name;
	/** 最后一次修改时间 */
	private String last_modify_time;
	/** 状态：0、无效；1、有效；2、已删除*/
	private String status;
	/** 状态名称：0、无效；1、有效 */
	private String status_name;
	
	/** 省份编码 */
	private String province_code;
	/** 区域编码 */
	private String area_code;
	/** 分区月份 */
	private String part_month;
	
	@Override
	public String toString() {
		return "ProjectGroupVo [id=" + id + ", project_code=" + project_code + ", project_name=" + project_name
				+ ", group_id=" + group_id + ", group_name=" + group_name + ", create_time=" + create_time
				+ ", create_author_code=" + create_author_code + ", create_author_name=" + create_author_name
				+ ", last_modify_time=" + last_modify_time + ", status=" + status + ", status_name=" + status_name
				+ ", province_code=" + province_code + ", area_code=" + area_code + ", part_month=" + part_month + "]";
	}
	
	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
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
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
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
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
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
