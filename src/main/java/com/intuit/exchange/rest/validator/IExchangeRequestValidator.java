package com.intuit.exchange.rest.validator;

public interface IExchangeRequestValidator {

    boolean validateActionByUser(Long exchnageRequestId, Long userId);
}
