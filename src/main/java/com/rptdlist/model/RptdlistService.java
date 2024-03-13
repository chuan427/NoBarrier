package com.rptdlist.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.model.OrderVO;

@Service("rptdlistService")
public class RptdlistService {

	@Autowired
	RptdlistRepository repository;
	
	public void addRptdlist(RptdlistVO rptdlistVO) {
		repository.save(rptdlistVO);
	}
	
	public void updateRptdlist(RptdlistVO rptdlistVO) {
		repository.save(rptdlistVO);
	}
	
	public void deleteRptdlist(Integer rptdNum) {
		Optional<RptdlistVO> rptdlistOptional = repository.findById(rptdNum);
		if (rptdlistOptional.isPresent()) {
			RptdlistVO rptdlistVO = rptdlistOptional.get();
            
			rptdlistVO.setRptdIsValid(0);

            repository.save(rptdlistVO);
        }
	}
	
	public RptdlistVO getOneRptdlist(Integer rptdNum) {
		Optional<RptdlistVO> rptdlistOptional = repository.findById(rptdNum);
		if (rptdlistOptional.isPresent()) {
			return rptdlistOptional.get();
		}
		return null;
	}

	public List<RptdlistVO> getAll(){
		return repository.findAll();
	}
	public OrderVO getOrderByreqNum(Integer rptdOrdernum){
		return getOneRptdlist(rptdOrdernum).getOrderVO();
	}
}
