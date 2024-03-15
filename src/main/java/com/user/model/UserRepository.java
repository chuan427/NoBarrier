package com.user.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserVO, Integer> {

	public UserVO findByComAccount(String comAccount);

	public UserVO findByComUniNumber(String ComUniNumber);

	@Modifying
	@Transactional
	@Query("UPDATE UserVO u SET u.comStat = 1 WHERE u.userId = :userId")
	void updateComStatToOne(Integer userId);
}
