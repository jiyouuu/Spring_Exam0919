package com.example.demo.deliver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeliverController {
	private final DeliverService deliverService;
	
	@GetMapping
	public String main() {
		return "index";
	}
	
	@GetMapping("/deliver/create")
	public String createDeliveries(DeliverDto deliverDto) {
		return "deliveries/create";
	}
	
	@PostMapping("/deliver/create")
	public String createDelivery(@Valid DeliverDto deliverDto) {
		this.deliverService.createDeliver(deliverDto);
		return "deliveries/list";
	}
	
	
	@GetMapping("/deliver/list")
	public String showDeliveries(DeliverDto deliverDto) {
		// currentUser
		// deliveries 배송리스트
		return "deliveries/list";
	}
}
