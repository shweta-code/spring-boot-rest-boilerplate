package com.intuit.exchange.rest.controller;

import com.intuit.exchange.rest.controller.response.UserDetailsResponse;
import com.intuit.exchange.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserById(@PathVariable("userId") Long userId) {
        UserDetailsResponse user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

// TODO
// 1. What should happen if you fail to create a resource.
// Possible Exceptions when inserting a new row in mysql.
// 2. Best practices of a Spring project
// 3. Check for validity of inputs and validity before inserting into database in one sample API
// TODO - Remove category controller
