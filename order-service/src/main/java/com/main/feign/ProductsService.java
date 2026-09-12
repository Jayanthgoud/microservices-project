package com.main.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "PRODUCTS-SERVICE", fallbackFactory = ProductsServiceFallbackFactory.class)
public interface ProductsService {

    @GetMapping("/api/v1/products/{id}/price")
    Double getPrice(@PathVariable Long id);

    @GetMapping("/api/v1/products/{id}/{quantity}/availability")
    int getAvailability(@PathVariable Long id, @PathVariable Integer quantity);

    @PutMapping("/api/v1/products/{id}/{quantity}/updateAvailability")
    void updateAvailability(@PathVariable Long id, @PathVariable Integer quantity);
}
