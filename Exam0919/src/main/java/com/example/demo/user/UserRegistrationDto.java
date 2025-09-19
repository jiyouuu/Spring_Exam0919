package com.example.demo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
	
	@NotBlank(message = "사용자명은 필수입니다")
	@Size(min = 2, max = 20, message = "사용자명은 2-20자 사이여야 합니다")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수입니다")
	@Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다")  
	private String password;
	
	@NotBlank(message = "비밀번호 확인은 필수입니다")
	private String confirmPassword;
	
	// 비밀번호 확인 메서드 
	public boolean isPasswordMatching() {
		return password != null && password.equals(confirmPassword);
	}
}
