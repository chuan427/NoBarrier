package com.productinformation.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductInformationRepository extends JpaRepository<ProductInformationVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from productInformation where pinfoNum =?1", nativeQuery = true)
	void deleteByPinfoNum(int PinfoNum);

}
