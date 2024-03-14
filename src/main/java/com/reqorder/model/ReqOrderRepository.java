package com.reqorder.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReqOrderRepository extends JpaRepository<ReqOrderVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from reqorder where reqNum =?1", nativeQuery = true)
	void deleteByReqNum(int ReqNum);
	
	@Query(value = "SELECT * FROM reqorder WHERE reqIsValid = 1", nativeQuery = true)
	List<ReqOrderVO> findByReqIsValid();

	@Query(value = "SELECT r FROM ReqOrderVO r WHERE r.userVO.userId = :userId")
	List<ReqOrderVO> findByUserId(@Param("userId") Integer userId);

}
