
package com.user.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<UserVO, Integer>{
	
	public UserVO findByComAccount(String comAccount);
	public UserVO findByComUniNumber(String ComUniNumber);
	public UserVO findByComContactPerson(String comContactPerson);
	
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE userInformation SET comStat = 1 WHERE userid = ?1 AND comStat = 0", nativeQuery = true)
	void reviewBycomStatIfZero(int userId);
	
	
	}
