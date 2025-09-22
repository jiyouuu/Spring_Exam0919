package com.example.demo;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Component 붙은 클래스는 Spring 컨테이너가 관리하는 객체가 되는 것
// 로그인 성공 핸들러 
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		
		// 사용자 역할에 따른 리다이렉트
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		for(GrantedAuthority auth : authorities) {
			if(auth.getAuthority().equals("ROLE_ADMIN")) {
				response.sendRedirect("/deliver/create");
				return ;
			}
			if(auth.getAuthority().equals("ROLE_INSPECTOR")) {
				response.sendRedirect("/deliver/list");
				return;
			}
		}
		
		// 기본 사용자는 메인 페이지
		response.sendRedirect("/");
	}
}