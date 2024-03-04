package com.reqorder.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("reqOrderService")
public class ReqOrderService {
	
	@Autowired
	ReqOrderRepository repository;
	
	public void addReqOrder(ReqOrderVO reqOrderVO) {
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
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ReqOrderVO> getAll() {
		return repository.findAll();
	}
}
