package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.Role;
import com.example.spring_mvc_data_crud.domain.User;

import com.example.spring_mvc_data_crud.exception.ObjectAlreadyExistsException;
import com.example.spring_mvc_data_crud.exception.ObjectNotFoundException;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.repos.UserRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByUsername(username);
        return user.orElse(null);
    }

    public boolean addUser( User user) {

        Optional<User> userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB.isPresent()){

            throw new ObjectAlreadyExistsException("User with name " +user.getUsername()+ " already Exists" );
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {

        return userRepo.findAll();
    }

    public User update(User user,Long id ) {

        return userRepo.findById(id)
                .map(userid -> {
                            userid.setUsername(user.getUsername());
                            if (!user.getPassword().isEmpty())
                                userid.setPassword(passwordEncoder.encode(user.getPassword()));
                            userid.setRoles(user.getRoles());
                            userid.setActive(user.isActive());
                            userid.setEmail(user.getEmail());
                            userid.setBirthday(user.getBirthday());
                            userRepo.save(userid);

                            return userid;
                        }
                )
        .orElseThrow(() -> new ObjectNotFoundException("User not found( id "+ id+")"));
    }

    public User findById(Long id){

        return userRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found( id "+ id+")"));
    }

    public void deleteUser(Long id){
         userRepo.deleteById(id);
    }
}
