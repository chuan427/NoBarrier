package com.forumreply.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ForumReplyRepository extends JpaRepository<ForumReplyVO, Integer> {
//	@Transactional
//	@Modifying
//	@Query(value = "delete from forumReply where frNum =?1", nativeQuery = true)
//	void deleteByFrNum(int FrNum);

	@Transactional
	@Modifying
	@Query(value = "update forumReply set frStat = 0 where frNum = ?1", nativeQuery = true)
	void deleteByFrStat(int FrNum);
	

}
