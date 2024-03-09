package com.forumreport.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ForumReportRepository extends JpaRepository<ForumReportVO, Integer> {
//	@Transactional
//	@Modifying
//	@Query(value = "delete from forum_report where frpNum =?1", nativeQuery = true)
//	void deleteByFrpNum(int frpNum);

	@Transactional
	@Modifying
	@Query(value = "update forumReport set frpIsValid = 0 where frpnum = ?1", nativeQuery = true)
	void deleteByFrpIsValid(int FrpNum);
	
	}
