package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.demo.user.UserSecurityService;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	private final UserSecurityService userDetailService;
	private final UserAuthenticationSuccessHandler successHandler;
	private final UserAuthenticationFailureHandler failureHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login","/register", "/css/**", "/js/**", "/images/**","/favicon.ico","/h2-console/**").permitAll()
						.requestMatchers("/deliveries/create", "/api/deliveries/${deliveryId}/status").hasRole("ADMIN")
						.requestMatchers("/api/deliveries/${deliveryId}/status").hasRole("INSPECTOR")
						.anyRequest().authenticated() // 나머지는 인증 필요
						)
				.csrf(csrf -> {
					csrf
					.ignoringRequestMatchers("/h2-console/**")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());	
				})
				.headers(headers -> headers
					        .frameOptions(frame -> frame.sameOrigin())      // H2 콘솔 iframe 허용
				)
				// 세션 관리와 세션 관련 설정을 정의할때 사용
				.sessionManagement(session ->session
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.maximumSessions(1)
						.maxSessionsPreventsLogin(false)
						)
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")  //로그인 처리 URL
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/", true) // ,true의 의미 : 항상 이 URL로 '리다이렉트' 강제화 
						.failureUrl("/login?error=true") // 로그인 실패시 error 파라미터 전달 
						.successHandler(successHandler)  // 성공 핸들러
						.failureHandler(failureHandler)  // 실패 핸들러
						.permitAll()
						)
				.logout(logout ->logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=true")   // 로그아웃 성공시 logout 파라미터 전달 
						.invalidateHttpSession(true)	// 세션 무효화
						.deleteCookies("JSESSIONID")  // 쿠키 삭제
						.clearAuthentication(true)   //인증 정보 삭제
						.permitAll()
						)
				.exceptionHandling(ex -> ex
						// 인증되지 않은 사용자가 접근을 시도했다면 
						.authenticationEntryPoint((request, response, authException) -> {
							System.out.println("인증되지 않은 접근 시도: " + request.getRequestURI());
		                    response.sendRedirect("/login");
						})
						// 사용자가 권한이 부족한 상태에서 특정 리소스에 접근하려고 한다면
						.accessDeniedHandler((request, response, accessDeniedException)->{
							response.sendRedirect("/access-denied");
						})
				 )
				.rememberMe(remember -> remember
						.key("SecretKey")
						.tokenValiditySeconds(7* 24 * 60 * 60)  // 7일
						 // 사용자 정보를 조회하는데 사용할 UserDetailsService를 설정할 때 사용 
						.userDetailsService(userDetailService)
						) 
				.build();
	}
	

	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12); // strength를 12로 설정하여 더 강력한 암호화
	}
	
	
}
