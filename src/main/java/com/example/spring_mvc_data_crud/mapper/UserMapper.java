package com.example.spring_mvc_data_crud.mapper;

import com.example.spring_mvc_data_crud.domain.User;
import com.example.spring_mvc_data_crud.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
