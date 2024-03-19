package com.security.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.administrator.model.AdministratorRepository;
import com.administrator.model.AdministratorVO;
import com.user.model.UserRepository;
import com.user.model.UserVO;

@Service
public class UserDetailsService_impl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	

	@Override
    public UserDetails loadUserByUsername(String comAccount) throws UsernameNotFoundException {
        
		String[] parts = comAccount.split("-");
		String role = parts[0]; // 是'user'
		String userNumber = parts[1];
		
		switch(role) {
			case "admin":
				AdministratorVO administratorVO = administratorRepository.findByAdminAccount(userNumber);
				
				if (administratorVO == null) {
		            throw new UsernameNotFoundException("不存在此管理員帳號!");
		        }
				else {
					String authority = "ROLE_ADMIN";
					String realPassword = administratorVO.getAdminPassword();
					Boolean accountIsDisable = administratorVO.getAdminStat() == 0 ? true :false;
					
					return User.builder()
			        	    .username(userNumber)
			        	    .password(realPassword)
			        	    .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authority))
			        	    .disabled(accountIsDisable)
			        	    .build();
				}
			default:
				UserVO userVO = userRepository.findByComAccount(userNumber);
				
				if (userVO == null) {
					throw new UsernameNotFoundException("不存在此公司帳號!");
				}
				else {	        
					
					String authority = "ROLE_USER";
					String realPassword = userVO.getComPassword();
					Boolean accountIsDisable = userVO.getComStat() == 0 ? true :false;
					
					return User.builder()
							.username(userNumber)
							.password(realPassword)
							.authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authority))
							.disabled(accountIsDisable)
							.build();
					
				}
		
		
		
		}
        

        
    }
	
}
