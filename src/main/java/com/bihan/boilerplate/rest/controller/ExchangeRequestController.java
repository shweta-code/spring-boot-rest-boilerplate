package com.bihan.boilerplate.rest.controller;

import com.bihan.boilerplate.rest.dto.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.model.Category;
import com.bihan.boilerplate.rest.model.ExchangeRequest;
import com.bihan.boilerplate.rest.service.CategoryService;
import com.bihan.boilerplate.rest.service.ExchangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/exchangeRequest")
public class ExchangeRequestController {

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    @PostMapping()
    public ResponseEntity<NewExchangeRequestDetails> createExchangeRequest(@RequestBody NewExchangeRequestDetails newExchangeRequestDetails) {
        return new ResponseEntity<>(exchangeRequestService.createRequest(newExchangeRequestDetails), HttpStatus.OK);
    }

    // TODO - Add other methods to change the status of
    // TODO - Add some code for pessimistic locking at abstract level
    // TODO - Document which parts of the application will be good with pessimistic locking
    // and optimistic locking
}

// TODO
// 1. What should happen if you fail to create a resource.
// Possible Exceptions when inserting a new row in mysql.
// 2. Best practices of a Spring project
// 3. Check for validity of inputs and validity before inserting into database in one sample API
