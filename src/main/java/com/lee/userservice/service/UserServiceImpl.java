package com.lee.userservice.service;

import com.lee.userservice.domain.UserEntity;
import com.lee.userservice.repository.UserRepository;
import com.lee.userservice.request.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserEntity createUser(RequestUser requestUser) {
        UserEntity userEntity = UserEntity.builder()
                .email(requestUser.getEmail())
                .name(requestUser.getName())
                .encryptedPwd("encrypted_password")
                .build();

        userRepository.save(userEntity);
        return userEntity;
    }
}
