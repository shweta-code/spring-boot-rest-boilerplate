package com.intuit.exchange.rest.exceptionHandlers;

import com.intuit.exchange.rest.controller.response.APIErrorResponseEntity;
import com.intuit.exchange.rest.exception.BadRequestException;
import com.intuit.exchange.rest.exception.ForbiddenActionException;
import com.intuit.exchange.rest.exception.NotFoundException;
import com.intuit.exchange.rest.exception.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponseEntity> handleResourceNotFoundException(ResourceNotFoundException ex) {
        print(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIErrorResponseEntity.create(ex.getMessage()));
    }

    @ExceptionHandler({Exception.class, DataAccessException.class})
    public ResponseEntity<APIErrorResponseEntity> handleGenericOrDataAccessException(Exception ex) {
        print(ex);
        APIErrorResponseEntity apiErrorResponseEntity = APIErrorResponseEntity.create("Something went wrong. Please try again or contact support.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponseEntity);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIErrorResponseEntity> handleBadRequestException(Exception ex) {
        print(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIErrorResponseEntity.create(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIErrorResponseEntity> handleNotFoundException(Exception ex) {
        print(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIErrorResponseEntity.create(ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<APIErrorResponseEntity> handleForbiddenActionException(Exception ex) {
        print(ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIErrorResponseEntity.create("Given action cannot be performed on the resource"));
    }

    private void print(Exception ex){
        System.err.println("An error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }

}
