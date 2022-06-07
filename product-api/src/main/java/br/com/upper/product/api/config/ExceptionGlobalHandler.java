package br.com.upper.product.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException validationException) {
        var datails = new ExceptionDetails();
        datails.setStatus(HttpStatus.BAD_REQUEST.value());
        datails.setMessage(validationException.getMessage());
        return new ResponseEntity<>(datails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException uthorizationException) {
        var datails = new ExceptionDetails();
        datails.setStatus(HttpStatus.UNAUTHORIZED.value());
        datails.setMessage(uthorizationException.getMessage());
        return new ResponseEntity<>(datails, HttpStatus.UNAUTHORIZED);
    }

}
