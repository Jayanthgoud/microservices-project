package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.model.OrderItemModel;

@Repository
public interface OrderItemDto extends JpaRepository<OrderItemModel, Long> {

}
