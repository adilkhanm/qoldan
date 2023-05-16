package com.diploma.qoldan.controller;

import com.diploma.qoldan.exception.category.CategoryExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.image.ImageExistsException;
import com.diploma.qoldan.exception.product.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            TagNotFoundException.class,
            ProductTypeNotFoundException.class,
            CategoryNotFoundException.class,
            ProductNotFoundException.class })
    protected ResponseEntity<?> handleNotFoundException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {
            TagExistsException.class,
            ProductTypeExistsException.class,
            CategoryExistsException.class,
            ImageExistsException.class })
    protected ResponseEntity<?> handleExistsException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {
            ProductIsNotActiveException.class })
    protected ResponseEntity<?> handleConflictExceptions(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
