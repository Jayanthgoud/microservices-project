package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.model.OrderModel;

@Repository
public interface OrderDto extends JpaRepository<OrderModel, Long> {

}
