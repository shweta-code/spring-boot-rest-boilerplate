package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.entity.User;

public interface UserService {
    void rewardsUser(Long userId, int credits);
    User getUserById(Long userId);
}
