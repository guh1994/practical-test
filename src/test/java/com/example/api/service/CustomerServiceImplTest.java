/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
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

    @InjectMocks
    private CustomerService subject;

    @Mock
    private CustomerRepository repository;

    @Mock
    private Customer customer;

    @Mock
    private Pageable pageable;

    @Before
    public void setup() {
        Mockito.when(customer.getName()).thenReturn(NAME);
        Mockito.when(customer.getEmail()).thenReturn(EMAIL);
        Mockito.when(repository.findCustomerById(ID)).thenReturn(customer);
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(customer));
        Mockito.when(repository.save(any())).thenReturn(customer);
    }
}
