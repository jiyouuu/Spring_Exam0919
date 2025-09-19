package com.example.demo.deliver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

	ORDERED("주문완료", "🎁"),
	PROCESSING("토심이 일기 작성중", "📝"),
    SHIPPING("배송중", "🚚"),
    DELIVERED("배송완료", "✅"),
    EATEN_BY_NURYEONG("누렁이가 먹음", "🐕");
	
	private final String description; 
	private final String emoji;
	

	public String getDisplayName() {
		return emoji + "" + description;
	}
}
