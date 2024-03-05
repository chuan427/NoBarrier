package com.quo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("quoService")
public class QuoService {

	@Autowired
	QuoRepository repository;
	
	public void addQuo(QuoVO quoVO) {
		repository.save(quoVO);
	}
	
	public void updateQuo(QuoVO quoVO) {
		repository.save(quoVO);
	}
	
	public void deleteQuo(Integer quoNum) {
		Optional<QuoVO> quoOptional = repository.findById(quoNum);
		if (quoOptional.isPresent()) {
            QuoVO quoVO = quoOptional.get();
            
            quoVO.setQuoIsValid(0);

            repository.save(quoVO);
        }
	}
	
	public QuoVO getOneQuo(Integer quoNum) {
		Optional<QuoVO> quoOptional = repository.findById(quoNum);
		if (quoOptional.isPresent()) {
			return quoOptional.get();
		}
		return null;
	}
	
	public List<QuoVO> getAll(){
		return repository.findAll();
	}
}