package com.intuit.exchange.rest.validator;

public class ExchangeRequestValidator implements IExchangeRequestValidator{


    @Override
    public boolean validateActionByUser(Long exchangeRequestId, Long userId) {
        return false;
    }
}
