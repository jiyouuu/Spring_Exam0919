package com.example.demo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.CustomValidationException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeliverUserController {

	private final DeliverUserService userService;
	
	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error, @RequestParam(name = "logout", required = false) String logout, Model model) {
		if("true".equals(error)) {
			model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다! 다시 시도해주세요! ");
		} 
		if("true".equals(logout)){
			model.addAttribute("logoutMessage", "안전하게 나가셨네요 ! 다음에도 배송 확인하러 오세요 ! ");
		}
		return "/auth/login";
	}
	
	
	@GetMapping("/register")
	public String register(UserRegistrationDto userRegistrationDto) {
		return "/auth/register";
	}
	

	@PostMapping("/register")
	public String register(@Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		// 비밀번호 확인 
		// rejectValue : 특정한 필드와 관련된 오류를 등록하는 메서드 
		// (해당 필드 / 속성, 오류식별코드, "메세지")
		// -> 폼 데이터 특정 
		if(!userRegistrationDto.isPasswordMatching()) {
			// error.user = 사용자 관련 오류가 발생했습니다.
			bindingResult.rejectValue("confirmPassword", "error.user", "비밀번호가 일치하지 않습니다");
		}
		
		if (bindingResult.hasErrors()) {
				Map<String, String> errors = new HashMap<>();
				
				for(FieldError error : bindingResult.getFieldErrors()) {
					errors.put(error.getField(), error.getDefaultMessage());
				}
				throw new CustomValidationException("회원가입 유효성 검사 실패" , bindingResult, errors);
		 }

		
		this.userService.register(userRegistrationDto);
		redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다!!");    
		return "redirect:/login?success";
		
	}
	
	
	
	@PostMapping("/logout")
	public String logout() {
		return "redirect:/login?logout=true";
	}

}
