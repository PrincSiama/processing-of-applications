package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.Jwt.JwtRequest;
import dev.sosnovsky.applications.Jwt.JwtResponse;
import dev.sosnovsky.applications.dto.CreateUserDto;
import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto);

    JwtResponse login(JwtRequest jwtRequest);

    JwtResponse getNewAccessToken(String refreshToken);

    List<UserDto> getUsers(Pageable pageable);

    UserDto setOperatorRole(int id);
}
