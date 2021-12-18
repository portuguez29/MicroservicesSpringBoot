package com.digitallab.academy.servicecustomer.repository;

import com.digitallab.academy.servicecustomer.entity.Customer;
import com.digitallab.academy.servicecustomer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String LastName);
    public List<Customer> findByRegion(Region region);
}
