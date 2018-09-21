package com.zccoder.scc.repository;

import com.zccoder.scc.domain.po.CodeTypePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * CodeType 资源接口
 * @Created by ZhangCheng on 2017-06-16
 *
 */
public interface CodeTypeRepository extends JpaRepository<CodeTypePo, Long>, JpaSpecificationExecutor<CodeTypePo>  {
	
	/** 查询类型，根据类型编码 */
	@Query("select o from urc_code_type o where o.status!='2' and o.type_code=?1")
	CodeTypePo getEffectiveCodeTypeByTypeCode(String type_code);
	
}
