package com.example.api.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Customer
{

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "customer_sequence_genarator" )
    @SequenceGenerator( name = "customer_sequence_genarator", sequenceName = "CUSTOMER_SEQUENCE", allocationSize = 1, initialValue = 3 )
    private Long id;

    private String name;

    @Email
    private String email;

    @OneToMany( mappedBy = "customer", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Address> addresses;

    public Long getId()
    {
        return id;
    }

    public void setId(
        final Long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(
        final String name )
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(
        final String email )
    {
        this.email = email;
    }

    public List<Address> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(
        final List<Address> addresses )
    {
        this.addresses = addresses;
    }

    public void update(
        final Customer customer )
    {
        this.name = customer.name;
        this.email = customer.email;
        this.addresses = customer.addresses;
    }

}
