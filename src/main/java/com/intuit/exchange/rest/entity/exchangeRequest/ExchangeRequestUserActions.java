package com.intuit.exchange.rest.entity.exchangeRequest;

public enum ExchangeRequestUserActions {
    ACCEPT("Accept"),
    REJECT("Reject"),
    CANCEL("Cancel"),
    COMPLETE("Complete");

    ExchangeRequestUserActions(String value) {
        this.value = value;
    }

    private String value;
}
