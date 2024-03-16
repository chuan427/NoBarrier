package com.administrator.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.model.AdministratorVO;

@Service("administratorService")
public class AdministratorService {

	@Autowired
	AdministratorRepository repository;
	
	public void addAdministrator(AdministratorVO administratorVO) {
		repository.save(administratorVO);
	}
	
	public void updateAdministrator(AdministratorVO administratorVO) {
		repository.save(administratorVO);
	}
	
	public void deleteAdministrator(Integer adminNum) {
		if (repository.existsById(adminNum)) {
			repository.deleteByAdminNum(adminNum);
		} else {
			throw new IllegalArgumentException("管理者不存在");
		}
	}
	
	public AdministratorVO getOneAdministrator(Integer adminNum) {
		Optional<AdministratorVO> optional = repository.findById(adminNum);
		
		return optional.orElse(null);
	}
	
	public List<AdministratorVO> getAll() {
		return repository.findAll();
	}
}
