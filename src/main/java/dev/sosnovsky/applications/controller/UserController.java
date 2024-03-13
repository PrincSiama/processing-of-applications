package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.JWTaccess.JwtRequest;
import dev.sosnovsky.applications.JWTaccess.JwtResponse;
import dev.sosnovsky.applications.dto.RefreshTokenDto;
import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        return userService.login(jwtRequest);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshTokenDto request) {
        return userService.getNewAccessToken(request.getRefreshToken());
    }

    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return userService.getUsers(PageRequest.of(page, size));
    }

    @PutMapping("/{userId}/set-role")
    public UserDto setOperatorRole(@PathVariable int userId) {
        return userService.setOperatorRole(userId);
    }

    // User, Operator, Administrator
    /*@PutMapping("/login")
    // выдать токен
    public void login() {

    }*/

    // User, Operator, Administrator
   /* @PutMapping("/logout")
    public void logOut() {

    }*/

}
