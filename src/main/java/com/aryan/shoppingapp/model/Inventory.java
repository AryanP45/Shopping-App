package com.aryan.shoppingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Inventory {
	
	Long ordered;
	
	Long price;
	
	Long available;

}
