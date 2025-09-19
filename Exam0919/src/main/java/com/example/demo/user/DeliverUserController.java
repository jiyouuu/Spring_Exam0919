package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeliverUserController {

	private final DeliverUserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "/auth/login";
	}
	
	@GetMapping("/register")
	public String register(UserRegistrationDto userRegistrationDto) {
		return "/auth/register";
	}
	
	@PostMapping("/register")
	public String register(@Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult) {
		
		// 비밀번호 확인 
		// rejectValue : 특정한 필드와 관련된 오류를 등록하는 메서드 
		// (해당 필드 / 속성, 오류식별코드, "메세지")
		// -> 폼 데이터 특정 
		if(!userRegistrationDto.isPasswordMatching()) {
			// error.user = 사용자 관련 오류가 발생했습니다.
			bindingResult.rejectValue("confirmPassword", "error.user", "비밀번호가 일치하지 않습니다");
		}
		this.userService.register(userRegistrationDto);
		return "redirect:/login";
	}
	
	
	
	@PostMapping("/logout")
	public String logout() {
		return "redirect:/";
	}

}
