/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import com.example.api.validator.RestEntityResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Gustavo Silva
 */
public class CustomerServiceImplTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule().strictness(Strictness.LENIENT);

    public static final String NAME = "Gustavo Silva";
    public static final String EMAIL = "gustavosilva@gmail.com";
    public static final Long ID = 1L;
    public static final Long ADDRESS_ID = 1L;
    public static final String STREET = "Rua da Joana";
    public static final String DISTRICT = "Jardim Botuquara";
    public static final String CITY = "São Joaquim";
    public static final String ZIPCODE = "01234-567";
    public static final String ADDRESS_STATE = "SP";
    public static final String NUMBER = "500";

    @InjectMocks
    private CustomerService subject;

    @Mock
    private CustomerRepository repository;

    @Mock
    private Customer customer;

    @Mock
    private Customer updatedCustomer;

    @Mock
    private Pageable pageable;

    @Before
    public void setup() {
        Mockito.when(customer.getName()).thenReturn(NAME);
        Mockito.when(customer.getEmail()).thenReturn(EMAIL);
        Mockito.when(customer.getId()).thenReturn(ID);
        Mockito.when(repository.findCustomerById(ID)).thenReturn(customer);
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(customer));
        Mockito.when(repository.save(any())).thenReturn(customer);
        Mockito.when(repository.saveAndFlush(any())).thenReturn(customer);
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(customer));
        Mockito.when(customer.getAddresses()).thenReturn(Arrays.asList(new Address(ADDRESS_ID, STREET, DISTRICT, CITY, ZIPCODE, ADDRESS_STATE, NUMBER)));
    }

    @Test
    public void shouldGetCustomers() {

        RestEntityResponse<List<Customer>> response = subject.findAll();

        Assert.assertEquals(1, response.getEntity().size());

        Customer customerTest = response.getEntity().get(0);
        Assert.assertEquals(ID, customerTest.getId());
        Assert.assertEquals(NAME, customerTest.getName());
        Assert.assertEquals(EMAIL, customerTest.getEmail());

    }

    @Test
    public void shouldGetNullCustomer() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        RestEntityResponse<List<Customer>> response = subject.findAll();

        Assert.assertNull(response.getEntity());
    }

    @Test
    public void shouldGetCustomerById() {

        Mockito.when(repository.existsById(ID)).thenReturn(true);

        RestEntityResponse<Customer> response = subject.findById(customer.getId());

        Assert.assertEquals(ID, response.getEntity().getId());
        Assert.assertEquals(NAME, response.getEntity().getName());
        Assert.assertEquals(EMAIL, response.getEntity().getEmail());

    }

    @Test
    public void shouldGetErrorMessageWhenCustomerNotFound() {

        RestEntityResponse<Customer> response = subject.findById(52L);

        Assert.assertNull(response.getEntity());
        Assert.assertEquals(Arrays.asList("Customer not found"), response.getMessages());
    }

    @Test
    public void shouldCreateCustomer() {
        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(RestEntityResponse.<Customer>builder()
                .success(true)
                .messages(Arrays.asList("Customer created"))
                .entity(customer)
                .build(), response);

    }

    @Test
    public void shouldValidateIfExistCustomerBeforeCreateCustomer() {
        Mockito.when(repository.existsByEmail(EMAIL)).thenReturn(true);

        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(customer.getName());
        customerTest.setEmail(customer.getEmail());

        RestEntityResponse<Customer> response = subject.createCustomer(customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer Already exists"), response.getMessages());

    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerNameIsNullWhenCreateCustomer() {
        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(null);
        customerTest.setEmail(customer.getEmail());

        RestEntityResponse<Customer> response = subject.createCustomer(customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer name is empty"), response.getMessages());

    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerEmailIsNullWhenCreateCustomer() {
        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(customer.getName());
        customerTest.setEmail(null);

        RestEntityResponse<Customer> response = subject.createCustomer(customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer email is empty"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerIsNullWhenCreateCustomer() {
        Customer customerTest = new Customer();
        customerTest.setId(null);
        customerTest.setName(null);
        customerTest.setEmail(null);

        RestEntityResponse<Customer> response = subject.createCustomer(customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer is null"), response.getMessages());
    }

    @Test
    public void shouldUpdateCustomer() {

        Customer updatedCustomerTest = new Customer();
        updatedCustomerTest.setId(1L);
        updatedCustomerTest.setName("Landro Silva");
        updatedCustomerTest.setEmail("leandrosilva@gmail.com");
        updatedCustomerTest.setAddresses(Arrays.asList(new Address(ADDRESS_ID, STREET, DISTRICT, CITY, ZIPCODE, ADDRESS_STATE, NUMBER)));

        Mockito.when(repository.save(customer)).thenReturn(updatedCustomer);
        Mockito.when(updatedCustomer.getId()).thenReturn(updatedCustomerTest.getId());
        Mockito.when(updatedCustomer.getName()).thenReturn(updatedCustomerTest.getName());
        Mockito.when(updatedCustomer.getEmail()).thenReturn(updatedCustomerTest.getEmail());
        Mockito.when(updatedCustomer.getAddresses()).thenReturn(updatedCustomerTest.getAddresses());
        Mockito.when(repository.existsById(ID)).thenReturn(true);

        RestEntityResponse<Customer> response = subject.updateCustomer(updatedCustomer.getId(), updatedCustomerTest);

        Mockito.verify(customer).update(updatedCustomerTest);
        Mockito.verify(repository).save(customer);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer updated"), response.getMessages());

    }

    @Test
    public void shouldValidateIfExistsCustomerBeforeUpdateCustomer() {
        Mockito.when(repository.existsById(ID)).thenReturn(true);

        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(customer.getName());
        customerTest.setEmail(customer.getEmail());
        customerTest.setAddresses(customer.getAddresses());

        RestEntityResponse<Customer> response
                = subject.updateCustomer(customer.getId(), customerTest);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer updated"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerNameIsNullWhenUpdateCustomer() {
        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(null);
        customerTest.setEmail(customer.getEmail());

        RestEntityResponse<Customer> response
                = subject.updateCustomer(customerTest.getId(), customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer name is empty"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerEmailIsNullWhenUpdateCustomer() {
        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(customer.getName());
        customerTest.setEmail(null);

        RestEntityResponse<Customer> response
                = subject.updateCustomer(customerTest.getId(), customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer email is empty"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerIsNullWhenUpdateCustomer() {
        Customer customerTest = new Customer();
        customerTest.setId(null);
        customerTest.setName(null);
        customerTest.setEmail(null);

        RestEntityResponse<Customer> response
                = subject.updateCustomer(customerTest.getId(), customerTest);

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer is null"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerAddressIsNullWhenUpdateCustomer() {
//        Customer customerTest = new Customer();
//        customerTest.setId(customer.getId());
//        customerTest.setName(customer.getName());
//        customerTest.setEmail(customer.getEmail());
//        customerTest.setAddresses(null);
//        
//        RestEntityResponse<Customer> response
//                = subject.updateCustomer(customerTest.getId(), customerTest);
//        
//        Assert.assertFalse(response.isSuccess());
//        Assert.assertEquals(Arrays.asList("Address is Null"), response.getMessages());
    }

    @Test
    public void shouldDeleteCustomer() {

        Customer customerTest = new Customer();
        customerTest.setId(customer.getId());
        customerTest.setName(customer.getName());
        customerTest.setEmail(customer.getEmail());

        RestEntityResponse<Customer> response = subject.deleteCustomer(ID);

        Assert.assertNull(response.getEntity());
        Assert.assertEquals(Arrays.asList("Customer deleted with success"), response.getMessages());
        Mockito.verify(repository).deleteById(ID);
    }

    @Test
    public void shouldErrorMessageIfCustomerNotExists() {
        Customer customerTest = new Customer();
        customerTest.setId(52L);

        RestEntityResponse<Customer> response = subject.deleteCustomer(customerTest.getId());

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer is not existis so that can't be no deleted"), response.getMessages());
    }

    @Test
    public void shouldErrorMessageIfCustomerIdIsNull() {

        Customer customerTest = new Customer();
        customerTest.setId(null);

        RestEntityResponse<Customer> response = subject.deleteCustomer(customerTest.getId());

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(Arrays.asList("Customer deletion id is null"), response.getMessages());
    }

}
