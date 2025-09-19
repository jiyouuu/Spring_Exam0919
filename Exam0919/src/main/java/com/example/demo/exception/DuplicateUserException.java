package com.example.demo.exception;


import com.example.demo.user.UserRegistrationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DuplicateUserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;
	private UserRegistrationDto userRegistrationDto;

}
