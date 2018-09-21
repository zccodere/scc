package com.zccoder.scc.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * urc_properties_group 数据对象
 * @Created by ZhangCheng on 2017-08-02
 *
 */
@Entity(name = "urc_properties_group")
public class PropertiesGroupPo {
	
	/** 参数组号 */
	@Id
	@GeneratedValue
	private Long id;
	/** 参数组名称 */
	@Column(nullable=false)
	private String group_name;
	/** 参数组类型编码 */
	@Column(nullable=false)
	private String code_id;
	/** 参数组类型名称 */
	@Column(nullable=false)
	private String code_name;
	/** 参数组说明 */
	@Column(nullable=false)
	private String group_desc;
	
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
	/** 状态：0、无效；1、有效；2、已删除*/
	private String status;
	
	/** 省份编码 */
	private String province_code;
	/** 区域编码 */
	private String area_code;
	/** 分区月份 */
	private String part_month;
	
	@Override
	public String toString() {
		return "PropertiesGroupPo [id=" + id + ", group_name=" + group_name + ", code_id=" + code_id + ", code_name="
				+ code_name + ", group_desc=" + group_desc + ", create_time=" + create_time + ", create_author_code="
				+ create_author_code + ", create_author_name=" + create_author_name + ", last_modify_time="
				+ last_modify_time + ", status=" + status + ", province_code=" + province_code + ", area_code="
				+ area_code + ", part_month=" + part_month + "]";
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
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getGroup_desc() {
		return group_desc;
	}
	public void setGroup_desc(String group_desc) {
		this.group_desc = group_desc;
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
}
