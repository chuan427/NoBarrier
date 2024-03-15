
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
public class OrderService {


		@Autowired
		OrderRepository repository;

		public void addOrder(OrderVO OrderVO) {
			repository.save(OrderVO);
		}

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
		//訂單對需求一對一
		public ReqOrderVO getReqOrderByordReqnum(Integer reqNum){
			return getOneOrder(reqNum).getReqOrderVO();
		}
//		//訂單對報價一對一
		public QuoVO getQuoByordQuonum(Integer quoNum){
			return getOneOrder(quoNum).getQuoVO();
		}
		//訂單對特賣一對一
		public LimitSaleVO getLimitSaleByordLimnum(Integer limNum){
			return getOneOrder(limNum).getLimitsaleVO();
		}
		public List<OrderVO> getOneStatOrder(UserVO userVO) {
	        List<OrderVO> allOrder = repository.findAll();
	        List<OrderVO> validOrder = new ArrayList<>();

	        for (OrderVO order : allOrder) {
	            if (order.getUserVO().getUserId() == userVO.getUserId() && order.getOrdIsValid() == 1) {
	                validOrder.add(order);
	            }
	        }
	        return validOrder;
	    }
}

