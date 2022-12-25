package com.lee.userservice.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    @Builder
    public ResponseUser(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }
}
