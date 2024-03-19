package com.forumpost.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	 
	 //模糊查詢
	
	 @Query("SELECT fp FROM ForumPostVO fp WHERE " +
		       "fp.userVO.comName LIKE %:searchTerm% OR " +
		       "fp.fpTitle LIKE %:searchTerm%")
		Page<ForumPostVO> findByComNameOrFpTitleContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
	 
	 //觀看數 & 留言數
	 

}
