/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.api.exceptions;

/**
 *
 * @author Gustavo Silva
 */
public class UpdateCustomerException extends RuntimeException {

    public UpdateCustomerException(String message) {
        super(message);
    }
}
