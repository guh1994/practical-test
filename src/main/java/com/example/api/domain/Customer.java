package com.example.api.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence_genarator")
    @SequenceGenerator(name = "customer_sequence_genarator", sequenceName = "CUSTOMER_SEQUENCE", allocationSize = 1, initialValue = 3)
    private Long id;

    private String name;

    @Email
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,targetEntity = Address.class)
    private List<Address> addresses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void update(
            final Customer customer) {
        this.name = customer.name;
        this.email = customer.email;
    }

}
