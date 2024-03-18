package com.limitsale.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionList.model.QueListVO;
import com.user.model.UserVO;

@Service()
public class LimitSaleService {

	@Autowired
	LimitSaleRepository repository;

	public void addLimitSale(LimitSaleVO limitSaleVO) {
		repository.save(limitSaleVO);
	}

	public void updateLimitSale(LimitSaleVO limitSaleVO) {
		repository.save(limitSaleVO);
	}

	public void deleteLimitSale(Integer limNum) {
		if (repository.existsById(limNum))
			repository.deleteByLimNum(limNum);
	}

	public LimitSaleVO getOneLimitSale(Integer limNum) {
		Optional<LimitSaleVO> optional = repository.findById(limNum);
//			return optional.get();
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<LimitSaleVO> getAll() {
		return repository.findAll();
	}

	public List<LimitSaleVO> getOneLimitSalebyUserid(UserVO userVo) {
		List<LimitSaleVO> allQuestions = repository.findAll();
		List<LimitSaleVO> validQuestions = new ArrayList<>();
//		    System.out.println(userVo.getUserId());
//		    System.out.println("===========================");
		for (LimitSaleVO sale : allQuestions) {
			if (sale.getUserVO().getUserId() == userVo.getUserId()) {
//		            System.out.println(question.getUserVO().getUserId());
				validQuestions.add(sale);
			}
		}
		return validQuestions;
	}
	
}
