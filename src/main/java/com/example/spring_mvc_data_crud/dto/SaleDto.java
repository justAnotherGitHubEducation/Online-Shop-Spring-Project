package com.example.spring_mvc_data_crud.dto;

import com.example.spring_mvc_data_crud.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDto {

   private Long id;
   private LocalDate date;
   @NotNull(message = "User is empty")
   private Long user_id;
   private String username;
   @NotBlank(message = "description is empty")
  private String description;
}
