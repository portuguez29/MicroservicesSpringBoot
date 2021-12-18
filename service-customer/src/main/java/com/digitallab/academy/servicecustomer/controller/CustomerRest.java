package com.digitallab.academy.servicecustomer.controller;


import com.digitallab.academy.servicecustomer.entity.Customer;
import com.digitallab.academy.servicecustomer.entity.Region;
import com.digitallab.academy.servicecustomer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/Customer")
public class CustomerRest {
    @Autowired
    CustomerService customerService;

    @GetMapping("/getAllCustomerByRegion")
    public ResponseEntity<List<Customer>> ListAllCustomerbyRegion(@RequestParam(required = false) Long regionID){
        List<Customer> customers = new ArrayList<>();
        if(regionID == null || String.valueOf(regionID).isEmpty()){
            customers = customerService.findCustomerAll();
            if(customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            Region region = new Region();
            region.setId(regionID);
            customers= customerService.findCustomerByRegion(region);
            if(customers == null){
                log.error("Customer with Region ID {} not found.", regionID);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getAllcustomer")
    public ResponseEntity<Customer> listAllCustomer(){
        return new ResponseEntity(customerService.findCustomerAll(), HttpStatus.OK);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity getCustomer(@PathVariable Long id){
        log.info("fetching customer with id {}",id);
        Customer customer = customerService.getCustomer(id);
        if(customer == null){
            log.error("Customer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating customer: {}", customer);
        if(result.hasErrors()){
            ErrorMessage errorMessage = new ErrorMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,errorMessage.formatMessage(result));
        }
        //Customer customerDB= customerService.createCustomer(customer);
        //return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
        return new ResponseEntity(customerService.createCustomer(customer),HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity updateCustomer(@RequestBody Customer customer){
        log.info("Updating customer {}", customer);
        return new ResponseEntity(customerService.updateCustomer(customer),HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id){
        return new ResponseEntity(customerService.deleteCustomer(id),HttpStatus.OK);
    }




}
