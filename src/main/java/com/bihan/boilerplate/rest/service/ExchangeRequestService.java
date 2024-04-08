package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.dto.ExchangeRequestDetailsResponse;
import com.bihan.boilerplate.rest.model.request.NewExchangeRequestDetails;

import java.util.List;

public interface ExchangeRequestService {
    ExchangeRequestDetailsResponse createRequest(NewExchangeRequestDetails exchangeRequest, Long requesterUserId);

    List<ExchangeRequestDetailsResponse> getExchangeRequestsForAUser(Long userId);

    ExchangeRequestDetailsResponse acceptExchangeRequest(Long exchageRequestId, Long userId);

    ExchangeRequestDetailsResponse completeExchangeRequest(Long exchangeRequestId, Long userId);
}
