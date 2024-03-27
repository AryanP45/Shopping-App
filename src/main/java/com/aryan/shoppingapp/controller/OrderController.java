package com.aryan.shoppingapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.shoppingapp.dto.OrderAndTransactionDto;
import com.aryan.shoppingapp.dto.OrderDto;
import com.aryan.shoppingapp.enums.TransactionStatus;
import com.aryan.shoppingapp.model.Coupon;
import com.aryan.shoppingapp.model.Inventory;
import com.aryan.shoppingapp.model.InventoryManager;
import com.aryan.shoppingapp.model.Order;
import com.aryan.shoppingapp.model.Transaction;
import com.aryan.shoppingapp.model.User;
import com.aryan.shoppingapp.repository.CouponRepository;
import com.aryan.shoppingapp.repository.OrderRepository;
import com.aryan.shoppingapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    // Constructor-based dependency injection using @RequiredArgsConstructor annotation

    private final InventoryManager inventoryManager;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    /**
     * Places a new order for a user.
     * 
     * @param userId      The ID of the user placing the order.
     * @param quantity    The quantity of items to order.
     * @param couponName  The name of the coupon to apply.
     * @return ResponseEntity containing the details of the placed order.
     */
    @PostMapping("/{userId}/order")
    public ResponseEntity<?> giveOrder(@PathVariable Long userId, @RequestParam("qty") Long quantity,
            @RequestParam("coupon") String couponName) {

        Inventory inventory = inventoryManager.getInventory();

        // Check if the requested quantity is available
        if (inventory.getAvailable() < quantity)
            return new ResponseEntity<>("Invalid Quantity", HttpStatus.NOT_FOUND);

        // Check the validity of the coupon code
        Coupon coupon = couponRepository.findByCouponName(couponName);
        if (coupon == null)
            return new ResponseEntity<>("Invalid Coupon", HttpStatus.NOT_FOUND);

        // Check if the user exists
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            return new ResponseEntity<>("Invalid User", HttpStatus.NOT_FOUND);

        // Create a new order instance
        Order order = new Order();
        order.setDate(new Date());
        order.setCouponName(couponName);
        
        // Calculate discount
        Long totalAmount = inventory.getPrice() * quantity;
        double discountPercentage = coupon.getDiscount() / 100.0;
        Long finalDiscountedAmount = (long) (totalAmount - (totalAmount * discountPercentage));

        order.setAmount(finalDiscountedAmount);
        Order savedOrder = orderRepository.save(order);

        // Add the recent order to the user
        List<Order> orders = user.getOrders();
        orders.add(savedOrder);
        user.setOrders(orders);
        userRepository.save(user);

        // Update inventory after order
        inventory.setAvailable(inventory.getAvailable() - quantity);
        inventory.setOrdered(inventory.getOrdered() + quantity);
        inventoryManager.setInventory(inventory);

        return ResponseEntity
                .ok(new OrderDto(savedOrder.getId(), savedOrder.getId(), quantity, finalDiscountedAmount, couponName));
    }


}
