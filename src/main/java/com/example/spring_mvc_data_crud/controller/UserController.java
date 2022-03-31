package com.example.spring_mvc_data_crud.controller;


import com.example.spring_mvc_data_crud.domain.Role;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.UserDto;
import com.example.spring_mvc_data_crud.mapper.UserMapper;
import com.example.spring_mvc_data_crud.service.UserService;
import com.example.spring_mvc_data_crud.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private  final UserMapper userMapper;


    @GetMapping()
    String getUserList(Model model){

        model.addAttribute("listUsers", userService.findAll().stream().map(user -> userMapper.userToUserDto(user)).collect(Collectors.toList()));
        return "users/users";
    }

    @GetMapping("/new")
    public String userCreate(Model model){

        model.addAttribute("user", new User());
        return "users/userNew";

    }

    @GetMapping("/{id}/update")
    public String getUserById(Model model,@PathVariable Long id){

        model.addAttribute("user",userMapper.userToUserDto(userService.findById(id)));
        model.addAttribute("roles", Role.values());

        return "users/userUpdate";

    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors",errors);
            return "users/userNew";
        }
        else {
            userService.addUser(userMapper.userDtoToUser(userDto));
            return "redirect:/users";
        }
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") @Valid UserDto userDto,@PathVariable Long id, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = ValidateUtils.getErrors(bindingResult);
            model.addAttribute("errors",errors);
            return "users/userUpdate";

        }
        else {

            userService.update(userMapper.userDtoToUser(userDto),id);
            return "redirect:/users";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long Id) {
        userService.deleteUser(Id);
        return "redirect:/users";
    }


}
