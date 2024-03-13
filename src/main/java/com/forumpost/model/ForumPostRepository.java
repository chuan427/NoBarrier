package com.forumpost.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ForumPostRepository extends JpaRepository<ForumPostVO, Integer> {
//	@Transactional
//	@Modifying
//	@Query(value = "delete from forumPost where fpNum =?1", nativeQuery = true)
//	void deleteByFpStat(int FpNum);

	@Transactional
	@Modifying
	@Query(value = "update forumPost set fpStat = 0 where fpNum = ?1", nativeQuery = true)
	void deleteByFpStat(int FpNum);
	
	//利用fpTime尋找最新貼文
	
	 @Query(value = "SELECT * FROM forumPost WHERE fpStat = 1 ORDER BY fpTime DESC LIMIT 1", nativeQuery = true)
	 Optional<ForumPostVO> findLatestPost();
	
}
