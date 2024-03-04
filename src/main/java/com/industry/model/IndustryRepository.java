package com.industry.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IndustryRepository extends JpaRepository<IndustryVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from industry where industryNum =?1", nativeQuery = true)
	void deleteByIndustryNum(int IndustryNum);
}
