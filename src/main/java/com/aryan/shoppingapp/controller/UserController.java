package com.aryan.shoppingapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.shoppingapp.model.User;
import com.aryan.shoppingapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	
	/**
	 * Adds a new user.
	 * 
	 * @return ResponseEntity containing the added user.
	 */
	@PostMapping("/add")
	public ResponseEntity<?> addUser(){
		User user =  userRepository.save(new User());
		return ResponseEntity.ok(user);
	}
	
}
