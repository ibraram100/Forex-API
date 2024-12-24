package com.ibrahimRamadan.forexAPI.service;

import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.exception.ResourceNotFoundException;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserByUsername(String username)
    {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty())
        {
            throw new ResourceNotFoundException("User not found for this username :: " + username);
        }
        return userOptional.get();
    }

}

