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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeliverUserController {

	private final DeliverUserService userService;
	
	@GetMapping("/login")
	public String login(HttpServletRequest request, @RequestParam(name = "error", required = false) String error, @RequestParam(name = "logout", required = false) String logout, Model model) {
		if("true".equals(error)) {
			// 세션에서 error꺼내기 
			HttpSession session = request.getSession(false);  // 현재 요청에 연결된 세션이 있으면 그 세션을 반환하고, 없다면 세션을 새로 생성하지 않고 null을 반환
															  // request.getSession() 또는 request.getSession(true)는 세션이 없으면 새로 만들어서 반환
			if(session != null) {
				String errorMessage = (String) session.getAttribute("errorMessage");
				model.addAttribute("errorMessage", errorMessage);
			// 메시지를 한번 꺼내고 제거해서, 새로고침 시 메시지가 계속 나오지 않도록 함
				session.removeAttribute("errorMessage");   
			}
			
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
