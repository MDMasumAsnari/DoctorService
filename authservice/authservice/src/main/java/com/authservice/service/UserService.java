package com.authservice.service;

import com.authservice.dto.ApiResponse;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<String> register(UserDto userDto){

        ApiResponse<String> response  = new ApiResponse<>();

        if(userRepository.existsByUsername(userDto.getUsername())) {
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with username exists");
            return response;
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email exists");
            return response;
        }

        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        response.setMessage("Success");
        response.setStatus(201);
        response.setData("Registration Success");
        return response;
    }

}
