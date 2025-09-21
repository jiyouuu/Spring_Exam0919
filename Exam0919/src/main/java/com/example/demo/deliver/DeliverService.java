package com.example.demo.deliver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.user.DeliverUser;
import com.example.demo.user.DeliverUserRepository;
import com.example.demo.user.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliverService {
	private final DeliverRepository deliverRepository;
	private final DeliverUserRepository deliverUserRepository;
	
	public List<DeliverUser> findAll() {
		return this.deliverUserRepository.findByRoleNot(UserRole.ADMIN);     // WHERE role <> 'ADMIN' 조건이 들어감 SQL문이!!
	}
	
	public void createDeliver(DeliverDto deliverDto) {
		// ORDERED("주문완료", "🎁")
		DeliverUser user = this.deliverUserRepository.findById(deliverDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("회원 없음"));
		Deliver d = new Deliver(deliverDto.getCustomerName(), deliverDto.getAddress(), deliverDto.getProductName(),DeliveryStatus.ORDERED,user);
		this.deliverRepository.save(d);
	}

	public List<Deliver> findDeliveries() {
		return this.deliverRepository.findAll();
	}

	public Deliver updateStatus(Long id, DeliveryRequestDTO deliveryRequestDto) {
		Deliver d = this.deliverRepository.findById(id).get();
		String newStatus = deliveryRequestDto.getStatus();
		d.updateStatus(DeliveryStatus.valueOf(newStatus));
		d.onUpdate();
		return this.deliverRepository.save(d);
	}

	

}
