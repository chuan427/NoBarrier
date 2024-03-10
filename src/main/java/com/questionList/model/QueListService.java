package com.questionList.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("queListService")
public class QueListService {

	@Autowired
	QueListRepository repository;

	public void addQue(QueListVO queListVO) {
		repository.save(queListVO);
	}

	public void updateQue(QueListVO queListVO) {
		repository.save(queListVO);
	}

	public void deleteQue(Integer queNum) {
		if (repository.existsById(queNum))
			repository.deleteByEmpno(queNum);
//		    repository.deleteById(queNum);
	}

	public QueListVO getOneQue(Integer queNum) {
		Optional<QueListVO> optional = repository.findById(queNum);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<QueListVO> getAll() {
		return repository.findAll();
	}

}
