package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.dto.ExchangeRequest;
import com.bihan.boilerplate.rest.dto.ExchangeRequestDetailsResponse;
import com.bihan.boilerplate.rest.dto.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;

import java.util.List;

public interface ExchangeRequestService {
    NewExchangeRequestDetails createRequest(NewExchangeRequestDetails exchangeRequest);

    List<ExchangeRequestDetailsResponse> listMyExchangeRequests(Long userId);

    /*NewExchangeRequestDetails acceptRequest(Long exchangeRequestId, Long userId);*/


}
