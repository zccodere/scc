package com.zccoder.scc.repository;

import com.zccoder.scc.domain.po.PropertiesPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Properties 资源接口
 * @Created by ZhangCheng on 2017-06-20
 *
 */
public interface PropertiesRepository extends JpaRepository<PropertiesPo, Long>, JpaSpecificationExecutor<PropertiesPo>{
	
	/** 查询参数，根据项目编码和参数键 */
	@Query("select o from urc_properties o where o.group_id=?1 and o.properties_key=?2")
	PropertiesPo getPropertiesPoByCodeAndKey(String group_id,String properties_key);
	
}
