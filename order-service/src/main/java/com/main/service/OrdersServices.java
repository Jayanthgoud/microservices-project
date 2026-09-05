package com.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.feign.ProductsService;
import com.main.feign.UserService;
import com.main.model.OrderItemModel;
import com.main.model.OrderModel;
import com.main.repository.OrderDto;

@Service
public class OrdersServices {

	@Autowired
	OrderDto orderDto;

	@Autowired
	ProductsService productsService;

	@Autowired
	UserService userService;

	public List<OrderModel> getAllOrders() {
		// TODO Auto-generated method stub
		return orderDto.findAll();
	}

	public OrderModel getOrderById(Long id) {
		// TODO Auto-generated method stub
		return orderDto.findById(id).orElse(null);
	}

	public OrderModel createOrder(OrderModel order) {
		Integer isAvailable;
		Double price;
		Double totalAmount = 0.0;
		Integer userExists = userService.isUserExists(order.getUserId());
		if(userExists == 0) {
			throw new RuntimeException("User ID is required to create an order");
		}
		List<OrderItemModel> items = order.getItems();
		if (items == null || items.isEmpty()) {
			throw new RuntimeException("Order must contain at least one item");
		}
		for(OrderItemModel item : items) {
			isAvailable = productsService.getAvailability(item.getProductId(), item.getQuantity());
			price = productsService.getPrice(item.getProductId());
			item.setId(null);
			item.setPrice(price);
			totalAmount += price * item.getQuantity();
			if(isAvailable == 0) {
				throw new RuntimeException("Product with id: " + item.getProductId() + " is not available in the requested quantity: " + item.getQuantity());
			}
			item.setOrder(order);
		}
		order.setItems(items);
		order.setTotalAmount(totalAmount);
		return orderDto.save(order);
	}

	public OrderModel updateOrder(Long id, String status) {
		OrderModel existingOrder = orderDto.findById(id).orElse(null);
		existingOrder.setStatus(status);
		
		// TODO Auto-generated method stub
		return orderDto.save(existingOrder);
	}

	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub
		OrderModel existingOrder = orderDto.findById(id).orElse(null);
		existingOrder.setStatus("CANCELLED");
		orderDto.save(existingOrder);
	}

	public OrderModel deleteOrderItem(Long id, Long item_id) {
		// TODO Auto-generated method stub
		OrderModel existingOrder = orderDto.findById(id).orElse(null);
		List<OrderItemModel> items = existingOrder.getItems();
		Double totalAmount = existingOrder.getTotalAmount();
		OrderModel orderCancel = new OrderModel();
		List<OrderItemModel> cancelItems = new ArrayList<>();
		for(OrderItemModel item : items) {
			if(item.getId().equals(item_id)) {
				totalAmount -= item.getPrice() * item.getQuantity();
				items.remove(item);
				cancelItems.add(item);
				item.setOrder(orderCancel);
				orderCancel.setItems(cancelItems);
				orderCancel.setTotalAmount(item.getPrice() * item.getQuantity());
				orderCancel.setStatus("CANCELLED");
				orderCancel.setUserId(existingOrder.getUserId());
				orderDto.save(orderCancel);
				break;
			}
		}
		existingOrder.setTotalAmount(totalAmount);
		return orderDto.save(existingOrder);
	}

}
