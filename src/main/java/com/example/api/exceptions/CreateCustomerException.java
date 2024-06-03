/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package com.example.api.exceptions;

/**
 * @author Gustavo Silva
 */
public class CreateCustomerException
    extends
        RuntimeException
{

    private static final long serialVersionUID = - 508100728271552344L;

    public CreateCustomerException(
        final String message )
    {
        super( message );
    }
}
