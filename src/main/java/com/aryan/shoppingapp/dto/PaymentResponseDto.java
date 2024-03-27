package com.aryan.shoppingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDto {
	private Long userId;
	private Long orderId;
	private String transactionId;
	private String status;
	private String description;
}
