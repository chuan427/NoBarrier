package com.industry.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reqorder.model.ReqOrderVO;
import com.user.model.UserVO;

@Service("industryService")
public class IndustryService {

	@Autowired
	IndustryRepository repository;
	
	public void addIndustry(IndustryVO industryVO) {
		repository.save(industryVO);
	}
	
	public void updateIndustry(IndustryVO industryVO) {
		repository.save(industryVO);
	}
	
	public void deleteIndustry(Integer industryNum) {
		if (repository.existsById(industryNum)) {
			repository.deleteByIndustryNum(industryNum);
		} else {
			throw new IllegalArgumentException("產業類別不存在");
		}
	}
	
	public IndustryVO getOneIndustry(Integer industryNum) {
		Optional<IndustryVO> optional = repository.findById(industryNum);
		
		return optional.orElse(null);
	}
	
	public List<IndustryVO> getAll() {
		return repository.findAll();
	}
	
	public Set<UserVO> getUserByComIndustry(Integer ComIndustry){
		return getOneIndustry(ComIndustry).getUser();
	}
	
	public Set<ReqOrderVO> getReqOrdersByIndustryNum(Integer IndustryNum){
		return getOneIndustry(IndustryNum).getReqOrder();
	}
}
