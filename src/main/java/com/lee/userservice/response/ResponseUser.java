package com.lee.userservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lee.userservice.domain.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;

    @Builder
    public ResponseUser(String email, String name, String userId, List<ResponseOrder> orders) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.orders = orders;
    }

    public ResponseUser(UserEntity userEntity){
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.userId = userEntity.getUserId();
        orders = new ArrayList<>();
    }
}
