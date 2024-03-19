package com.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.administrator.model.AdministratorRepository;
import com.administrator.model.AdministratorVO;
import com.user.model.UserRepository;
import com.user.model.UserVO;

@Component
public class AuthenticationSuccessHandler_impl implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository repository;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		User user = (User) authentication.getPrincipal();
		
		boolean isAdmin = false;
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		
		if(isAdmin) {
			String account = user.getUsername();
			AdministratorVO administratorVO = administratorRepository.findByAdminAccount(account);
			
			HttpSession session = request.getSession();
			
			session.setAttribute("loggingInAdmin", administratorVO);
			
			System.out.println(administratorVO.getAdminName());//測試是否有成功取得VO"/userinformation/userpage"
			
			response.sendRedirect(request.getContextPath() + "/authorization_new_member");//導向管理員葉面
		}
		else {
			String account = user.getUsername();
			
			UserVO userVO = repository.findByComAccount(account);
			
			HttpSession session = request.getSession();
			
			session.setAttribute("loggingInUser", userVO);
			
			System.out.println(userVO.getComMail());//測試是否有成功取得VO"/userinformation/userpage"
			
			response.sendRedirect(request.getContextPath() + "/userinformation/userpage");
		}
		

		
	}
		
}
