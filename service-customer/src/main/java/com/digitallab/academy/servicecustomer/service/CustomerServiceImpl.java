package com.digitallab.academy.servicecustomer.service;

import com.digitallab.academy.servicecustomer.entity.Customer;
import com.digitallab.academy.servicecustomer.entity.Region;
import com.digitallab.academy.servicecustomer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll(){
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        //Para la idempotencia
        //Si existe el usuario ya no lo crea de nuevo
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if(customerDB != null){
            return customerDB;
        }
        customer.setStatus("Created");
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if(customerDB == null){
            return null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setStatus("Updated");
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer customerDB = getCustomer(id);
        if(customerDB == null){
            return null;
        }
        customerDB.setStatus("Deleted");
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

}
