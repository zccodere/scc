package com.zccoder.scc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zccoder.scc.domain.po.CodeListPo;

/**
 * CodeList 资源接口
 * @Created by ZhangCheng on 2017-06-16
 *
 */
public interface CodeListRepository extends JpaRepository<CodeListPo, Long>, JpaSpecificationExecutor<CodeListPo> {
	
}
