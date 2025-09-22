package com.example.demo.deliver;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliverStatusController {
	private final DeliverService deliverService;
	

	@PreAuthorize("hasRole('ADMIN') || hasRole('INSPECTOR')")
	@PutMapping("/{id}/status")
	public ResponseEntity<DeliveryResponseDTO> updateStatus(@PathVariable(value="id") Long id, @RequestBody DeliveryRequestDTO deliveryRequestDto){
		Deliver d = this.deliverService.updateStatus(id, deliveryRequestDto);
		return ResponseEntity.ok(new DeliveryResponseDTO(d.getStatus(), d.getStatus().getDescription()));
	}
	
	
}

