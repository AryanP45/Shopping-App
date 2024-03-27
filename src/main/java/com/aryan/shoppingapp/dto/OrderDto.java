package com.aryan.shoppingapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {
	Long userId;
	Long orderId;
	Long quantity;
	Long amount;
	String couponName;
}
