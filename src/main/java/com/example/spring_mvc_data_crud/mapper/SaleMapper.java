package com.example.spring_mvc_data_crud.mapper;

import com.example.spring_mvc_data_crud.domain.Sale;
import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.SaleDto;
import com.example.spring_mvc_data_crud.dto.UserDto;
import com.example.spring_mvc_data_crud.service.SaleService;
import com.example.spring_mvc_data_crud.service.UserService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceContext;

@Mapper(componentModel = "spring", uses = {SaleService.class,UserService.class})
public interface SaleMapper {

    @Mapping(source = "user_id",target = "user")
    @Mapping(target = "items",ignore = true)
    Sale saleDtoToSale(SaleDto saleDto);


    @Mapping(source = "sale.user.id",target = "user_id")
    @Mapping(source = "sale.user.username",target = "username")
    SaleDto saleToSaleDto(Sale sale);

}

