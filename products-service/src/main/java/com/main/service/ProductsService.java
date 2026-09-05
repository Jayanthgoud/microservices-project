package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.model.Product;
import com.main.repository.ProductsDto;

@Service
public class ProductsService {

    @Autowired
    ProductsDto Dto;

    public List<Product> getProducts() {
        return Dto.findAll();
    }

    public Product getProductById(Long id) {
        return Dto.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return Dto.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = Dto.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        return Dto.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Dto.deleteById(id);
    }

    public Double getPrice(Long id) {
        // TODO Auto-generated method stub

        Product product = Dto.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return product.getPrice();
    }

    public int getAvailability(Long id, Integer quantity) {
        // TODO Auto-generated method stub

        Product product = Dto.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        Integer stock = product.getStock();
        if (stock - quantity > 0) {
            return 1; // Product is available
        } else {
            return 0; // Product is not available
        }
    }
}
