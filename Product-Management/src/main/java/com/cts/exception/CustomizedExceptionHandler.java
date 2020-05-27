package com.cts.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> product = new ArrayList<>();
        product.add(ex.getLocalizedMessage());
        Message error = new Message("Product Not Found", product);
        return new ResponseEntity<>(error,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ProductNotFoundException ex, WebRequest request) {
        List<String> product = new ArrayList<>();
        product.add(ex.getLocalizedMessage());
        Message error = new Message("Product Not Found", product);
        return new ResponseEntity<>(error,new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> product = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
        	product.add(error.getDefaultMessage());
        }
        Message error = new Message("Validation Failed", product);
        return new ResponseEntity<>(error,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}


