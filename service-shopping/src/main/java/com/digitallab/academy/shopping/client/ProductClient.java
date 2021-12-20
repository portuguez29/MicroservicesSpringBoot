package com.digitallab.academy.shopping.client;

import com.digitallab.academy.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-product"/*, url = "http://localhost:8091/products"*/)
//@RequestMapping("/products")
public interface ProductClient {
    @GetMapping("/products/getProduct/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id);

    @PostMapping("/products/updateStock/{id}")
    public ResponseEntity<Product> updateStock(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Double quantity);

}
