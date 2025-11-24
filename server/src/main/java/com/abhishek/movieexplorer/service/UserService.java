package com.abhishek.movieexplorer.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.abhishek.movieexplorer.dto.response.UserResponse;
import com.abhishek.movieexplorer.exception.ResourceNotFoundException;
import com.abhishek.movieexplorer.mapper.UserMapper;
import com.abhishek.movieexplorer.model.User;
import com.abhishek.movieexplorer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;

    public UserResponse getMe(UUID userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toDto(user);
    }

}
