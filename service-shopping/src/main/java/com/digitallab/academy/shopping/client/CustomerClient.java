package com.digitallab.academy.shopping.client;

import com.digitallab.academy.shopping.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-customer"/*, url = "http://localhost:8092/Customer"*/, fallback =  CustomerHystrixFallbackFactory.class)
//@RequestMapping("/Customer")
public interface CustomerClient {
    @GetMapping("/Customer/getCustomer/{id}")
    ResponseEntity<Customer> getCustomer(@PathVariable Long id);

}
