package com.main.controller;

import com.main.model.OrderModel;
import com.main.service.OrdersServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {
	@Autowired
	OrdersServices service;
	
    @GetMapping
    public List<OrderModel> getAllOrders() {
    	return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderModel getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    @PostMapping
    public OrderModel createOrder(@RequestBody OrderModel order) {
        return service.createOrder(order);
    }

    @PutMapping("/{id}")
    public OrderModel updateOrder(
            @PathVariable Long id,
            @RequestParam String status) {
        return service.updateOrder(id, status);
    }
    @PutMapping("/{id}/{item_id}")
    public OrderModel deleteOrderItem(
            @PathVariable Long id,
            @PathVariable Long item_id) {
        return service.deleteOrderItem(id, item_id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
    	service.deleteOrder(id);
    }
}