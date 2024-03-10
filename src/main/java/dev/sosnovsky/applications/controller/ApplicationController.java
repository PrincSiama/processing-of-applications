package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public Application createApplication(@RequestBody @Valid CreateApplicationDto createApplicationDto,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.create(createApplicationDto, userDetails);
    }

    @PutMapping("/{applicationId}")
    public Application updateApplication(@PathVariable int applicationId,
                                         @RequestBody @Valid CreateApplicationDto createApplicationDto,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.update(applicationId, createApplicationDto, userDetails);
    }

    @PutMapping("/{applicationId}/send")
    public Application sendApplication(@PathVariable int applicationId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.send(applicationId, userDetails);
    }

    @PutMapping("/{applicationId}/accept")
    public Application acceptApplication(@PathVariable int applicationId) {
        return applicationService.accept(applicationId);
    }

    @PutMapping("/{applicationId}/reject")
    public Application rejectApplication(@PathVariable int applicationId) {
        return applicationService.reject(applicationId);
    }

    @GetMapping
    public List<Application> getUserApplications(
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") Sort.Direction sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        return applicationService.getUserApplications(page, size, sort, userDetails);
    }

    @GetMapping("/sent")
    public List<Application> getSentApplications(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") Sort.Direction sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return applicationService.getSentApplications(text, page, size, sort);
    }

}
