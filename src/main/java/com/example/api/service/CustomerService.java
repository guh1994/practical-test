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
import com.example.api.exceptions.CreateCustomerException;
import com.example.api.exceptions.DeletionException;
import com.example.api.exceptions.UpdateCustomerException;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.validator.RestEntityResponse;

@Service
public class CustomerService
{

    @Autowired
    private CustomerRepository repository;

    @Autowired
    AddressRepository addressRepository;

    public Page<Customer> findAll(
        final Pageable pageable )
    {

        return repository.findAll( pageable );
    }

    public RestEntityResponse<List<Customer>> findAll()
    {

        final List<Customer> customers = repository.findAll();

        if( customers.isEmpty() ) {

            return RestEntityResponse.<List<Customer>> builder()
                .success( false )
                .messages( Arrays.asList( "Customers not found" ) )
                .build();
        }

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

    public RestEntityResponse<Customer> createCustomer(
        final Customer customer )
    {

        try {

            final List<String> validate = validateCustomer( customer );

            if( ! validate.isEmpty() ) {
                return RestEntityResponse.<Customer> builder()
                    .success( false )
                    .messages( validate )
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

            final Customer customerCreated = repository.save( customer );

            return RestEntityResponse.<Customer> builder()
                .success( true )
                .messages( Arrays.asList( "Customer created" ) )
                .entity( customerCreated )
                .build();
        } catch( final CreateCustomerException e ) {

            System.out.println( "[CreateCustomer] Exception: " + e );

            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer can't be created because someone is wrong" ) )
                .build();
        }
    }

    public RestEntityResponse<Customer> updateCustomer(
        final Long id,
        final Customer customer )
    {

        try {

            final List<String> validate = validateCustomer( customer );
            if( ! validate.isEmpty() ) {

                return RestEntityResponse.<Customer> builder()
                    .success( false )
                    .messages( validate )
                    .entity( null )
                    .build();
            }

            final Customer customerById = repository.findCustomerById( id );
            if( customerById == null ) {

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

            customerById.update( customer );
            customer.getAddresses().forEach( address -> address.setCustomer( customerById ) );
            final Customer customerUpdated = repository.save( customerById );

            return RestEntityResponse.<Customer> builder()
                .success( true )
                .messages( Arrays.asList( "Customer updated" ) )
                .entity( customerUpdated )
                .build();

        } catch( final UpdateCustomerException uce ) {

            System.out.println( "[UpdateCustomer] [Exception]: " + uce );

            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer can't be updated because something is wrong" ) )
                .entity( null )
                .build();
        }
    }

    public RestEntityResponse<Customer> deleteCustomer(
        final Long id )
    {

        try {
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
        } catch( final DeletionException de ) {
            System.out.println( "[DeleteCustomer] [Exception]: " + de );

            return RestEntityResponse.<Customer> builder()
                .success( false )
                .messages( Arrays.asList( "Customer can't be deleted becaus something is wrong" ) )
                .build();
        }
    }

    private List<String> validateCustomer(
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
