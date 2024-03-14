package com.security.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.model.UserRepository;
import com.user.model.UserVO;

@Service
public class UserDetailsService_impl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
    public UserDetails loadUserByUsername(String comAccount) throws UsernameNotFoundException {
        UserVO userVO = userRepository.findByComAccount(comAccount);
        
        
        if (userVO == null) {
            throw new UsernameNotFoundException("不存在此公司帳號!");
        }
        else {
//        System.out.println(userVO.getComName());
        
        
        String authority = "ROLE_USER";
        String realPassword = userVO.getComPassword();
        Boolean accountIsDisable =userVO.getComStat() == 0 ? true :false;
        
        return User.builder()
        	    .username(comAccount)
        	    .password(realPassword)
        	    .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authority))
        	    .disabled(accountIsDisable)
        	    .build();
        
        }
        
     
        
    }
	
}
