package com.aryan.shoppingapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.shoppingapp.model.Inventory;
import com.aryan.shoppingapp.model.InventoryManager;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryManager inventoryManager;
	
	/**
	 * Retrieves details of the inventory.
	 * 
	 * @return ResponseEntity containing the inventory details.
	 */
	@GetMapping("")
	public ResponseEntity<Inventory> getInventoryDetails(){ 
		return ResponseEntity.ok(inventoryManager.getInventory());
	}
}
