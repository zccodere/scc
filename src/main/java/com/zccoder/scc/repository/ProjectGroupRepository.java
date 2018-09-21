package com.zccoder.scc.repository;

import com.zccoder.scc.domain.po.ProjectGroupPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ProjectGroup 资源接口
 * @Created by ZhangCheng on 2017-08-14
 *
 */
public interface ProjectGroupRepository extends JpaRepository<ProjectGroupPo, Long>, JpaSpecificationExecutor<ProjectGroupPo> {
	
}
