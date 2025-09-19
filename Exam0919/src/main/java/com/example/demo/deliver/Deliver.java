package com.example.demo.deliver;

import java.time.LocalDateTime;

import com.example.demo.user.DeliverUser;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Deliver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String customerName;
	
	@Column(nullable = false,length = 500)
	private String address;
	
	@Column(nullable = false)
	private String productName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private DeliverUser user;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime updateAt;
	
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updateAt = LocalDateTime.now();
	}
}






