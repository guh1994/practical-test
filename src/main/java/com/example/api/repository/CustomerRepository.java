package com.example.api.repository;

import java.util.List;


import com.example.api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();
       
        boolean existsByEmail(String email);
        
        Customer findCustomerById(Long id);
}
