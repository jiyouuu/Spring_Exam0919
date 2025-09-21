package com.example.demo.deliver;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.exception.CustomValidationException;
import com.example.demo.user.DeliverUser;
import com.example.demo.user.DeliverUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeliverController {
	private final DeliverService deliverService;
	private final DeliverUserService userService;
	
	@GetMapping
	public String main() {
		return "index";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deliver/create")
	public String createDeliveries(DeliverDto deliverDto, Model model) {
		List<DeliverUser> users = this.deliverService.findAll();
		model.addAttribute("users", users);
		return "deliveries/create";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/deliver/create")
	public String createDelivery(@Valid DeliverDto deliverDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationException("새 배송 등록 유효성 검사 실패", bindingResult, errors);
		}
		this.deliverService.createDeliver(deliverDto);
		return "deliveries/list";
	}
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/deliver/list")
	public String showDeliveries(DeliverDto deliverDto, Model model, Principal principal) {
		DeliverUser user = this.userService.findCurrentUser(principal);
		List<Deliver> deliveries = this.deliverService.findDeliveries();
		model.addAttribute("deliveries", deliveries);
		model.addAttribute("currentUser", user);
		model.addAttribute("deliveryStatuses", DeliveryStatus.values());
		return "deliveries/list";
	}
}
