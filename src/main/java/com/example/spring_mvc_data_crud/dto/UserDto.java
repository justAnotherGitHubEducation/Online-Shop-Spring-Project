package com.example.spring_mvc_data_crud.dto;

import com.example.spring_mvc_data_crud.domain.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Username is empty")
    private String username;

    private String password;

    private boolean active;
    private LocalDate birthday;

    @Email
    private String email;

    private Set<String> roles;

}
