package com.example.spring_mvc_data_crud.controller;
import com.example.spring_mvc_data_crud.domain.Role;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.UserDto;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.repos.UserRepo;
import com.example.spring_mvc_data_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class RegistrationController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") UserDto userDto, Model model) {

        User userFromDb = (User) userService.loadUserByUsername(userDto.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        userService.addUser(userMapper.userDtoToUser(userDto));
        return "redirect:/login";
    }
}