package com.example.demo.exception;


import java.util.Random;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.user.DeliverUserRepository;
import com.example.demo.user.UserRole;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
					//이 파일은 전체 예외처리를 관리하려고 만들어둔 파일
@ControllerAdvice    // Controller에 관련된 전역(글로벌) 설정을 처리할 때 사용
public class GlobalExceptionHandler {
	
	private final DeliverUserRepository userRepository;
	// 캐릭터의 말(quote)을 랜덤으로 보여주기 위해 사용
	private final Random random = new Random();
	
	// 컨트롤러에서 WaffleBearExperimentException 발생 시 자동으로 호출됨
	@ExceptionHandler(WaffleBearExperimentException.class)
	public String handleWaffleBearException(WaffleBearExperimentException e, Model model) {
		 String[] waffleBearQuotes = {
		            "아! 이 택배 상자가 완벽한 실험 재료네요!",
		            "잠깐만요! 이것도 과학적으로 분석해봐야겠어요!",
		            "으음... 이 택배에서 흥미로운 화학 반응이 일어날 것 같은데요?"
		        };
		 
		 model.addAttribute("errorType", "wafflebear");  // errorType: 어떤 캐릭터 예외인지 구분
		 model.addAttribute("errorMessage", e.getMessage());
		 model.addAttribute("characterQuote", waffleBearQuotes[random.nextInt(waffleBearQuotes.length)]);
		 return "error/character-error";  // 즉, 예외가 발생하면 해당 뷰로 포워딩되고, 모델 데이터를 이용해 UI에서 보여줌
	}
	
	
	 @ExceptionHandler(MoodSwingException.class)
	 public String handleMoodSwingException(MoodSwingException e, Model model) {
		 String[] tosimQuotes = {
		            "오늘은... 기분이 별로예요... 일기 쓰기 싫어요...",
		            "흠... 마음이 복잡해서 배송 상태를 바꾸고 싶지 않아요",
		            "이런 날에는 그냥 조용히 있고 싶어요..."
		  };
		 model.addAttribute("errorType", "tosim");
	     model.addAttribute("errorMessage", e.getMessage());
	     model.addAttribute("characterQuote", tosimQuotes[random.nextInt(tosimQuotes.length)]);
	     return "error/character-error";
		 
	 }
	 
	 // 중복된 사용자 예외 처리 
	 @ExceptionHandler(DuplicateUserException.class)
	 public String handleDuplicateUserException(DuplicateUserException e, Model model) {
		 model.addAttribute("errorMessage", e.getMessage());
		 model.addAttribute("userRegistrationDto", e.getUserRegistrationDto());
		 return "auth/register";
	 }
	 
	 

	// 유효성 검사 실패 처리
	@ExceptionHandler(CustomValidationException.class)
	 public String validationException(CustomValidationException e, Model model) {
		 if(e.getBindingResult().getObjectName().equals("userRegistrationDto")) {
			 model.addAttribute("userRegistrationDto", e.getBindingResult().getTarget());    // BindingResult 안에는 검증 대상 객체(DTO)가 들어있음
				// 그래서 폼에서 입력한 그대로 DTO 뷰에 보내는거임 
			 // BindingResult.{DTO이름} 으로 보내야, th:errors나 #fields.hasErrors()가 정상 작동함 
			 model.addAttribute("org.springframework.validation.BindingResult.userRegistrationDto", e.getBindingResult()); // 유효성 에러 표시를 위해 BindingResult객체를 연결 

			 return "auth/register";
		 }
		 if(e.getBindingResult().getObjectName().equals("deliverDto")) {
			 model.addAttribute("deliverDto", e.getBindingResult().getTarget());
			 model.addAttribute("org.springframework.validation.BindingResult.deliverDto", e.getBindingResult());
			 model.addAttribute("users", this.userRepository.findByRoleNot(UserRole.ADMIN));
		 }
		 
		 return "deliveries/create";
		
	 }

	
	 
}










