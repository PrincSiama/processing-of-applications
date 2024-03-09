package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.Sort;
import dev.sosnovsky.applications.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    // User
    @PostMapping
    public Application createApplication(@RequestBody @Valid CreateApplicationDto createApplicationDto,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.create(createApplicationDto, userDetails);
    }

    // User
    @PutMapping("/{applicationId}")
    public Application updateApplication(@PathVariable int applicationId,
                                         @RequestBody @Valid CreateApplicationDto createApplicationDto,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.update(applicationId, createApplicationDto, userDetails);
    }

    // User
    @PutMapping("/{applicationId}/send")
    public Application sendApplication(@PathVariable int applicationId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.send(applicationId, userDetails);
    }

  /*  @GetMapping("/{applicationId}")
    public Application getApplication(@PathVariable int applicationId) {

        return null;
    }*/

    // Operator
    @PutMapping("/{applicationId}/accept")
    public Application acceptApplication(@PathVariable int applicationId) {
        return applicationService.accept(applicationId);
    }

    // Operator
    @PutMapping("/{applicationId}/reject")
    public Application rejectApplication(@PathVariable int applicationId) {
        return applicationService.reject(applicationId);
    }

    // User - только созданные им заявки
    @GetMapping
    public List<Application> getUserApplications(
            @RequestParam(value = "sort", required = false) Sort sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.getUserApplications(sort, PageRequest.of(page, size), userDetails);
    }

    // Operator - с фильтрацией по имени пользователя
    // заявки только в статусе "Отправлено"
    @GetMapping("/sent")
    public List<Application> getSentApplications(
            @RequestParam(value = "text", required = false) String findText,
            @RequestParam(value = "sort", required = false) Sort sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return applicationService.getSentApplications(findText, sort, PageRequest.of(page, size));
    }

}
