package com.reqorder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void addReqOrder(ReqOrderVO reqOrderVO, UserVO loggingInUser, IndustryVO industryVO) {
        reqOrderVO.setUserVO(loggingInUser);
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
        return optional.orElse(null);
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

    public List<ReqOrderVO> getAllReqOrderExceptMe(Integer reqUserid) {
        List<ReqOrderVO> allReqOrder = repository.findByReqUseridAndReqIsValid(reqUserid);
        return allReqOrder;
    }
    
    
    
    
	public Set<QuoVO> getOrderByquoNum(Integer ordReqnum){
		return getOneReqOrder(ordReqnum).getQuotations();
	}
	
	
}
