package com.example.demo.exception;

import lombok.NoArgsConstructor;

//Unchecked 예외 (RuntimeException 상속) → throws 안 해도 됨
//Checked 예외 (Exception 상속) → throws 선언 필요
//보통 웹/서비스 로직에서 사용자/서비스 로직에서 직접 처리 안 하고 
//전역 예외 처리기(@ControllerAdvice)에서 처리할 예정이면 Unchecked Exception을 많이 씀.
@NoArgsConstructor
public class WaffleBearExperimentException extends RuntimeException{
    // 와플곰이 택배를 실험재료로 쓸 때 발생하는 예외
	private static final long serialVersionUID = 1L;
	
	// 메세지를 받는 생성자 (예외 발생 이유를 설명하는 문자열 전달)
	// 메시지를 전달할 수 있는 생성자를 포함시키면 뷰에 메시지 표시 가능
	// ex) throw new WaffleBearExperimentException("실험이 실패했습니다!");라면 
	// e.getMessage()하면  => 실험이 실패했습니다 나옴
	public WaffleBearExperimentException(String message) {
		super(message);
	}
	
	// 메세지 + 원인(exception)을 받는 생성자
	public WaffleBearExperimentException(String message, Throwable cause) {
		super(message, cause);
	}
	
	// 원인(exception)만 받는 생성자
	public WaffleBearExperimentException(Throwable cause) {
		super(cause);
	}

}
