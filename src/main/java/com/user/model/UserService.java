package com.user.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		public void updateBankInfo(Integer userId, String comContactPerson, String comBank, String accountNumber) {
	        
			
			UserVO userVO = getOneUser(userId);
	        if (userVO != null) {
	        	userVO.setComContactPerson(comContactPerson);
	            userVO.setComBank(comBank);
	            userVO.setAccountNumber(accountNumber);
	            repository.save(userVO);
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    }
		
		@Transactional
		public boolean changeUserPassword(Integer userId, String comPassword, String newPassword) {
			
		    UserVO userVO = getOneUser(userId);
		    if (userVO != null) {
		        // 使用passwordEncoder来验证旧密码是否与数据库中的密码匹配
		        if(passwordEncoder.matches(comPassword, userVO.getComPassword())) {
		            // 如果旧密码正确，使用passwordEncoder加密新密码
		            String encodedNewPassword = passwordEncoder.encode(newPassword);
		            // 更新用户密码
		            userVO.setComPassword(encodedNewPassword);
		            repository.save(userVO);
		            return true; // 更改密码成功
		        } else {
		            // 旧密码不匹配
		            return false; // 更改密码失败
		        }
		    } else {
		        throw new RuntimeException("User not found");
		    }
		}

}

