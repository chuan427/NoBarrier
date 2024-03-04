package com.reqorder.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReqOrderRepository extends JpaRepository<ReqOrderVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from reqOrder where reqNum =?1", nativeQuery = true)
	void deleteByReqNum(int ReqNum);
	
}
