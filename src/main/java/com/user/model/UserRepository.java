
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
	@Query(value = "update userInformation set comStat = 1 where userid = ?0", nativeQuery = true)
	void reviewBycomStat(int userId);
	
	
	}
