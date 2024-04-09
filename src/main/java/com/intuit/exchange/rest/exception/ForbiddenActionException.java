package com.intuit.exchange.rest.exception;

import lombok.Getter;

@Getter
public class ForbiddenActionException extends RuntimeException {

    private String actionBy;
    private String actionOn;
    private Object fieldValue;
    public ForbiddenActionException(String actionBy, String actionOn, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", actionBy, actionOn, fieldValue));
        this.actionBy = actionBy;
        this.actionOn = actionOn;
        this.fieldValue = fieldValue;
    }

    public ForbiddenActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
