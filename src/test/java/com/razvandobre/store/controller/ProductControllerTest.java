package com.razvandobre.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razvandobre.store.model.Product;
import com.razvandobre.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> productList = List.of(new Product(1L, "Laptop", 5000.0, 10));

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetProductById_Found() throws Exception {
        Product product = new Product(1L, "Laptop", 5000.0, 10);

        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(999L)).thenReturn(Optional.empty());

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product not found"));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product(1L, "Laptop", 5000.0, 10);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
