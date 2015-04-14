/**
 * 
 */
package com.sendtend.web.dao.member;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.sendtend.web.entity.member.TblMember;

public interface MemberDAO extends JpaRepository<TblMember, Long>, JpaSpecificationExecutor<TblMember> {
	/**/
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.sendtend.web.entity.member.TblMember")
		}
	)
	@Query("from TblMember o order by o.priority ASC")
	List<TblMember> findAllWithCache();
	/**/
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.sendtend.web.entity.member.TblMember")
		}
	)
	@Query("from TblMember o where o.id=1 or o.id=:id order by o.priority ASC")
	List<TblMember> findByIdWithCache(@Param("id") Long id);
	
	TblMember getByMemberName(String memberName);
	
}