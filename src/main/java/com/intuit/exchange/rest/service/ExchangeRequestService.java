package com.intuit.exchange.rest.service;

import com.intuit.exchange.rest.responseObjects.ExchangeRequestDetailsResponse;
import com.intuit.exchange.rest.model.request.NewExchangeRequestDetails;

import java.util.List;

public interface ExchangeRequestService {
    ExchangeRequestDetailsResponse createRequest(NewExchangeRequestDetails exchangeRequest, Long requesterUserId);

    List<ExchangeRequestDetailsResponse> getExchangeRequestsForAUser(Long userId);

    ExchangeRequestDetailsResponse acceptExchangeRequest(Long exchangeRequestId, Long userId);

    ExchangeRequestDetailsResponse completeExchangeRequest(Long exchangeRequestId, Long userId);
}
