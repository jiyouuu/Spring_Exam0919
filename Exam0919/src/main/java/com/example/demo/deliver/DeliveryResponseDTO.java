package com.example.demo.deliver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryResponseDTO {

	private DeliveryStatus status;	// enum 자체 
	
	private String statusDisplay;    //  배송중, 배송완료 등 
}
// updatedDelivery.statusDisplay}로 변경되었습니다
// updatedDelivery.status === 'EATEN_BY_NURYEONG