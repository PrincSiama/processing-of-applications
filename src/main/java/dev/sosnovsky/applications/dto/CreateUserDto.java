package dev.sosnovsky.applications.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @Size(min = 8, message = "Длина пароля должна быть не менее 8 символов")
    private String password;
}
