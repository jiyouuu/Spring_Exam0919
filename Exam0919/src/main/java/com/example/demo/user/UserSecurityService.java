package com.example.demo.user;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{

	private final DeliverUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DeliverUser u = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("사용자 찾을 수 없음 " +username));
		
		u.setLastLoginAt(LocalDateTime.now());
		this.userRepository.save(u);
		return User.builder()
				.username(u.getUsername())
				.password(u.getPassword())
				// u 객체의 역할(즉 권한)을 시큐리티에 맞게 매핑해주는 메서드 .
				.roles(u.getRole().name())    
				.accountExpired(!u.isAccountNonExpired())   // 계정 만료되었다면 만료해라
				.accountLocked(!u.isAccountNonLocked())   // 기존 계정이 잠겨져 있다면 잠궈라
				.credentialsExpired(!u.isCredentialsNonExpired())
				.disabled(!u.isEnabled())
				.build();			
	}

	
}
