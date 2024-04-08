package com.bihan.boilerplate.rest.service.impl;

import com.bihan.boilerplate.rest.constants.EntityConstants;
import com.bihan.boilerplate.rest.entity.User;
import com.bihan.boilerplate.rest.exception.ArgumentNullException;
import com.bihan.boilerplate.rest.exception.DatabaseException;
import com.bihan.boilerplate.rest.exception.ResourceNotFoundException;
import com.bihan.boilerplate.rest.repository.UserRepository;
import com.bihan.boilerplate.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public void rewardsUser(Long userId, int credits) {
        if(credits == 0){
            System.out.println("There are no credits to be rewarded");
            return;
        }
        if (userId == null) {
            throw new ArgumentNullException("The userId cannot be null");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId.toString());
        }
        User user = userOptional.get();
        Integer updatedCredits = user.getCredits() + credits;
        user.setCredits(updatedCredits);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new DatabaseException("Exception occurred while saving user", e);
        }
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new ResourceNotFoundException(EntityConstants.ENTITY_NAME_USER, EntityConstants.ENTITY_FIELD_ID, userId.toString());
    }
}
