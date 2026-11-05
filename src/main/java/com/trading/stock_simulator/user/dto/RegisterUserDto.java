package com.trading.stock_simulator.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDto {
    private String username;
    private String email;
    private String password;
}
