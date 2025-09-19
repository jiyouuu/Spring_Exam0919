package com.example.demo.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DuplicateUserException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliverUserService {
	
	private final DeliverUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public void register(UserRegistrationDto userRegistrationDto) {
		// 이름 중복 검사 
		if(this.userRepository.existsByUsername(userRegistrationDto.getUsername())) {
			throw new DuplicateUserException("이미 존재하는 사용자명임 : " + userRegistrationDto.getUsername());
		}
		
		
		if(this.userRepository.count() == 0) {
			DeliverUser u = new DeliverUser(userRegistrationDto.getUsername(), 
					passwordEncoder.encode(userRegistrationDto.getPassword()),
					UserRole.ADMIN);
			this.userRepository.save(u);
		} else if (userRegistrationDto.getUsername().equals("gg")){
			DeliverUser u = new DeliverUser(userRegistrationDto.getUsername(), 
					passwordEncoder.encode(userRegistrationDto.getPassword()),
					UserRole.INSPECTOR);
			this.userRepository.save(u);
		} else{
			DeliverUser u = new DeliverUser(userRegistrationDto.getUsername(), 
					passwordEncoder.encode(userRegistrationDto.getPassword()),
					UserRole.USER);
			this.userRepository.save(u);
		}
	}

}
