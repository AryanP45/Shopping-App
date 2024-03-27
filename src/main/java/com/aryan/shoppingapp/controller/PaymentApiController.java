package com.aryan.shoppingapp.controller;

import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.shoppingapp.dto.PaymentResponseDto;
import com.aryan.shoppingapp.enums.TransactionStatus;
import com.aryan.shoppingapp.model.Order;
import com.aryan.shoppingapp.model.Transaction;
import com.aryan.shoppingapp.repository.OrderRepository;
import com.aryan.shoppingapp.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentApiController {

	private final OrderRepository orderRepository;
	private final TransactionRepository transactionRepository;

	/**
	 * Mocks the payment API and returns random responses. The response is stored
	 * in a transaction and linked with the specified order.
	 * 
	 * @param userId   The ID of the user making the payment.
	 * @param orderId  The ID of the order for which the payment is being made.
	 * @param amount   The amount to be paid.
	 * @return ResponseEntity with the mocked payment response.
	 */
	@PostMapping("/{userId}/{orderId}/pay")
	public ResponseEntity<?> makePayment(@PathVariable Long userId, @PathVariable Long orderId,
			@RequestParam("amount") Long Amount) {
		// Mock response status codes and descriptions
		String[] statusCodes = { "200", "400", "400", "400", "504", "405" };
		String[] descriptions = { "successful", "Payment Failed as amount is invalid", "Payment Failed from bank",
				"Payment Failed due to invalid order id", "No response from payment server",
				"Order is already paid for" };

		// Generate a random index to select a response
		Random random = new Random();
		int index = random.nextInt(statusCodes.length);
		String statusCode = statusCodes[index];
		String description = descriptions[index];

		PaymentResponseDto mockApiResponse = null;
		switch (statusCode) {
		case "200":
			mockApiResponse = new PaymentResponseDto(userId, orderId, generateTransactionId(),
					TransactionStatus.Successful.name(), description);
			break;
		case "400":
		case "504":
		case "405":
			mockApiResponse = new PaymentResponseDto(userId, orderId, generateTransactionId(),
					TransactionStatus.failed.name(), description);
			break;
		default:
			mockApiResponse = new PaymentResponseDto(userId, orderId, generateTransactionId(),
					TransactionStatus.failed.name(), description);
		}

		// Save transaction
		Transaction transaction = new Transaction();
		transaction.setTransactionId(mockApiResponse.getTransactionId());
		transaction.setStatus(mockApiResponse.getStatus().equals(TransactionStatus.failed.name())
				? TransactionStatus.failed : TransactionStatus.Successful);
		transaction = transactionRepository.save(transaction);

		// Save transaction in order
		Order order = orderRepository.findById(orderId).get();
		List<Transaction> transactions = order.getTransactions();
		transactions.add(transaction);
		order.setTransactions(transactions);
		orderRepository.save(order);

		return ResponseEntity.status(Integer.parseInt(statusCode)).body(mockApiResponse);
	}

	// Generate a mock transaction ID
	private String generateTransactionId() {
		return "tran" + (int) (Math.random() * 1000000);
	}
}
