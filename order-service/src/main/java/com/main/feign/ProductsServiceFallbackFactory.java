package com.main.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductsServiceFallbackFactory implements FallbackFactory<ProductsService> {

    @Override
    public ProductsService create(Throwable cause) {
        return new ProductsService() {
            @Override
            public Double getPrice(Long id) {
                throw new IllegalStateException("Products service is unavailable", cause);
            }

            @Override
            public int getAvailability(Long id, Integer quantity) {
                return 0;
            }
        };
    }
}
