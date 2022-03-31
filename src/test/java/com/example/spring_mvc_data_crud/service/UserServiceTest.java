package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.Role;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.UserDto;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.repos.UserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DisplayName(value = "User Service")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private final List<User> users = new ArrayList<>();
    private User user;
    private UserDto  userDto;


    @BeforeEach
    public void init() {

        user = User.builder()
                .username("Ivan")
                .id(123l)
                .email("123@mail.ru")
                .password("123456")
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .build();

        users.add(user);
    }

    @Test
    @DisplayName("Should Retrieve user by username")
    void ShouldLoadUserByUsername() {

        when(userRepo.findByUsername("Ivan")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("Ivan");

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());

    }

    @Test
    @DisplayName("Should save user")
    void ShouldSaveUserCorrect() {

        when(userRepo.findByUsername("Ivan")).thenReturn(Optional.ofNullable(null));
        when(passwordEncoder.encode(user.getPassword())).thenReturn("123456");
        when(userRepo.save(user)).thenReturn(user);

        boolean result = userService.addUser(user);

        assertEquals(result, true);

        Mockito.verify(userRepo, Mockito.times(1))
                .save(user);

    }

    @Test
    @DisplayName("Should Retrieve all users")
    void shouldReturnFindAll(){

        when(userRepo.findAll()).thenReturn(users);

        List<User> userServiceAll = userService.findAll();

        assertNotNull(userServiceAll);
        assertEquals(1, userServiceAll.size());

    }

    @Test
    @DisplayName("Should Retrieve user by id")
    void shouldFindById(){

        when(userRepo.findById(1l)).thenReturn(Optional.of(user));

        User userById = userService.findById(1l);

        assertThat(userById).isNotNull();;
        assertThat(userById.getUsername()).isEqualTo(user.getUsername());
        assertThat(userById.getPassword()).isEqualTo( user.getPassword());
        assertThat(userById.getId()).isEqualTo(user.getId());

    }

    @Test
    @DisplayName("Should delete user by id")
    void shouldDeleteUser(){

        userService.deleteUser(user.getId());
        Mockito.verify(userRepo, Mockito.times(1))
                .deleteById(user.getId());

    }

    @Test
    @DisplayName("Should update user by id")
    void shouldUpdateUser(){

        when(userRepo.findById(123l)).thenReturn(Optional.of(user));
        user.setEmail("222@gmail.com");
        User userUpdated = userService.update(user,123l);

        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getEmail()).isEqualTo("222@gmail.com");

    }



}