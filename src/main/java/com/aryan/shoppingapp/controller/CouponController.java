package com.aryan.shoppingapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.shoppingapp.model.Coupon;
import com.aryan.shoppingapp.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {
	
	private final CouponRepository couponRepository;
	
	
	/**
	 * Adds a new coupon.
	 * 
	 * @param couponName The name of the coupon.
	 * @param discount   The discount amount of the coupon.
	 * @return ResponseEntity containing the added coupon.
	 */
	@PostMapping("/add")
	public ResponseEntity<Coupon> addCoupon(@RequestParam("name") String couponName,@RequestParam("off")Long discount){
		Coupon coupon = new Coupon();
		coupon.setCouponName(couponName);
		coupon.setDiscount(discount);
		return ResponseEntity.ok(couponRepository.save(coupon));
	}
}
