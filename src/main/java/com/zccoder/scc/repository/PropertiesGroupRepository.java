package com.zccoder.scc.repository;

import com.zccoder.scc.domain.po.PropertiesGroupPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Properties 资源接口
 * @Created by ZhangCheng on 2017-08-02
 *
 */
public interface PropertiesGroupRepository extends JpaRepository<PropertiesGroupPo, Long>, JpaSpecificationExecutor<PropertiesGroupPo>{
	
	
}
