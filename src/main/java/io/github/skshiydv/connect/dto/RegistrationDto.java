package io.github.skshiydv.connect.dto;

import lombok.Data;


@Data
public class RegistrationDto {
    private String username;
    private String name;
    private String email;
    private String password;
}
