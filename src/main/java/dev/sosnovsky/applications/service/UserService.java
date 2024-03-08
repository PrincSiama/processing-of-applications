package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(Pageable pageable);
    UserDto setOperatorRole(int id);

}
