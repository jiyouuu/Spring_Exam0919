package com.example.demo.deliver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public List<Deliver> findAllDeliveries() {
		return this.deliverRepository.findAll();
	}

	public Deliver updateStatus(Long id, DeliveryRequestDTO deliveryRequestDto) {
		Deliver d = this.deliverRepository.findById(id).get();
		String newStatus = deliveryRequestDto.getStatus();
		d.updateStatus(DeliveryStatus.valueOf(newStatus));
		d.onUpdate();
		return this.deliverRepository.save(d);
	}

	
	public Deliver findDeliverDetail(Long id) {
		return this.deliverRepository.findById(id).get();
	}

	public Page<Deliver> findAll(Pageable pageable, String keyword) {
		// 검색어 없으면 그냥 페이징만 처리
		if(keyword.trim().isEmpty() || keyword == null) {
			// Spring Data JPA에서는 **JpaRepository / PagingAndSortingRepository**에서 제공하는 findAll(Pageable pageable) 메서드가 
			// 자동으로 페이징을 지원하도록 되어있음 
			return this.deliverRepository.findAll(pageable);
		}
		String lower_keyword = keyword.trim().toLowerCase();
		return this.deliverRepository.findByKeyword(lower_keyword, pageable);
	}


//	public Page<Deliver> findDeliveries(DeliverUser user) {
//		DeliverUser u = this.deliverUserRepository.findByUsername(user.getUsername())
//				.orElseThrow(() -> new IllegalArgumentException("회원 없음!"));
//		return this.deliverRepository.findByuserId(u.getId());
//	}

	public Page<Deliver> findDeliveries(DeliverUser user, Pageable pageable, String keyword) {
		DeliverUser u = this.deliverUserRepository.findByUsername(user.getUsername())
			.orElseThrow(() -> new IllegalArgumentException("회원 없음!"));
		List<Deliver> deliveries = this.deliverRepository.findByuserId(u.getId());
		if(keyword.trim().isEmpty() || keyword == null) {
			// return this.deliverRepository.findBy
		}
		
		
		return null;
	}

}
