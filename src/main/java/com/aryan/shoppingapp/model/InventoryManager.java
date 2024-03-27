package com.aryan.shoppingapp.model;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

/**
 * Component responsible for managing the inventory.
 */
@Component
@Data
public class InventoryManager {

    private Inventory inventory;

    /**
     * Initializes the inventory with default values after construction.
     */
    @PostConstruct
    public void init() {
        inventory = new Inventory(0L, 100L, 100L);
    }
    
}
