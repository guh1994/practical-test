/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package com.example.api.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Gustavo Silva
 */
@Entity
public class Address
{

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "address_sequence_genarator" )
    @SequenceGenerator( name = "address_sequence_genarator", sequenceName = "ADDRESS_SEQUENCE", allocationSize = 1, initialValue = 1 )
    private Long id;

    private String street;

    private String district;

    private String city;

    private String zipcode;

    private String addressState;

    private String number;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "customer_id" )
    @JsonBackReference
    private Customer customer;

    protected Address()
    {
    }

    public Address(
        final Long id,
        final String street,
        final String district,
        final String city,
        final String zipcode,
        final String addressState,
        final String number )
    {
        this.id = id;
        this.street = street;
        this.district = district;
        this.city = city;
        this.zipcode = zipcode;
        this.addressState = addressState;
        this.number = number;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(
        final Long id )
    {
        this.id = id;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(
        final String street )
    {
        this.street = street;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(
        final String district )
    {
        this.district = district;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(
        final String city )
    {
        this.city = city;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(
        final String zipcode )
    {
        this.zipcode = zipcode;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(
        final String number )
    {
        this.number = number;
    }

    public String getAddressState()
    {
        return addressState;
    }

    public void setAddressState(
        final String addressState )
    {
        this.addressState = addressState;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(
        final Customer customer )
    {
        this.customer = customer;
    }

}
