package com.user.model;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forumpost.model.ForumPostVO;
import com.reqorder.model.ReqOrderVO;


@Service("userService")
public class UserService {


		@Autowired
		UserRepository repository;

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

		public UserVO getOneUser(Integer userId) {
			Optional<UserVO> optional = repository.findById(userId);
//			return optional.get();
			return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		}

		public List<UserVO> getAll() {
			return repository.findAll();
		}
	
		public Set<ReqOrderVO> getReqOrdersByUserId(Integer userId){
			return getOneUser(userId).getReqOrder();
		}
		
		public Set<ForumPostVO> getForumPostByfpUserid(Integer fpUserid){
		return getOneUser(fpUserid).getForumPost();
	}
		
}
