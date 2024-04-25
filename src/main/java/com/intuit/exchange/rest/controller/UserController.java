package com.intuit.exchange.rest.controller;

import com.intuit.exchange.rest.responseObjects.UserDetailsResponse;
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
