package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.StatusOfApplications;
import dev.sosnovsky.applications.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class Controller {

    // User
    @PostMapping
    public Application createApplication (@RequestBody CreateApplicationDto createApplicationDto) {

        return null;
    }

    // User
    @PutMapping("/{applicationId}")
    public Application updateApplication (@PathVariable int applicationId) {

        return null;
    }

    // User
    @PutMapping("/{applicationId}/send")
    public Application sendApplication (@PathVariable int applicationId) {

        return null;
    }

    @GetMapping("/{applicationId}")
    public Application getApplication (@PathVariable int applicationId) {

        return null;
    }

    // Operator
    @PutMapping("/{applicationId}/accept")
    public Application acceptApplication (@PathVariable int applicationId) {

        return null;
    }

    // Operator
    @PutMapping("/{applicationId}/reject")
    public Application rejectApplication (@PathVariable int applicationId) {

        return null;
    }

    // User - только созданные им заявки
    @GetMapping
    public List<Application> getUserApplications (
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        /*int userId =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/

        return null;
    }

    // Operator - с фильтрацией по имени пользователя
    @GetMapping
    public List<Application> getApplications (
            @RequestParam(value = "text", required = false) String findText,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {

        return null;
    }

    // Operator, Administrator
    @GetMapping
    public List<User> getUsers () {

        return null;
    }

    // Administrator
    @PostMapping("/{userId}")
    public User setRoleOperator (@PathVariable int userId) {

        return null;
    }

    // User, Operator, Administrator
    public void logIn() {

    }

    // User, Operator, Administrator
    public void logOut() {

    }

}
