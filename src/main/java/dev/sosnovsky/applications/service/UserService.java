package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.JWTaccess.JwtRequest;
import dev.sosnovsky.applications.JWTaccess.JwtResponse;
import dev.sosnovsky.applications.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<JwtResponse> login(JwtRequest jwtRequest);
    ResponseEntity<JwtResponse> getNewAccessToken(String refreshToken);
    List<UserDto> getUsers(Pageable pageable);
    UserDto setOperatorRole(int id);

}
