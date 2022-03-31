package com.example.spring_mvc_data_crud.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Optional;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    public static Optional<Role> find(String gender) {

        return Arrays.stream(values()).filter(it -> it.name().equals(gender))
                .findFirst();
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
