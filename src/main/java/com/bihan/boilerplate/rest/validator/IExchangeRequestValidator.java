package com.bihan.boilerplate.rest.validator;

public interface IExchangeRequestValidator {

    boolean validateActionByUser(Long exchnageRequestId, Long userId);
}
