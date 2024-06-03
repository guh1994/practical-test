/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package com.example.api.exceptions;

/**
 * @author Gustavo Silva
 */
public class DeletionException
    extends
        RuntimeException
{

    private static final long serialVersionUID = 7051517686290807398L;

    public DeletionException(
        final String message )
    {
        super( message );
    }
}
