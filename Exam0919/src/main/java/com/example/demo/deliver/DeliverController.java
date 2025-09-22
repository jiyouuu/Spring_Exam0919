package com.example.demo.deliver;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.CustomValidationException;
import com.example.demo.user.DeliverUser;
import com.example.demo.user.DeliverUserService;
import com.example.demo.user.UserRole;

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
	public String showDeliveries(DeliverDto deliverDto, Model model, Principal principal,
								@RequestParam(value = "page", defaultValue = "0") int page,
								@RequestParam(value = "size", defaultValue = "2") int size,
								@RequestParam(value = "keyword", required = false) String keyword) {
		DeliverUser user = this.userService.findCurrentUser(principal);

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		
		if(user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.INSPECTOR)) {
			Page<Deliver> allDeliveries = this.deliverService.findAll(pageable,keyword);
			// List<Deliver> allDeliveries = this.deliverService.findAllDeliveries();
			model.addAttribute("deliveries", allDeliveries);
			model.addAttribute("currentUser", user);
			model.addAttribute("deliveryStatuses", DeliveryStatus.values());
			
		}
		else {
			Page<Deliver> myDeliveries = this.deliverService.findDeliveries(user,pageable,keyword);
			// List<Deliver> myDeliveries = this.deliverService.findDeliveries(user);
			model.addAttribute("deliveries", myDeliveries);
			model.addAttribute("currentUser", user);
		}
		return "deliveries/list";
	}
	
	
	
	
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/deliver/{id}")
	public String showDetail(@PathVariable(value = "id") Long id, Model model) {
		Deliver deliver = this.deliverService.findDeliverDetail(id);
		model.addAttribute("delivery", deliver);
		return "deliveries/detail";
	}
	
	
	
	
	
	
	
	
	
}
