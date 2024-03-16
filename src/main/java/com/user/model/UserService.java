package com.user.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.forumpost.model.ForumPostVO;
import com.order.model.OrderVO;
import com.reqorder.model.ReqOrderVO;


@Service("userService")
public class UserService {


		@Autowired
		UserRepository repository;
		
		private final PasswordEncoder passwordEncoder;
		
	    @Autowired
	    public UserService(PasswordEncoder passwordEncoder) {
	        this.passwordEncoder = passwordEncoder;
	    }
//		PasswordEncoder pe = new BCryptPasswordEncoder();

		public void addUser(UserVO userVO) {
			repository.save(userVO);
		}

		public void updateUser(UserVO userVO) {
			repository.save(userVO);
		}

		public void deleteUser(Integer userId) {
			if (repository.existsById(userId))
			    repository.deleteById(userId);
		}

		public boolean userIsExist(String comAccount) {
			if (repository.findByComAccount(comAccount)!=null) {
				return true;
			}else {
				return false;
			}
		}
		
		public boolean userIsExistByUni(String comUniNumber) {
			if (repository.findByComUniNumber(comUniNumber)!=null) {
				return true;
			}else {
				return false;
			}
		}
		
		public String getUserEmail(String comAccount) {
			UserVO userVO = repository.findByComAccount(comAccount);
			String email = userVO.getComMail();
			return email;
		}
		
		public void resetPassword(String comAccount,String newPassword) {
			UserVO userVO = repository.findByComAccount(comAccount);
			if (userVO!= null) {
	            
				String encodeNewPassword = passwordEncoder.encode(newPassword);
				
				userVO.setComPassword(encodeNewPassword);

	            repository.save(userVO);
	        }
		}
		
		public UserVO getOneUser(Integer userId) {
			Optional<UserVO> optional = repository.findById(userId);
//			return optional.get();
			return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		}

		public List<UserVO> getAll() {
			return repository.findAll();
		}
		
		public List<UserVO> getOneStatUser(UserVO userVO) {
	        List<UserVO> allUser = repository.findAll();
	        List<UserVO> validUser = new ArrayList<>();

	        for (UserVO User : allUser) {
	            if (User.getUserId() == userVO.getUserId() && User.getComIsValid() == 1) {
	                validUser.add(User);
	            }
	        }
	        return validUser;
	    }
		
	
		public Set<ReqOrderVO> getReqOrdersByUserId(Integer userId){
			return getOneUser(userId).getReqOrder();
		}
		
		public Set<ForumPostVO> getForumPostByfpUserid(Integer fpUserid){
		return getOneUser(fpUserid).getForumPost();
	}
		public Set<OrderVO> getOrderByordSellerid(Integer ordSellerid){
			return getOneUser(ordSellerid).getOrders();
		}
		
		public List<UserVO> getOneStatUserCom(UserVO userVO) {
		    List<UserVO> allUser = repository.findAll();
		    
		    return allUser.stream()
		                  .filter(uersVO -> uersVO.getUserId() == userVO.getUserId())
		                  .collect(Collectors.toList());
		}
}

