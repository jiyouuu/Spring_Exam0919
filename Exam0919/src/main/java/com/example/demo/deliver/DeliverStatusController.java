package com.example.demo.deliver;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
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
	
	// /api/deliveries/${deliveryId}/status`
	@PreAuthorize("hasRole('ADMIN') || hasRole('INSPECTOR')")
	@PutMapping("/{id}/status")
	public ResponseEntity<DeliveryResponseDTO> updateStatus(@PathVariable(value="id") Long id, @RequestBody DeliveryRequestDTO deliveryRequestDto){

		Deliver d = this.deliverService.updateStatus(id, deliveryRequestDto);

		return ResponseEntity.ok(new DeliveryResponseDTO(d.getStatus(), d.getStatus().getDescription()));
	}
	
	
}
//<div sec:authorize="hasRole('ADMIN') or hasRole('INSPECTOR')">
//<select class="status-select" 
//        th:attr="data-delivery-id=${delivery.id}"
//        onchange="updateDeliveryStatus(this)">
//    <option th:each="status : ${deliveryStatuses}"
//            th:value="${status}"
//            th:text="${status.displayName}"
//            th:selected="${status == delivery.status}">
//        상태
//    </option>
//</select>
//</div>

//<div sec:authorize="hasRole('USER')">
//<span class="status-badge" th:class="'status-badge status-' + ${delivery.status}"
//      th:text="${delivery.statusDisplay}">상태</span>
//</div>