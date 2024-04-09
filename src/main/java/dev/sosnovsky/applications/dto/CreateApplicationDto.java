package dev.sosnovsky.applications.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateApplicationDto {
    @NotBlank
    private String description;
    @NotBlank
    private String name;
    private String phoneNumber;
}
