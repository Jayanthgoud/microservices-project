package com.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.main.model.Product;
import com.main.repository.ProductsDto;
import com.main.service.ProductsService;

@ExtendWith(MockitoExtension.class)
class ProductsServiceApplicationTests {

    @Mock
    private ProductsDto productsDto;

    @InjectMocks
    private ProductsService productsService;

    @Test
    void shouldReturnAllProducts() {
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(1200.0);
        product.setStock(10);

        when(productsDto.findAll()).thenReturn(List.of(product));

        List<Product> result = productsService.getProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    void shouldReturnProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Mouse");
        product.setDescription("Wireless mouse");
        product.setPrice(40.0);
        product.setStock(25);

        when(productsDto.findById(1L)).thenReturn(Optional.of(product));

        Product result = productsService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Mouse", result.getName());
    }

    @Test
    void shouldSaveProduct() {
        Product product = new Product();
        product.setName("Keyboard");
        product.setDescription("Mechanical keyboard");
        product.setPrice(95.0);
        product.setStock(18);

        when(productsDto.save(product)).thenReturn(product);

        Product result = productsService.addProduct(product);

        assertNotNull(result);
        assertEquals("Keyboard", result.getName());
        verify(productsDto).save(product);
    }
}
