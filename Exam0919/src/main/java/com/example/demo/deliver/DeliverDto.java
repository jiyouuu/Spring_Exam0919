package com.example.demo.deliver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliverDto {

	@NotBlank(message = "배송받을 고객의 이름을 입력하세요!")
	private String customerName;
	@NotBlank(message = "배송할 상품의 이름을 입력하세요!")
	private String productName;
	@NotBlank(message = "배송받을 주소를 상세히 입력하세요!")
	private String address;
	@NotNull(message = "배송받을 고객을 선택하세요!")
	private Long userId;
}
