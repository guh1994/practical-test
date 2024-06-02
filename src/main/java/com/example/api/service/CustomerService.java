package com.example.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.exceptions.CreateCustomerException;
import com.example.api.exceptions.DeletionException;
import com.example.api.exceptions.UpdateCustomerException;
import com.example.api.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@Service
public class CustomerService {

    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Page<Customer> findAll(Pageable pageable) {

        return repository.findAll(pageable);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<String> createCustomer(Customer customer) {

        try {

            List<String> validate = validateCustomer(customer);

            if (!validate.isEmpty()) {
                return new ResponseEntity<>("Customer is not created because is not valid "
                        + validate.toString(), HttpStatus.BAD_REQUEST);
            }

            boolean existsByEmail = repository.existsByEmail(customer.getEmail());
            if (existsByEmail) {
                return new ResponseEntity<>("Customer already exists", HttpStatus.OK);
            }

            repository.save(customer);

            return new ResponseEntity<>("Customer created", HttpStatus.CREATED);
        } catch (CreateCustomerException e) {

            System.out.println("[CreateCustomer] Exception: " + e);

            return new ResponseEntity<>("Customer can't be created because someone is wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateCustomer(Long id, Customer customer) {

        try {

            List<String> validate = validateCustomer(customer);
            if (!validate.isEmpty()) {
                return new ResponseEntity<>("Customer is not updated because is not valid "
                        + validate.toString(), HttpStatus.BAD_REQUEST);
            }
            Customer customerById = repository.findCustomerById(id);
            if (customerById == null) {
                return new ResponseEntity<>("Customer not found", HttpStatus.BAD_REQUEST);

            }
            Customer customerUpdated = repository.save(customer);

            return new ResponseEntity<>("Customer updated: " + customerUpdated.toString(), HttpStatus.CREATED);

        } catch (UpdateCustomerException uce) {
            System.out.println("[UpdateCustomer] [Exception]: " + uce);
            return new ResponseEntity<>("Customer can't be updated because something is wrong", HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<String> deleteCustomer(Long id) {

        try {
            if (id == null) {
                return new ResponseEntity<>("Customer deletion id is null", HttpStatus.BAD_REQUEST);
            }

            Optional<Customer> customer = repository.findById(id);
            if (!customer.isPresent()) {

                return new ResponseEntity<>("Customer is not existis so that can't be no deleted",
                        HttpStatus.BAD_REQUEST);
            }
            repository.deleteById(id);

            return new ResponseEntity<>("Customer deleted with success", HttpStatus.OK);
        } catch (DeletionException de) {
            System.out.println("[DeleteCustomer] [Exception]: " + de);

            return new ResponseEntity<>("Customer can't be deleted becaus something is wrong", HttpStatus.BAD_REQUEST);
        }
    }

    private List<String> validateCustomer(Customer customer) {

        List<String> messages = new ArrayList<>();

        if (StringUtils.isEmpty(customer)) {
            messages.add("Customer is empty");
        }

        if (StringUtils.isEmpty(customer.getName())) {
            messages.add("Customer name is empty");
        }

        if (StringUtils.isEmpty(customer.getEmail())) {
            messages.add("Customer email is empty");
        }
        return messages;
    }
}
