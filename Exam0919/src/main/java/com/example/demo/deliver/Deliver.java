package com.example.demo.deliver;

import java.time.LocalDateTime;

import com.example.demo.user.DeliverUser;

import groovy.transform.builder.Builder;
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
	private LocalDateTime updatedAt;
	
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

//	public Deliver(String customerName, String address, String productName, DeliveryStatus status, DeliverUser user,
//			LocalDateTime createdAt) {
//		this.customerName = customerName;
//		this.address = address;
//		this.productName = productName;
//		this.status = status;
//		this.user = user;
//		this.createdAt = createdAt;
//	}
	
	@Builder
	public Deliver(String customerName, String address, String productName, DeliveryStatus status, DeliverUser user) {
		this.customerName = customerName;
		this.address = address;
		this.productName = productName;
		this.status = status;
		this.user = user;
	}
	
	public void updateStatus(DeliveryStatus status) {
		this.status = status;
	}
	
}






