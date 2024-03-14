package com.productinformation.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//public interface ProductInformationRepository extends JpaRepository<ProductInformationVO, Integer>{
//	
//	@Transactional
//	@Modifying
//	@Query(value = "delete from productInformation where pinfoNum =?1", nativeQuery = true)
//	void deleteByPinfoNum(int PinfoNum);
//
//	
//}

@Repository
public interface ProductInformationRepository extends JpaRepository<ProductInformationVO, Integer> {
    List<ProductInformationVO> findByUserVOUserId(Integer userId);
    
    
	@Transactional
	@Modifying
	@Query(value = "delete from productInformation where pinfoNum =?1", nativeQuery = true)
	void deleteByPinfoNum(int PinfoNum);
}