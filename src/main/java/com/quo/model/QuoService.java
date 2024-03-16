package com.quo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.model.OrderVO;
import com.reqorder.model.ReqOrderRepository;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserRepository;
import com.user.model.UserVO;

@Service("quoService")
public class QuoService {

	@Autowired
	QuoRepository repository;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	ReqOrderRepository reqrepository;
	
	public void addQuo(QuoVO quoVO, UserVO loggingInUser) {
		quoVO.setUserVO(loggingInUser);
		
		Integer reqNum = 2;
		
		ReqOrderVO reqOrderVO = reqrepository.findById(reqNum).orElse(null);
		quoVO.setReqOrderVO(reqOrderVO);
				
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
	
	public List<QuoVO> getOneStatQuotation(UserVO userVO) {
        List<QuoVO> allQuotation = repository.findAll();
        List<QuoVO> validQuotation = new ArrayList<>();

        for (QuoVO quotation : allQuotation) {
            if (quotation.getUserVO().getUserId() == userVO.getUserId() && quotation.getQuoIsValid() == 1) {
                validQuotation.add(quotation);
            }
        }
        return validQuotation;
    }
//	public OrderVO getOrderByquoNum(Integer ordQuonum){
//		return getOneQuo(ordQuonum).getOrderVO();
//	}
	public ReqOrderVO getOrderByreqNum(Integer ordReqnum){
		return getOneQuo(ordReqnum).getReqOrderVO();
	}
}
