package dev.sosnovsky.applications.dto;

import dev.sosnovsky.applications.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String name;
    private String phoneNumber;
    private List<Role> role;
}
