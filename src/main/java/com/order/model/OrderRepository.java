package com.order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.reqorder.model.ReqOrderVO;
import com.user.model.UserVO;


public interface OrderRepository extends JpaRepository<OrderVO, Integer>{
	@Transactional
	@Modifying
	@Query(value = "delete from `order` where ordNum =?1", nativeQuery = true)
	void deleteByOrderId(int ordNum);
	
//	
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE `order` SET ordStat = 1 WHERE ordStat=?0 ", nativeQuery = true)
//	void changeStatByOrderId(int ordNum);
	
	@Query(value = "SELECT * FROM order WHERE ordNum = ?1", nativeQuery = true)
	List<OrderVO> findByOrdNum(Integer ordNum);

	List<OrderVO> findByUserVOUserId(Integer userId);


	
}
