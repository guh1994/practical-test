/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.api.repository;

import com.example.api.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Gustavo Silva
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

}
