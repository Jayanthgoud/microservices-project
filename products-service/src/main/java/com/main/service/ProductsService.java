package com.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.main.model.Product;
import com.main.repository.ProductsDto;

@Service
public class ProductsService {

    @Autowired
    ProductsDto Dto;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    public List<Product> getProducts() {
        String key = "products:all";
        try{
        Object cachedValue = redisTemplate.opsForValue().get(key);
        if (cachedValue instanceof List<?> cachedList
                && cachedList.stream().allMatch(Product.class::isInstance)) {
            System.out.println("Returning cached products");
            List<Product> products = new ArrayList<>();
            cachedList.forEach(product -> products.add((Product) product));
            return products;
        }
    } catch (RedisConnectionFailureException exception) {
        System.out.println("Redis unavailable; reading from database");
    }

        List<Product> products = Dto.findAll();
        try{
            redisTemplate.opsForValue().set(key, products);
        } catch (RedisConnectionFailureException exception) {
            System.out.println("Redis unavailable; database result returned");
        }
        return products;
    }

    public Product getProductById(Long id) {
        String key = "product:" + id;

        try {
            Object cachedValue = redisTemplate.opsForValue().get(key);
            if (cachedValue instanceof Product cachedProduct) {
                return cachedProduct;
            }
        } catch (RedisConnectionFailureException exception) {
            System.out.println("Redis unavailable; reading from database");
        }

        Product product = Dto.findById(id).orElse(null);

        try {
            if (product != null) {
                redisTemplate.opsForValue().set(key, product);
            }
        } catch (RedisConnectionFailureException exception) {
            System.out.println("Redis unavailable; database result returned");
        }

        return product;
    }

    public Product addProduct(Product product) {
        Product savedProduct = Dto.save(product);
        redisTemplate.delete("products:all");
        return savedProduct;
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = Dto.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        Product updatedProduct = Dto.save(existingProduct);
        redisTemplate.delete("product:" + id);
        redisTemplate.delete("products:all");
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        Dto.deleteById(id);
        redisTemplate.delete("product:" + id);
        redisTemplate.delete("products:all");
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

    public void updateAvailability(Long id, Integer quantity) {
        // TODO Auto-generated method stub
        Product product = Dto.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        Integer stock = product.getStock();
        stock -= quantity;
        product.setStock(stock);
        Dto.save(product);
    }
}
