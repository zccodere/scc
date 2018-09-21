package com.zccoder.scc.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * urc_code_type 数据对象
 * @Created by ZhangCheng on 2017-06-16
 *
 */
@Entity(name = "urc_code_type")
public class CodeTypePo {
	
	/** 类型号 */
	@Id
	@GeneratedValue
	private Long id;
	/** 类型编码 */
	@Column(nullable=false)
	private String type_code;
	/** 类型名称 */
	@Column(nullable=false)
	private String type_name;
	/** 类型描述 */
	@Column(nullable=false)
	private String type_desc;
	
	private String max_length;
	private String load_flag;
	private String app_type;
	private String data_type;
	
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
	/** 状态：0、无效；1、有效 */
	private String status;
	
	/** 省份编码 */
	private String province_code;
	/** 区域编码 */
	private String area_code;
	/** 分区月份 */
	private String part_month;
	
	@Override
	public String toString() {
		return "CodeTypePo [id=" + id + ", type_code=" + type_code + ", type_name=" + type_name + ", type_desc="
				+ type_desc + ", max_length=" + max_length + ", load_flag=" + load_flag + ", app_type=" + app_type
				+ ", data_type=" + data_type + ", create_time=" + create_time + ", create_author_code="
				+ create_author_code + ", create_author_name=" + create_author_name + ", last_modify_time="
				+ last_modify_time + ", status=" + status + ", province_code=" + province_code + ", area_code="
				+ area_code + ", part_month=" + part_month + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}
	public String getMax_length() {
		return max_length;
	}
	public void setMax_length(String max_length) {
		this.max_length = max_length;
	}
	public String getLoad_flag() {
		return load_flag;
	}
	public void setLoad_flag(String load_flag) {
		this.load_flag = load_flag;
	}
	public String getApp_type() {
		return app_type;
	}
	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
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
