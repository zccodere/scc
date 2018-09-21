package com.zccoder.scc.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * urc_properties 数据对象
 * 
 * @Created by ZhangCheng on 2017-06-16
 *
 */
@Entity(name = "urc_properties")
public class PropertiesPo {

	/** 参数号 */
	@Id
	@GeneratedValue
	private Long id;
	/** 参数类型 */
	private String properties_type;
	/** 参数组号 */
	@Column(nullable = false)
	private String group_id;
	/** 参数组名称 */
	@Column(nullable = false)
	private String group_name;
	/** 参数名称 */
	private String properties_name;
	/** 参数键 */
	@Column(nullable = false)
	private String properties_key;
	/** 参数值 */
	@Column(length = 512, nullable = false)
	private String properties_value;
	/** 参数描述 */
	@Column(length = 512)
	private String properties_desc;

	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;
	/** 创建人编号 */
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

	@Override
	public String toString() {
		return "PropertiesPo [id=" + id + ", properties_type=" + properties_type + ", group_id=" + group_id
				+ ", group_name=" + group_name + ", properties_name=" + properties_name + ", properties_key="
				+ properties_key + ", properties_value=" + properties_value + ", properties_desc=" + properties_desc
				+ ", create_time=" + create_time + ", create_author_code=" + create_author_code
				+ ", create_author_name=" + create_author_name + ", last_modify_time=" + last_modify_time + ", status="
				+ status + ", province_code=" + province_code + ", area_code=" + area_code + ", part_month="
				+ part_month + "]";
	}
}
