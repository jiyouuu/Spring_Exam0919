package com.example.demo.exception;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.example.demo.user.DeliverUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomValidationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private String message;
	private BindingResult bindingResult;
	
	private Map<String, String> errors;

}
