package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.dto.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.dto.NewItemDetails;
import com.bihan.boilerplate.rest.model.ExchangeRequest;
import com.bihan.boilerplate.rest.model.Item;

import java.util.List;

public interface ExchangeRequestService {
    NewExchangeRequestDetails createRequest(NewExchangeRequestDetails exchangeRequest);
}
