package com.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.user.model.UserRepository;
import com.user.model.UserVO;

@Component
public class AuthenticationSuccessHandler_impl implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository repository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		User user = (User) authentication.getPrincipal();
		
		String account = user.getUsername();
		
		UserVO userVO = repository.findByComAccount(account);
		
		HttpSession session = request.getSession();
		
		session.setAttribute("loggingInUser", userVO);
		
		System.out.println(userVO.getComMail());//測試是否有成功取得VO"/userinformation/userpage"
		
		response.sendRedirect(request.getContextPath() + "/userinformation/userpage");

		
	}
		
}
