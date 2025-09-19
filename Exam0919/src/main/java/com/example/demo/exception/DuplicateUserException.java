package com.example.demo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateUserException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DuplicateUserException(String message) {
		super(message);
	}
	
	// 메세지 + 원인(exception)을 받는 생성자
	public DuplicateUserException(String message, Throwable cause) {
		super(message, cause);
	}
			
	// 원인(exception)만 받는 생성자
	public DuplicateUserException(Throwable cause) {
		super(cause);
	}
	
}
