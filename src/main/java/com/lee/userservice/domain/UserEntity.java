package com.lee.userservice.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPwd;

    @Builder
    public UserEntity(String email, String name, String encryptedPwd) {
        this.email = email;
        this.name = name;
        this.userId = UUID.randomUUID().toString();
        this.encryptedPwd = encryptedPwd;
    }
}
