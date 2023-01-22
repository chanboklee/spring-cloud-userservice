package com.lee.userservice.service;

import com.lee.userservice.domain.UserEntity;
import com.lee.userservice.repository.UserRepository;
import com.lee.userservice.request.RequestUser;
import com.lee.userservice.response.ResponseOrder;
import com.lee.userservice.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;
    private final Environment env;

    @Transactional
    @Override
    public UserEntity createUser(RequestUser requestUser) {
        UserEntity userEntity = UserEntity.builder()
                .email(requestUser.getEmail())
                .name(requestUser.getName())
                .encryptedPwd(passwordEncoder.encode(requestUser.getPwd()))
                .build();

        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public ResponseUser getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null){
            throw new UsernameNotFoundException("User not found");
        }

        // String orderUrl = "http://127.0.0.1:8000/order-service/%s/orders";
        String orderUrl = String.format(env.getProperty("order_service_url"), userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseOrder>>() {
        });

        ResponseUser responseUser = ResponseUser.builder().email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .orders(orderListResponse.getBody())
                .build();

        return responseUser;
    }

    @Override
    public List<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null){
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserEntity getUserDetailByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException(email);
        }
        return userEntity;
    }
}
