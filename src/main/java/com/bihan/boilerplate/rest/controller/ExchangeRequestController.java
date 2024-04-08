package com.bihan.boilerplate.rest.controller;

import com.bihan.boilerplate.rest.controller.response.PageableAPISuccessResponseEntity;
import com.bihan.boilerplate.rest.dto.ExchangeRequest;
import com.bihan.boilerplate.rest.dto.ExchangeRequestDetailsResponse;
import com.bihan.boilerplate.rest.model.request.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.service.ExchangeRequestService;
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

    // TODO - validate input
    // TODO - Write in doc , for Authorizaation , we will do API level access
    // TODO - Include roles table in db schema
    @PostMapping()
    public ResponseEntity<NewExchangeRequestDetails> create(@RequestHeader("bearerToken") Long bearerToken,@RequestBody NewExchangeRequestDetails newExchangeRequestDetails) {
        // We can use functional programming to pass what these methods will do and return response entity


       exchangeRequestService.createRequest(newExchangeRequestDetails, bearerToken);
       return new ResponseEntity<>(newExchangeRequestDetails, HttpStatus.OK);
    }

    // TODO - Validation of bearer token
    @GetMapping()
    public ResponseEntity<PageableAPISuccessResponseEntity<ExchangeRequest>> list(@RequestHeader("bearerToken") Long bearerToken, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<ExchangeRequestDetailsResponse> exchangeRequestEntities = exchangeRequestService.getExchangeRequestsForAUser(bearerToken);
        return new ResponseEntity<>(new PageableAPISuccessResponseEntity(exchangeRequestEntities, page, pageSize), HttpStatus.OK);
    }

    @PutMapping("{requestId}/complete")
    public ResponseEntity<ExchangeRequestDetailsResponse> completeExchangeRequests(@RequestHeader("bearerToken") Long bearerToken, Long exchangeRequestId) {
        ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = exchangeRequestService.completeExchangeRequest(exchangeRequestId, bearerToken);
        return new ResponseEntity<>(exchangeRequestDetailsResponse, HttpStatus.OK);
    }

    @PutMapping("/{requestId}/accept")
    public ResponseEntity<ExchangeRequestDetailsResponse> acceptExchangeRequest(@RequestHeader("bearerToken") Long bearerToken, Long exchangeRequestId) {
        // TODO - verify if user can accept the request or say Forbidden - Validation
        ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = exchangeRequestService.acceptExchangeRequest(exchangeRequestId, bearerToken);
        return new ResponseEntity<>(exchangeRequestDetailsResponse, HttpStatus.OK);
    }

    // TODO - P1 - Reject , Cancel exchange Request
    // TODO - Entity structure for Browse Request - Thought process on database indexing
    // TODO - You can read about different headers of REST


    /*@GetMapping("/received")
    public ResponseEntity<ExchangeRequestDetailsResponse> receivedExchangeRequests() {
        // get exchange requests from receive table whose status is created
        // with possible actions as accept, reject
        // get  exchange requests from receive table whose status is accepted
        // with possible actions as complete
    }

    @GetMapping("/sent")
    public ResponseEntity<ExchangeRequestDetailsResponse> sentExchangeRequests() {
        // get exchange requests from receive table whose status is created
        // with possible actions as nothing
        // get  exchange requests from receive table whose status is accepted
        // with possible actions as complete
    }

    @PutMapping("{requestId}/accept")
    public ResponseEntity<ExchangeRequestDetailsResponse> receivedExchangeRequests() {
        // verify if user can accept the request or say Forbidden
        // mark it accepted status
    }

    @PutMapping("/received/{requestId}/reject")
    public ResponseEntity<ExchangeRequestDetailsResponse> acceptExchangeRequest(@RequestHeader("bearerToken") String bearerToken) {
        // verify if user can reject the request or say Forbidden - Validation
        // mark it rejected status
    }

    @PutMapping("/sent/{requestId}/cancel")
    public ResponseEntity<ExchangeRequestDetailsResponse> cancelExchangeRequests() {
        // verify if user can reject the request or say Forbidden - Validation
        // verify the reuqest is not completed / expired
        // mark it rejected status
    }

    @PutMapping("/sender/{requestId}/complete")
    public ResponseEntity<ExchangeRequestDetailsResponse> receivedExchangeRequests() {
        // verify the request is owned by both
        // exchange request
    }

    @PutMapping("/receiver/{requestId}/complete")
    public ResponseEntity<ExchangeRequestDetailsResponse> receivedExchangeRequests() {
        // verify the request is owned by both
        // exchange request
        // Pessimistic Lock
    }*/

    // TODO - Add other methods to change the status of ExchangeRequest
    // TODO - Add some code for pessimistic locking at abstract level
    // TODO - Document which parts of the application will be good with pessimistic locking
    // TODO - and optimistic locking
    // TODO -
}

// TODO
// 1. What should happen if you fail to create a resource.
// Possible Exceptions when inserting a new row in mysql.
// 2. Best practices of a Spring project
// 3. Check for validity of inputs and validity before inserting into database in one sample API
// TODO - Create exchnage request ref no n table exchnageRequest, split exchangeRequest in three tables
// TODO - Use Log4j, Swagger
