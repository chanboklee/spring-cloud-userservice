package com.lee.userservice.service;

import com.lee.userservice.domain.UserEntity;
import com.lee.userservice.dto.UserDto;
import com.lee.userservice.request.RequestUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserEntity createUser(RequestUser requestUser);
    UserEntity getUserByUserId(String userId);
    List<UserEntity> getUserByAll();
}
