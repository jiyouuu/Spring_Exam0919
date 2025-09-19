package com.example.demo.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.deliver.Deliver;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class DeliverUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	// 한 명의 User가 여러 Deliver를 가질 수 있다
	// mappedBy = "user" → Deliver의 user 필드가 DB에서 FK를 관리한다는 뜻.
	// 즉 "User의 delivers는 Deliver의 user 필드에 의해 매핑된다"
	// User 테이블에는 FK 컬럼이 생기지 않음, 관계는 Deliver 테이블에서 관리.
	// User를 저장/삭제하면 Deliver도 자동으로 저장/삭제 (cascade = ALL)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Deliver> delivers = new ArrayList<>();
	

	@Builder
	public DeliverUser(String username, String password, UserRole role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	 @Column
	 private LocalDateTime lastLoginAt;


	 public void setLastLoginAt(LocalDateTime lastLoginAt) {
		 this.lastLoginAt = lastLoginAt;
	 }
	 
	 @Column(nullable = false)
	 private boolean enabled = true;
	   
	 @Column(nullable = false)
	 private boolean accountNonExpired = true;
	    
	 @Column(nullable = false)
	 private boolean accountNonLocked = true;
	    
	 @Column(nullable = false)
	 private boolean credentialsNonExpired = true; 
	 
	 public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	 public void setAccountNonLocked(boolean accountNonLocked) {
		 this.accountNonLocked = accountNonLocked;
	 }

	 public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		 this.credentialsNonExpired = credentialsNonExpired;
	 }

	 public void setEnabled(boolean enabled) {
		 this.enabled = enabled;
	 }


}
