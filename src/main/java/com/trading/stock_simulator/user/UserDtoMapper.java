package com.trading.stock_simulator.user;

import com.trading.stock_simulator.user.dto.RegisterUserDto;
import com.trading.stock_simulator.user.dto.UserDto;

public class UserDtoMapper {
    static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setCashBalance(user.getCashBalance());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }

    static User toEntity(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(registerUserDto.getPassword());
        return user;
    }
}
