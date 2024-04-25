package com.intuit.exchange.rest.controller;

import com.intuit.exchange.rest.responseObjects.ExchangeRequestDetailsResponse;
import com.intuit.exchange.rest.responseObjects.PageableAPISuccessResponseEntity;
import com.intuit.exchange.rest.model.request.NewExchangeRequestDetails;
import com.intuit.exchange.rest.service.ExchangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/exchangeRequest")
public class ExchangeRequestController {

    @Autowired
    private ExchangeRequestService exchangeRequestService;


    @PostMapping()
    public ResponseEntity<ExchangeRequestDetailsResponse> create(@RequestHeader("bearerToken") Long bearerToken,@RequestBody NewExchangeRequestDetails newExchangeRequestDetails) {
        ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = exchangeRequestService.createRequest(newExchangeRequestDetails, bearerToken);
        return new ResponseEntity<>(exchangeRequestDetailsResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageableAPISuccessResponseEntity<ExchangeRequestDetailsResponse>> list(@RequestHeader("bearerToken") Long bearerToken, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<ExchangeRequestDetailsResponse> exchangeRequests = exchangeRequestService.getExchangeRequestsForAUser(bearerToken);
        return new ResponseEntity<>(new PageableAPISuccessResponseEntity<>(exchangeRequests, page, pageSize, exchangeRequests.size()), HttpStatus.OK);
    }

    @PatchMapping("{requestId}/complete")
    public ResponseEntity<ExchangeRequestDetailsResponse> completeExchangeRequests(@RequestHeader("bearerToken") Long bearerToken,  @PathVariable("requestId") Long exchangeRequestId) {
        ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = exchangeRequestService.completeExchangeRequest(exchangeRequestId, bearerToken);
        return new ResponseEntity<>(exchangeRequestDetailsResponse, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/accept")
    public ResponseEntity<ExchangeRequestDetailsResponse> acceptExchangeRequest(@RequestHeader("bearerToken") Long bearerToken, @PathVariable("requestId") Long exchangeRequestId) {
        ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = exchangeRequestService.acceptExchangeRequest(exchangeRequestId, bearerToken);
        return new ResponseEntity<>(exchangeRequestDetailsResponse, HttpStatus.OK);
    }
}
