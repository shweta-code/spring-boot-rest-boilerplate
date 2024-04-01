package com.bihan.boilerplate.rest.controller;

import com.bihan.boilerplate.rest.controller.response.PageableAPISuccessResponseEntity;
import com.bihan.boilerplate.rest.dto.ExchangeRequest;
import com.bihan.boilerplate.rest.dto.ExchangeRequestDetailsResponse;
import com.bihan.boilerplate.rest.dto.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;
import com.bihan.boilerplate.rest.service.ExchangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/exchangeRequests")
public class ExchangeRequestController {

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    // TODO - validate input
    // TODO - take requester user from header
    @PostMapping()
    public ResponseEntity<NewExchangeRequestDetails> createExchangeRequest(@RequestBody NewExchangeRequestDetails newExchangeRequestDetails) {
        // TODO - We can create a common exception layer to handle exception across requests
        // We can use functional programming to pass what these methods will do and return response entity

       exchangeRequestService.createRequest(newExchangeRequestDetails);
       return new ResponseEntity<>(newExchangeRequestDetails, HttpStatus.OK);
    }

    // TODO - Validation of bearer token
    @GetMapping()
    public ResponseEntity<PageableAPISuccessResponseEntity<ExchangeRequest>> list(@RequestHeader("bearerToken") Long bearerToken, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {

        // TODO - We can create a common exception layer to handle exception across requests
        // We can use functional programming to pass what these methods will do and return response entity

        List<ExchangeRequestDetailsResponse> exchangeRequestEntities = exchangeRequestService.listMyExchangeRequests(bearerToken);
        return new ResponseEntity<>(new PageableAPISuccessResponseEntity(exchangeRequestEntities, page, pageSize), HttpStatus.OK);
    }

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
