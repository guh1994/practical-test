package com.example.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import com.example.api.validator.RestEntityResponse;

@Service
public class CustomerService
{

    @Autowired
    private CustomerRepository repository;

    public Page<Customer> findAll(
        final Pageable pageable )
    {
        return repository.findAll( pageable );
    }

    public RestEntityResponse<List<Customer>> findAll()
    {
        final List<Customer> customers = repository.findAll();
        return RestEntityResponse.<List<Customer>> builder()
            .success( true )
            .entity( customers )
            .build();
    }

    public RestEntityResponse<Customer> findById(
        final Long id )
    {
        if( id == null ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Id is null" ) )
                .build();
        }

        final boolean existsById = repository.existsById( id );
        if( ! existsById ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer not found" ) )
                .build();
        }

        return RestEntityResponse.<Customer> builder()
            .success( true )
            .entity( repository.findCustomerById( id ) )
            .build();
    }

    public RestEntityResponse<Customer> create(
        final Customer customer )
    {

        final List<String> validateMessages = validate( customer );

        if( ! validateMessages.isEmpty() ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( validateMessages )
                .build();
        }

        final boolean existsByEmail = repository.existsByEmail( customer.getEmail() );
        if( existsByEmail ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer Already exists" ) )
                .build();
        }

        if( customer.getAddresses() != null ) {
            customer.getAddresses().forEach( (
                final Address address ) -> {
                address.setCustomer( customer );
            } );
        }

        final Customer persistentCustomer = repository.save( customer );

        return RestEntityResponse.<Customer> builder()
            .success( true )
            .messages( Arrays.asList( "Customer created" ) )
            .entity( persistentCustomer )
            .build();

    }

    public RestEntityResponse<Customer> update(
        final Customer customer )
    {
        if( customer.getId() == null ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer id is null" ) )
                .entity( null )
                .build();
        }

        final List<String> validateMessages = validate( customer );
       
        if( ! validateMessages.isEmpty() ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( validateMessages )
                .entity( null )
                .build();
        }

        final Customer customerByEmail = repository.findByEmail( customer.getEmail() );
        if( customerByEmail != null && customerByEmail.getId() != customer.getId() ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer Already exists with provided email" ) )
                .build();
        }

        final Customer persistentCustomer = repository.findCustomerById( customer.getId() );
        if( persistentCustomer == null ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer not found" ) )
                .entity( null )
                .build();
        }

        if( customer.getAddresses() == null ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Address is Null" ) )
                .build();
        }

        persistentCustomer.update( customer );
        customer.getAddresses().forEach( address -> address.setCustomer( persistentCustomer ) );
        final Customer customerUpdated = repository.save( persistentCustomer );

        return RestEntityResponse.<Customer> builder()
            .success( true )
            .messages( Arrays.asList( "Customer updated" ) )
            .entity( customerUpdated )
            .build();

    }

    public RestEntityResponse<Customer> deleteCustomer(
        final Long id )
    {
        if( id == null ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer deletion id is null" ) )
                .build();
        }

        final Optional<Customer> customer = repository.findById( id );
        if( ! customer.isPresent() ) {
            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer is not existis so that can't be no deleted" ) )
                .build();

        }

        repository.deleteById( id );

        return RestEntityResponse.<Customer> builder()
            .success( true )
            .messages( Arrays.asList( "Customer deleted with success" ) )
            .build();
    }

    private List<String> validate(
        final Customer customer )
    {
        final List<String> messages = new ArrayList<>();

        if( customer.getName() == null && customer.getEmail() == null ) {
            messages.add( "Customer is null" );
            return messages;
        }

        if( StringUtils.isEmpty( customer.getName() ) ) {
            messages.add( "Customer name is empty" );
        }

        if( StringUtils.isEmpty( customer.getEmail() ) ) {
            messages.add( "Customer email is empty" );
        }
        return messages;
    }

}
