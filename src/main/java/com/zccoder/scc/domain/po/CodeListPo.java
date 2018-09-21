package com.zccoder.scc.domain.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * urc_code_list 数据对象
 * @Created by ZhangCheng on 2017-06-16
 *
 */
@Entity(name = "urc_code_list")
public class CodeListPo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 类型值号 */
	@Id
	@GeneratedValue
	private Long id;
	/** 类型编码 */
	@Column(nullable=false)
	private String type_code;
	/** 类型值 */
	@Column(nullable=false)
	private String code_id;
	/** 类型值名称 */
	@Column(nullable=false)
	private String code_name;
	/** 排序编号 */
	private String seq_id;
	private String macro_code;
	private String parent_type_code;
	private String parent_code_id;
	/** 生效标识（兼容旧表） */
	private String eff_flag;
	
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
		return "CodeListPo [id=" + id + ", type_code=" + type_code + ", code_id=" + code_id + ", code_name=" + code_name
				+ ", seq_id=" + seq_id + ", macro_code=" + macro_code + ", parent_type_code=" + parent_type_code
				+ ", parent_code_id=" + parent_code_id + ", eff_flag=" + eff_flag + ", create_time=" + create_time
				+ ", create_author_code=" + create_author_code + ", create_author_name=" + create_author_name
				+ ", last_modify_time=" + last_modify_time + ", status=" + status + ", province_code=" + province_code
				+ ", area_code=" + area_code + ", part_month=" + part_month + "]";
	}
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getMacro_code() {
		return macro_code;
	}
	public void setMacro_code(String macro_code) {
		this.macro_code = macro_code;
	}
	public String getParent_type_code() {
		return parent_type_code;
	}
	public void setParent_type_code(String parent_type_code) {
		this.parent_type_code = parent_type_code;
	}
	public String getParent_code_id() {
		return parent_code_id;
	}
	public void setParent_code_id(String parent_code_id) {
		this.parent_code_id = parent_code_id;
	}
	public String getEff_flag() {
		return eff_flag;
	}
	public void setEff_flag(String eff_flag) {
		this.eff_flag = eff_flag;
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
