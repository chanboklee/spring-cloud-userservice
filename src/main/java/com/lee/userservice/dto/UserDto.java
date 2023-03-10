package com.lee.userservice.dto;

import com.lee.userservice.response.ResponseOrder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private LocalDateTime createdAt;
    private String encryptedPwd;
    private List<ResponseOrder> orders;

    @Builder
    public UserDto(String email, String name, String pwd) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
    }
}
