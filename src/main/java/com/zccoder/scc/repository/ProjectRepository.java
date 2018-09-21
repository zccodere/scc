package com.zccoder.scc.repository;

import com.zccoder.scc.domain.po.ProjectPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Project 资源接口
 * @Created by ZhangCheng on 2017-06-16
 *
 */
public interface ProjectRepository extends JpaRepository<ProjectPo, Long>, JpaSpecificationExecutor<ProjectPo> {
	
	/** 查询项目，根据项目编码 */
	@Query("select o from urc_project o where o.status!='2' and o.project_code=?1")
	ProjectPo getEffectiveProjectByProjectCode(String project_code);
	
}
