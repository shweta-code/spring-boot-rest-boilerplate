package com.bihan.boilerplate.rest.exceptionHandlers;

import com.bihan.boilerplate.rest.controller.response.APIErrorResponseEntity;
import com.bihan.boilerplate.rest.exception.BadRequestException;
import com.bihan.boilerplate.rest.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponseEntity> handleResourceNotFoundException(ResourceNotFoundException ex) {
        APIErrorResponseEntity errorResponse = new APIErrorResponseEntity();
        errorResponse.setSuccess(false);
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponseEntity> handleGenericException(Exception ex) {
        APIErrorResponseEntity errorResponse = new APIErrorResponseEntity();
        errorResponse.setSuccess(false);
        errorResponse.setMessage("Something went wrong. Please try again or contact support.");
        // errorResponse.setErrors(Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIErrorResponseEntity> handleBadRequestException(Exception ex) {
        APIErrorResponseEntity errorResponse = new APIErrorResponseEntity();
        errorResponse.setSuccess(false);
        errorResponse.setMessage(ex.getMessage());
        // errorResponse.setErrors(Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
