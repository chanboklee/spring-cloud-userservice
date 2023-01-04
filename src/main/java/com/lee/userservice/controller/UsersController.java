package com.lee.userservice.controller;

import com.lee.userservice.domain.UserEntity;
import com.lee.userservice.dto.UserDto;
import com.lee.userservice.request.RequestUser;
import com.lee.userservice.response.ResponseUser;
import com.lee.userservice.service.UserService;
import com.lee.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UsersController {

    private final Environment environment;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in User Service" +
                ", port(local.server.port)="+ environment.getProperty("local.server.port") +
                ", port(server.port)="+ environment.getProperty("server.port") +
                ", token secret="+ environment.getProperty("token.secret") +
                ", token expiration time=" + environment.getProperty("token.expiration.time"));
    }

    @GetMapping("/welcome")
    public String welcome(){
        // return environment.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser requestUser){
        UserEntity userEntity = userService.createUser(requestUser);

        ResponseUser responseUser = ResponseUser.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        List<ResponseUser> result = userService.getUserByAll().stream().map(ResponseUser::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserEntity userEntity = userService.getUserByUserId(userId);
        ResponseUser responseUser = ResponseUser.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }


}
