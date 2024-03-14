package com.questionList.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.model.UserRepository;
import com.user.model.UserVO;


@Service("queListService")
public class QueListService {

	@Autowired
	QueListRepository repository;

	
	@Autowired
    UserRepository userrepository;

    @Autowired
    HttpServletRequest request;
    
    
	public void addQue(QueListVO queListVO,UserVO loggingInUser) {
		          
//		Integer userId = (Integer) request.getSession().getAttribute("userId");              
//		UserVO userVO = userrepository.findById(userId).orElse(null);             
		queListVO.setUserVO(loggingInUser); // 设置用户信息到广告对象中
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
	
	
	public List<QueListVO> getONE1StatQuestions(UserVO userVO) {
	    List<QueListVO> allQuestions = repository.findAll();
	    List<QueListVO> validQuestions = new ArrayList<>();
	    System.out.println(userVO.getUserId());
	    System.out.println("===========================");
	    for (QueListVO question : allQuestions) {
	        if (question.getUserVO().getUserId() == userVO.getUserId() && question.getQueStat() == 1) {
//	            System.out.println(question.getUserVO().getUserId());
	        	validQuestions.add(question);
	        }
	    }
	    return validQuestions;
	}

	
	public List<QueListVO> getONEStat0Questions(UserVO userVO) {
//		Integer userId = 3;   

        List<QueListVO> allQuestions = repository.findAll();
        List<QueListVO> validQuestions = new ArrayList<>();

        for (QueListVO question : allQuestions) {
            if (question.getUserVO().getUserId()== userVO.getUserId()  &&question.getQueStat() == 0) { // 判断 queIsValid 是否为 1
                validQuestions.add(question);
            }
        }
        return validQuestions;
    }

}
