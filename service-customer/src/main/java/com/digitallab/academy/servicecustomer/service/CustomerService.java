package com.digitallab.academy.servicecustomer.service;

import com.digitallab.academy.servicecustomer.entity.Customer;
import com.digitallab.academy.servicecustomer.entity.Region;

import java.util.List;

public interface CustomerService {
    List<Customer> findCustomerAll();
    List<Customer> findCustomerByRegion(Region region);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer deleteCustomer(Long id);
    Customer getCustomer(Long id);
}
