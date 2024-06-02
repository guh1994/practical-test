package com.example.api.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> findAll(Pageable pageable) {

        Page<Customer> findAll = service.findAll(pageable);
        return findAll.getContent();
    }

    @GetMapping("{id}")
    public Customer findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @PostMapping(value = "create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {

        ResponseEntity<String> createdCustomer = service.createCustomer(customer);

        System.out.println(createdCustomer);

        return createdCustomer;

    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<String> updateCustomer(
            @PathVariable(name = "id") Long id,
            @RequestBody Customer customer) {

        ResponseEntity<String> updatedCustomer = service.updateCustomer(id, customer);

        System.out.println(updatedCustomer);

        return updatedCustomer;
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") final Long id) {

        ResponseEntity<String> responseEntity = service.deleteCustomer(id);

        return responseEntity;
    }

}
