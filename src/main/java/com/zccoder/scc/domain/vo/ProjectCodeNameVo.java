package com.zccoder.scc.domain.vo;

/**
 * 
 * 项目编码和项目名称 数据对象
 * @Created by ZhangCheng on 2017-06-22
 * */
public class ProjectCodeNameVo {
	
	/** 项目编码 */
	private String project_code;
	/** 项目名称 */
	private String project_name;
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
	@Override
	public String toString() {
		return "ProjectCodeNameVo [project_code=" + project_code + ", project_name=" + project_name + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project_code == null) ? 0 : project_code.hashCode());
		result = prime * result + ((project_name == null) ? 0 : project_name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectCodeNameVo other = (ProjectCodeNameVo) obj;
		if (project_code == null) {
			if (other.project_code != null)
				return false;
		} else if (!project_code.equals(other.project_code))
			return false;
		if (project_name == null) {
			if (other.project_name != null)
				return false;
		} else if (!project_name.equals(other.project_name))
			return false;
		return true;
	}
}
