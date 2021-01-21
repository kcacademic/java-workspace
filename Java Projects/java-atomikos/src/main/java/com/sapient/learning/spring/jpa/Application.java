package com.sapient.learning.spring.jpa;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sapient.learning.spring.jpa.inventory.Inventory;
import com.sapient.learning.spring.jpa.inventory.InventoryRepository;
import com.sapient.learning.spring.jpa.order.Order;
import com.sapient.learning.spring.jpa.order.OrderRepository;

public class Application {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Transactional(rollbackFor = Exception.class)
	public void placeOrder(String productId, int amount) throws SQLException {

		String orderId = UUID.randomUUID().toString();

		Inventory inventory = inventoryRepository.findOne(productId);
		inventory.setBalance(inventory.getBalance() - amount);
		inventoryRepository.save(inventory);

		Order order = new Order();
		order.setOrderId(orderId);
		order.setProductId(productId);
		order.setAmount(new Long(amount));
		orderRepository.save(order);
	}

}
