package com.example.demo.user;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority{

	USER("일반고객", "ROLE_USER"),
	ADMIN("급하냥", "ROLE_ADMIN"),	
	INSPECTOR("바쁘개", "ROLE_INSPECTOR");
	
	private final String displayName;
	private final String authority;
	
	@Override
	public String getAuthority() {return authority;}
}
