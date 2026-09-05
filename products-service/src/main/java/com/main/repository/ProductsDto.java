package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.model.Product;

@Repository
public interface ProductsDto extends JpaRepository<Product, Long> {

}
    