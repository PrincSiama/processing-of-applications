package dev.sosnovsky.applications.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateApplicationDto {
    @NotBlank
    private String description;
    @NotBlank
    private String name;
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = "Некорректный формат номера мобильного телефона")
    private String phoneNumber;
}
