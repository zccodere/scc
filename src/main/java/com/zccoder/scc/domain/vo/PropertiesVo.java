package com.zccoder.scc.domain.vo;

/**
 * PropertiesVo 数据对象
 * @Created by ZhangCheng on 2017-06-20
 *
 */
public class PropertiesVo {
	
	/** 参数号 */
	private Long id;
	/** 参数类型 */
	private String properties_type;
	/** 参数组号 */
	private String group_id;
	/** 参数组名称 */
	private String group_name;
	/** 参数名称 */
	private String properties_name;
	/** 参数键 */
	private String properties_key;
	/** 参数值 */
	private String properties_value;
	/** 参数描述 */
	private String properties_desc;

	/** 创建时间 */
	private String create_time;
	/** 创建人编号 */
	private String create_author_code;
	/** 创建人名称 */
	private String create_author_name;
	/** 最后一次修改时间 */
	private String last_modify_time;
	/** 状态：0、无效；1、有效 */
	private String status;
	/** 状态名称：0、无效；1、有效 */
	private String status_name;
	@Override
	public String toString() {
		return "PropertiesVo [id=" + id + ", properties_type=" + properties_type + ", group_id=" + group_id
				+ ", group_name=" + group_name + ", properties_name=" + properties_name + ", properties_key="
				+ properties_key + ", properties_value=" + properties_value + ", properties_desc=" + properties_desc
				+ ", create_time=" + create_time + ", create_author_code=" + create_author_code
				+ ", create_author_name=" + create_author_name + ", last_modify_time=" + last_modify_time + ", status="
				+ status + ", status_name=" + status_name + "]";
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
	public String getProperties_type() {
		return properties_type;
	}
	public void setProperties_type(String properties_type) {
		this.properties_type = properties_type;
	}
	public String getProperties_name() {
		return properties_name;
	}
	public void setProperties_name(String properties_name) {
		this.properties_name = properties_name;
	}
	public String getProperties_key() {
		return properties_key;
	}
	public void setProperties_key(String properties_key) {
		this.properties_key = properties_key;
	}
	public String getProperties_value() {
		return properties_value;
	}
	public void setProperties_value(String properties_value) {
		this.properties_value = properties_value;
	}
	public String getProperties_desc() {
		return properties_desc;
	}
	public void setProperties_desc(String properties_desc) {
		this.properties_desc = properties_desc;
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
}
