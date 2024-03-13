package com.reqorder.model;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industry.model.IndustryRepository;
import com.industry.model.IndustryVO;
import com.order.model.OrderVO;
import com.quo.model.QuoVO;
import com.user.model.UserRepository;
import com.user.model.UserVO;

@Service("reqOrderService")
public class ReqOrderService {

	@Autowired
	ReqOrderRepository repository;

	@Autowired
	UserRepository userrepository;

	@Autowired
	IndustryRepository industryrepository;
	
	@Autowired
	HttpServletRequest request;

	public void addReqOrder(ReqOrderVO reqOrderVO) {
		Integer userId = 2;

		// Integer userId = (Integer) request.getSession().getAttribute("userId");

		UserVO userVO = userrepository.findById(userId).orElse(null);
		reqOrderVO.setUserVO(userVO); // 设置用户信息到广告对象中
		
		
		Integer industryNum = 1;
		
		IndustryVO industryVO = industryrepository.findById(industryNum).orElse(null);
		reqOrderVO.setIndustryVO(industryVO); // 设置用户信息到广告对象中
		
		repository.save(reqOrderVO);
	}

	public void updateReqOrder(ReqOrderVO reqOrderVO) {
		repository.save(reqOrderVO);
	}

	public void deleteReqOrder(Integer reqNum) {
		if (repository.existsById(reqNum)) {
			repository.deleteById(reqNum);
		} else {
			throw new IllegalArgumentException("訂單不存在");
		}
	}

	public ReqOrderVO getOneReqOrder(Integer reqNum) {
		Optional<ReqOrderVO> optional = repository.findById(reqNum);
//		return optional.get();
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ReqOrderVO> getAll() {
		return repository.findAll();
	}
	
	//需求對報價一對一
	public QuoVO getOrderByquoNum(Integer ordReqnum){
		return getOneReqOrder(ordReqnum).getQuoVO();
	}
	//需求對訂單一對一
	public OrderVO getOrderByreqNum(Integer ordReqnum){
		return getOneReqOrder(ordReqnum).getOrderVO();
	}
}
