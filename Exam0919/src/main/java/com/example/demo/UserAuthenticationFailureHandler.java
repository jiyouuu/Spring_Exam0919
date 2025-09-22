package com.example.demo;

import java.io.IOException;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//로그인 실패 핸들러 
// 실패 핸들러가 세션에 저장한 메시지를 로그인 페이지 GET 요청 시 꺼내서 뷰로 넘김
@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override							// HttpServletRequest 클라이언트(브라우저 등)가 서버에 보낸 HTTP 요청을 나타내는 객체
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		
		// 실패 원인별 메시지
		String errorMessage = "로그인에 실패했습니다용가리!";
		
		if(exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 올바르지 않습니다용가리!";
		} else if(exception instanceof DisabledException) {
			errorMessage = "계정이 비활성화되었습니다용! 관리자에게 문의해주세용가리!";
		} else if(exception instanceof AccountExpiredException) {
			errorMessage = "계정이 만료되었습니다용! 갱신이 필요해용가리!";
		}
		
		// 세션에 에러 메세지 저장 후 리다이렉트
		request.getSession().setAttribute("errorMessage", errorMessage);
		response.sendRedirect("/login?error=true");
	}
}