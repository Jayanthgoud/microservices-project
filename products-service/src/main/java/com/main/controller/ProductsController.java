package com.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.model.Product;
import com.main.service.ProductsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    ProductsService productsService;
    @GetMapping("/")
    public List<Product> getProducts() {

        return productsService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productsService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productsService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productsService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
    }

    @GetMapping("/{id}/price")
    public Double getPrice(@PathVariable Long id) {
        return productsService.getPrice(id);
    }

    @GetMapping("/{id}/{quantity}/availability")
    public int getAvailability(@PathVariable Long id, @PathVariable Integer quantity) {
        return productsService.getAvailability(id, quantity);
    }
    
    

}
