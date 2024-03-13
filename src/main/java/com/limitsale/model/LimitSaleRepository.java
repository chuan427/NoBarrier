package com.limitsale.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface LimitSaleRepository extends JpaRepository<LimitSaleVO, Integer>{
	@Transactional
	@Modifying
	@Query(value = "delete from limitSale where limNum =?1", nativeQuery = true)
	void deleteByLimNum(int LimNum);
}
