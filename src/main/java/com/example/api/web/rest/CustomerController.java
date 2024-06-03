package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import com.example.api.validator.RestEntityResponse;

@RestController
@RequestMapping( "/customers" )
public class CustomerController
{

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<RestEntityResponse<List<Customer>>> findAll()
    {

        final RestEntityResponse<List<Customer>> response = service.findAll();

        if( ! response.isSuccess() ) {

            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );

        }

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @GetMapping( "pageable" )
    public List<Customer> findAllPageable(
        final Pageable pageable )
    {

        final Page<Customer> findAll = service.findAll( pageable );
        return findAll.getContent();
    }

    @GetMapping( "{id}" )
    public ResponseEntity<RestEntityResponse<Customer>> findById(
        @PathVariable final Long id )
    {

        final RestEntityResponse<Customer> response = service.findById( id );

        if( ! response.isSuccess() ) {
            return new ResponseEntity<>( response, HttpStatus.NOT_FOUND );

        }
        return new ResponseEntity<>( response, HttpStatus.OK );

    }

    @PostMapping
    public ResponseEntity<RestEntityResponse<Customer>> createCustomer(
        @RequestBody final Customer customer )
    {

        final RestEntityResponse<Customer> response = service.createCustomer( customer );

        if( ! response.isSuccess() ) {
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
        }

        return new ResponseEntity<>( response, HttpStatus.CREATED );

    }

    @PutMapping
    public ResponseEntity<RestEntityResponse<Customer>> updateCustomer(
        @RequestBody final Customer customer )
    {

        final RestEntityResponse<Customer> response = service.updateCustomer( customer );

        if( ! response.isSuccess() ) {
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );
        }
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @DeleteMapping( value = "{id}" )
    public ResponseEntity<RestEntityResponse<Customer>> deleteCustomer(
        @PathVariable( name = "id" ) final Long id )
    {

        final RestEntityResponse<Customer> response = service.deleteCustomer( id );

        if( ! response.isSuccess() ) {
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST );

        }

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

}
