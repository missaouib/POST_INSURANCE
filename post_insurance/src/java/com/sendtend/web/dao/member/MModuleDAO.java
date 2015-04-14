/**
 * 
 */
package com.sendtend.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.sendtend.web.entity.member.TblMemberModule;

public interface MModuleDAO extends JpaRepository<TblMemberModule, Long>, JpaSpecificationExecutor<TblMemberModule> {
	Page<TblMemberModule> findByParentId(Long parentId, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.sendtend.web.entity.member.TblMemberModule")
		}
	)
	@Query("from TblMemberModule m order by m.priority ASC")
	List<TblMemberModule> findAllWithCache();
	
	TblMemberModule getBySn(String sn);
}