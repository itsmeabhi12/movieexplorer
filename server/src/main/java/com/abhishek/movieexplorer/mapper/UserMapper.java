package com.abhishek.movieexplorer.mapper;

import org.mapstruct.Mapper;

import com.abhishek.movieexplorer.dto.response.UserResponse;
import com.abhishek.movieexplorer.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user);
}
