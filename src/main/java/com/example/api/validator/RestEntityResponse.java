/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package com.example.api.validator;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gustavo Silva
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestEntityResponse<T>
{
    private boolean success;
    private List<String> messages;
    private T entity;

}
