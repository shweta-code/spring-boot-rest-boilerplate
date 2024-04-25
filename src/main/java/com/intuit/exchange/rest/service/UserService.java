package com.intuit.exchange.rest.service;

import com.intuit.exchange.rest.responseObjects.UserDetailsResponse;
import com.intuit.exchange.rest.entity.User;

public interface UserService {
    void rewardsUser(Long userId, int credits);
    UserDetailsResponse getUserById(Long userId);
    User getUserEntityById(Long userId);
}
