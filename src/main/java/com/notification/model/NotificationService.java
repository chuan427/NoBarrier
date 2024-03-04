package com.notification.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("notificationService")
public class NotificationService {

	@Autowired
	NotificationRepository repository;
	
	public void addNotification(NotificationVO notificationVO) {
		repository.save(notificationVO);
	}
	
	public void updateNotification(NotificationVO notificationVO) {
		repository.save(notificationVO);
	}
	
	public void deleteNotification(Integer notiNum) {
	    if (repository.existsById(notiNum)) {
	        repository.deleteById(notiNum);
	    } else {
	        throw new IllegalArgumentException("通知不存在");
	    }
	}
	
	public NotificationVO getOneNotification(Integer notiNum) {
		Optional <NotificationVO> optional = repository.findById(notiNum);
		return optional.orElse(null);
	}
	
	public List<NotificationVO> getAll() {
		return repository.findAll();
	}
}
