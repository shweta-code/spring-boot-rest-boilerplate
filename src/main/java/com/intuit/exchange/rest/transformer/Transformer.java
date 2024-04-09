package com.intuit.exchange.rest.transformer;

public interface Transformer <I, O>{
    O transform(I input);
}
