package com.aryan.shoppingapp.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderAndTransactionDto {
	Long orderId;
	Long amount;
	Date date;
	String couponName;
	String transactionId;
	String status;
}
