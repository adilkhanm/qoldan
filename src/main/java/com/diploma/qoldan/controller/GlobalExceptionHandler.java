package com.diploma.qoldan.controller;

import com.diploma.qoldan.exception.category.CategoryExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.image.ImageExistsException;
import com.diploma.qoldan.exception.product.*;
import com.diploma.qoldan.exception.user.UsernameExistsException;
import com.diploma.qoldan.exception.wishlist.WishlistExistsException;
import com.diploma.qoldan.exception.wishlist.WishlistNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            TagNotFoundException.class,
            ProductTypeNotFoundException.class,
            CategoryNotFoundException.class,
            ProductNotFoundException.class,
            WishlistNotFoundException.class})
    protected ResponseEntity<?> handleNotFoundException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {
            TagExistsException.class,
            ProductTypeExistsException.class,
            CategoryExistsException.class,
            ImageExistsException.class,
            WishlistExistsException.class,
            UsernameExistsException.class })
    protected ResponseEntity<?> handleExistsException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {
            ProductIsNotActiveException.class })
    protected ResponseEntity<?> handleConflictExceptions(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {
            AuthenticationException.class })
    protected ResponseEntity<?> handleAuthenticationException(Exception exception, WebRequest request) {
        return handleExceptionInternal(
                exception,
                "Invalid credentials: username or password\n" + exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request);
    }
}
