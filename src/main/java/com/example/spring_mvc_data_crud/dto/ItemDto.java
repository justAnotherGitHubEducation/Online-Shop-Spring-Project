package com.example.spring_mvc_data_crud.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    Long id;
    @NotNull(message = "product is empty")
    Long product_id;
    String productName;
    String comment;
    @NotNull(message = "quantity is empty")
    Integer quantity;
    Long sale_id;
}
