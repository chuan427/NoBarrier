package com.administrator.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdministratorRepository extends JpaRepository<AdministratorVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from administrator where adminNum =?1", nativeQuery = true)
	void deleteByAdminNum(int AdminNum);
}
