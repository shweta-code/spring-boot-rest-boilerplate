package com.intuit.exchange.rest.controller.response;

import lombok.*;

@Getter
@Setter
@ToString
public class APIErrorResponseEntity {

    private boolean success = false;
    private String message;

    public static APIErrorResponseEntity create(String message) {
        APIErrorResponseEntity errorResponse = new APIErrorResponseEntity();
        errorResponse.setSuccess(false);
        errorResponse.setMessage(message);
        return errorResponse;
    }

}
