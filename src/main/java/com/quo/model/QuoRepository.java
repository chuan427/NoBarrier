package com.quo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface QuoRepository extends JpaRepository <QuoVO, Integer> {
	
	@Query(value = "SELECT * FROM quotation WHERE quoIsValid = 1 AND quoUserid != ?\n" + "", nativeQuery = true)
	List<QuoVO> findByQuoUseridAndQuoIsValid(Integer quoUserid);

}
