package br.com.upper.product.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String message) {
        super(message);
    }

}
