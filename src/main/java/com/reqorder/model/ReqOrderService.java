package com.reqorder.model;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.industry.model.IndustryRepository;
import com.industry.model.IndustryVO;
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

	public void addReqOrder(ReqOrderVO reqOrderVO, UserVO loggingInUser) {

		// Integer userId = (Integer) request.getSession().getAttribute("userId");
		//UserVO userVO = userrepository.findById(userId).orElse(null);
		reqOrderVO.setUserVO(loggingInUser);

		Integer industryNum = 1;

		IndustryVO industryVO = industryrepository.findById(industryNum).orElse(null);
		reqOrderVO.setIndustryVO(industryVO); 

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
	
	public List<ReqOrderVO> findByReqIsValid() {
		return repository.findByReqIsValid();
	}
	
	public List<ReqOrderVO> getReqOrderByUserId(Integer userId) {
	    return repository.findByUserId(userId);
	}

	public List<ReqOrderVO> getAll() {
		return repository.findAll();
	}

	public List<ReqOrderVO> getOneStatQuestions(UserVO userVO) {
	    List<ReqOrderVO> allReqOrder = repository.findAll();
	    List<ReqOrderVO> validReqOrder = new ArrayList<>();

	    for (ReqOrderVO reqorder : allReqOrder) {
	        if (reqorder.getUserVO().getUserId() == userVO.getUserId() && reqorder.getReqIsValid() == 1) {
	            validReqOrder.add(reqorder);
	        }
	    }
	    return validReqOrder;
	}

}
