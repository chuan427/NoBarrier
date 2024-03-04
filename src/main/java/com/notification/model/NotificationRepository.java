package com.notification.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<NotificationVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from notification where notiNum =?1", nativeQuery = true)
	void deleteByNotiNum(int NotiNum);
}
