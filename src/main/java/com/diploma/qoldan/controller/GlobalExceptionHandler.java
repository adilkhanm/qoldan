package com.diploma.qoldan.controller;

import com.diploma.qoldan.exception.cart.CartIsEmptyException;
import com.diploma.qoldan.exception.cart.CartNotFoundException;
import com.diploma.qoldan.exception.cart.CartProductExistsException;
import com.diploma.qoldan.exception.cart.CartProductNotFoundException;
import com.diploma.qoldan.exception.category.CategoryExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.donation.AnnouncementIsNotActiveException;
import com.diploma.qoldan.exception.donation.DonationAnnouncementNotFoundException;
import com.diploma.qoldan.exception.donation.DonationNotFoundException;
import com.diploma.qoldan.exception.donation.DonationStatusNotFound;
import com.diploma.qoldan.exception.image.ImageExistsException;
import com.diploma.qoldan.exception.image.ImageNotFoundException;
import com.diploma.qoldan.exception.order.OrderAlreadyConfirmedException;
import com.diploma.qoldan.exception.order.OrderExistsException;
import com.diploma.qoldan.exception.order.OrderRowNotFoundException;
import com.diploma.qoldan.exception.order.OrderStatusNotFoundException;
import com.diploma.qoldan.exception.organization.OrganizationNotFoundException;
import com.diploma.qoldan.exception.product.*;
import com.diploma.qoldan.exception.user.UserAddressNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.exception.user.UsernameExistsException;
import com.diploma.qoldan.exception.wishlist.WishlistExistsException;
import com.diploma.qoldan.exception.wishlist.WishlistNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            TagNotFoundException.class,
            ProductTypeNotFoundException.class,
            CategoryNotFoundException.class,
            ProductNotFoundException.class,
            WishlistNotFoundException.class,
            CartProductNotFoundException.class,
            OrderStatusNotFoundException.class,
            OrderRowNotFoundException.class,
            ImageNotFoundException.class,
            OrganizationNotFoundException.class,
            DonationAnnouncementNotFoundException.class,
            DonationNotFoundException.class,
            DonationStatusNotFound.class })
    protected ResponseEntity<?> handleNotFoundException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {
            TagExistsException.class,
            ProductTypeExistsException.class,
            CategoryExistsException.class,
            ImageExistsException.class,
            WishlistExistsException.class,
            UsernameExistsException.class,
            CartProductExistsException.class,
            OrderExistsException.class })
    protected ResponseEntity<?> handleExistsException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {
            ProductIsNotActiveException.class,
            CartIsEmptyException.class,
            ProductIsNotAvailableException.class,
            OrderAlreadyConfirmedException.class,
            AnnouncementIsNotActiveException.class })
    protected ResponseEntity<?> handleConflictExceptions(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {
            CartNotFoundException.class,
            UserAddressNotFoundException.class })
    protected ResponseEntity<?> handleInternalErrorException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {
            UserHasNoAccessException.class })
    protected ResponseEntity<?> handleForbiddenException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}
