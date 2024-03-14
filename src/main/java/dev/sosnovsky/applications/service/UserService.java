package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.Jwt.JwtRequest;
import dev.sosnovsky.applications.Jwt.JwtResponse;
import dev.sosnovsky.applications.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    JwtResponse login(JwtRequest jwtRequest);
    JwtResponse getNewAccessToken(String refreshToken);
    List<UserDto> getUsers(Pageable pageable);
    UserDto setOperatorRole(int id);

}
