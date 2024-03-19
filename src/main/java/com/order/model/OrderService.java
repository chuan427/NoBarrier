
package com.order.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limitsale.model.LimitSaleVO;

import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserVO;



@Service("orderService")
public class OrderService<OrdRatstar> {


		@Autowired
		OrderRepository repository;

		public void addOrder(OrderVO OrderVO) {
			
			repository.save(OrderVO);
		}
		
		public void addChatOrder(OrderVO OrderVO) {
		repository.save(OrderVO);
	}
		
//		public void addOrder(OrderVO OrderVO,UserVO loggingInUser,QuoVO quoVO) {
//			OrderVO.setUserVO(loggingInUser);
//			OrderVO.setQuoVO(quoVO);
//			repository.save(OrderVO);
//		}

		public void updateOrder(OrderVO OrderVO) {
			repository.save(OrderVO);
		}
		//改變訂單狀態
		public void changeStatByOrderId(OrderVO OrderVO) {
			repository.save(OrderVO);
		}

		public void deleteOrder(Integer ordNum) {
			if (repository.existsById(ordNum))
				repository.deleteByOrderId(ordNum);
			    repository.deleteById(ordNum);
		}

		public OrderVO getOneOrder(Integer ordNum) {
			Optional<OrderVO> optional = repository.findById(ordNum);
//			return optional.get();
			return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		}

		public List<OrderVO> getAll() {
			return repository.findAll();
		}

		//訂單對特賣多對一

//		public LimitSaleVO getLimitSaleByordLimnum(Integer limNum){
//			return getOneOrder(limNum).getLimitsaleVO();
//		}
		
		public List<OrderVO> getOrderDetails(Integer userId) {
			return repository.findByUserVOUserId(userId);
		}


		
		public List<OrderVO> getOrdersByUserId(Integer userId) {
	        // 调用 OrderRepository 中的方法查询订单
	        return repository.findByUserVOUserId(userId);
	    }

		public void rateAndReviewOrder(Integer ordNum, Double ordRatstar, String ordComment) {
		    OrderVO order = repository.findById(ordNum).orElse(null);
		    if (order != null) {
		        order.setOrdRatstar(ordRatstar);
		        order.setOrdComment(ordComment);
		        repository.save(order);
		    }
		}

		public OrdRatstar getOrdRatstar() {
			// TODO Auto-generated method stub
			return null;
		}



		
}


