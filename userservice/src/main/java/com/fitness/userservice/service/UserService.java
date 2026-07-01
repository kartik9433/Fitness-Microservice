package com.fitness.userservice.service;

import com.fitness.userservice.dto.UserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse register(UserRequest userRequest) {
<<<<<<< HEAD
        if (userRepository.existsByEmail(userRequest.getEmail())) {
=======
        if (userRepository.exitsByEmail(userRequest.getEmail())) {
>>>>>>> cb27da3248d2de220fabba8c6cb4d55cac571b31
            User exituser  = userRepository.findByEmail(userRequest.getEmail());
            UserResponse userResponse1 = new UserResponse();
            userResponse1.setId(exituser.getId());
            userResponse1.setKeycloakId(exituser.getKeycloakId());
            userResponse1.setEmail(exituser.getEmail());
            userResponse1.setPassword(exituser.getPassword());
            userResponse1.setFirstname(exituser.getFirstname());
            userResponse1.setLastname(exituser.getLastname());
            userResponse1.setCreatedAt(exituser.getCreatedAt());
            userResponse1.setUpdatedAt(exituser.getUpdatedAt());
            return userResponse1;
        }

        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setFirstname(userRequest.getFirstname());
<<<<<<< HEAD
        newUser.setKeycloakId(userRequest.getKeycloakId());
=======
>>>>>>> cb27da3248d2de220fabba8c6cb4d55cac571b31
        newUser.setLastname(userRequest.getLastname());
        newUser.setPassword(userRequest.getPassword());
        User user = userRepository.save(newUser);

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(user.getId());
        userResponse2.setKeycloakId(user.getKeycloakId());
        userResponse2.setEmail(user.getEmail());
        userResponse2.setPassword(user.getPassword());
        userResponse2.setFirstname(user.getFirstname());
        userResponse2.setLastname(user.getLastname());
        userResponse2.setCreatedAt(user.getCreatedAt());
        userResponse2.setUpdatedAt(user.getUpdatedAt());

        return userResponse2;
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }

    public Boolean existByUserId(String userId) {
        return userRepository.existsByKeycloakId(userId);
    }

<<<<<<< HEAD
=======

>>>>>>> cb27da3248d2de220fabba8c6cb4d55cac571b31
}