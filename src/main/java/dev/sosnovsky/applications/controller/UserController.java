package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // Administrator
    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return userService.getUsers(PageRequest.of(page, size));
    }

    // Administrator
    @PutMapping("/{userId}/setRole")
    public UserDto setOperatorRole(@PathVariable int userId) {
        return userService.setOperatorRole(userId);
    }

    // User, Operator, Administrator
    @PutMapping("/{userId}/logIn")
    public void logIn() {

    }

    // User, Operator, Administrator
    @PutMapping("/{userId}/logOut")
    public void logOut() {

    }

}
