package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.Customer;

public interface CustomerRepository
    extends
        JpaRepository<Customer,Long>
{

    List<Customer> findAllByOrderByNameAsc();

    boolean existsByEmail(
        String email );

    Customer findCustomerById(
        Long id );
    
    Customer findByEmail(
        String email );
}
