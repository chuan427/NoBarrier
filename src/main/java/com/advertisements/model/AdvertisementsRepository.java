package com.advertisements.model;
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdvertisementsRepository extends JpaRepository<AdvertisementsVO, Integer> {

}