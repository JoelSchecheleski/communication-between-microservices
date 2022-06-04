package br.com.upper.product.api.config;

import lombok.Data;

@Data
public class ExceptionDetails {

    private int status;
    private String message;
    
}
