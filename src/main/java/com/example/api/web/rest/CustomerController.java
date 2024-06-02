package com.example.api.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import com.example.api.validator.RestEntityResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<RestEntityResponse<List<Customer>>> findAll() {

        RestEntityResponse<List<Customer>> response = service.findAll();

        if (!response.isSuccess()) {

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("pageable")
    public List<Customer> findAllPageable(Pageable pageable) {

        Page<Customer> findAll = service.findAll(pageable);
        return findAll.getContent();
    }

    @GetMapping("{id}")
    public ResponseEntity<RestEntityResponse<Customer>> findById(@PathVariable Long id) {

        RestEntityResponse<Customer> response = service.findById(id);

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "create")
    public ResponseEntity<RestEntityResponse<Customer>> createCustomer(@RequestBody Customer customer) {

        RestEntityResponse<Customer> response = service.createCustomer(customer);

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<RestEntityResponse<Customer>> updateCustomer(
            @PathVariable(name = "id") Long id,
            @RequestBody Customer customer) {

        RestEntityResponse<Customer> response = service.updateCustomer(id, customer);

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<RestEntityResponse<Customer>> deleteCustomer(
            @PathVariable(name = "id") final Long id) {

        RestEntityResponse<Customer> response = service.deleteCustomer(id);

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
