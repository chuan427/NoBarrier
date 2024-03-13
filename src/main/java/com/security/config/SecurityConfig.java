package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.security.model.UserDetailsService_impl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService_impl userDetailsService_impl;
	
	@Autowired
	private AuthenticationSuccessHandler_impl authenticationSuccessHandler_impl;
	
	
//	@Bean
//	DaoAuthenticationProvider daoAuthenticationProvider() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
//		daoAuthenticationProvider.setUserDetailsService(userDetailsService_impl);
//		return daoAuthenticationProvider;
//	}
//	
//
//	@Bean
//	DaoAuthenticationProvider daoAuthenticationProvider2() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
//		daoAuthenticationProvider.setUserDetailsService(userDetailsService_impl);
//		return daoAuthenticationProvider;
//	}
	
	
//
//	private class AuthenticationProvider2 implements  AuthenticationProvider{
//
//		@Override
//		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//			String username = authentication.getName();
//			String password = authentication.getCredentials().toString();
//			
//			
//			if(username.contains("company")) {
//				
//				String userNameinDB;
//				String passwordinDB="";
//				if(getPasswordEncoder().matches(password,passwordinDB )) {
//					return new UsernamePasswordAuthenticationToken(username, password , AuthorityUtils.createAuthorityList("ROLE_COMPANY"));
//				}else {
//					
//					throw new BadCredentialsException("認證錯誤");
//				}
//				
//
//			} else {
//				
//			}
//			
//			return null;
//		}
//
//		@Override
//		public boolean supports(Class<?> authentication) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//		
//	}
	
//	
//	
//	
//	
//	
//	@Bean 
//	AuthenticationManager  authenticationManager(HttpSecurity http){
//		return new ProviderManager(daoAuthenticationProvider(),daoAuthenticationProvider2() );
//	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
				
				// 表單提交
				http.formLogin()
					// 自定義登入頁面
					.loginPage("/loginpage")
					// loginpage.html 表單 action 內容
					.loginProcessingUrl("/login")//頁面url
					// 登入成功之後要造訪的頁面
					.successHandler(authenticationSuccessHandler_impl)
					// 登入失敗後要造訪的頁面
					.failureForwardUrl("/loginfail");
				
				// 授權認證
				http.authorizeHttpRequests()
					// 不需要被認證的頁面：/loginpage
					.antMatchers("/").permitAll()
					.antMatchers("/loginpage").hasRole("ANONYMOUS")
					.antMatchers("/webjars/**").permitAll()
					.antMatchers("/loginsuccess").permitAll()
					.antMatchers("/loginfail").permitAll()
					.antMatchers("/checkAccountExists").permitAll()
					.antMatchers("/forgetPasswordPage").permitAll()
					.antMatchers("/ad/**").permitAll()
					.antMatchers("/images/**").permitAll()
					
//					// 權限判斷
//					// 必須要有 admin 權限才可以訪問
//					.antMatchers("/adminpage").hasAuthority("admin")
//					// 必須要有 manager 角色才可以訪問
//					.antMatchers("/managerpage").hasRole("manager")
//					// 其他指定任意角色都可以訪問
//					.antMatchers("/employeepage").hasAnyRole("manager", "employee")
					// 其他的都要被認證
					.anyRequest().authenticated();
				
				 http.csrf().disable(); // 關閉 csrf 防護
				
				// 登出
				http.logout()
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/login")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")); // 可以使用任何的 HTTP 方法登出
			
				// 異常處理
				http.exceptionHandling()
					.accessDeniedPage("/loginsuccess"); //用來防止登入過的人再到登入頁面
														//但這應該不是常規作法，因為我的設計目前會accessDeniedPage的情況只有登入的人想造訪登入頁面
														//這個缺點是如果有其他權限角色設定如果發生accessDeniedPage都會被導到/loginsuccess
//					.accessDeniedHandler(myAccessDeniedHandler);
				
				// 勿忘我（remember-me）
				http.rememberMe()
					.userDetailsService(userDetailsService_impl)
					.tokenValiditySeconds(60*60*24); // 通常都會大於 session timeout 的時間
	}



	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}
