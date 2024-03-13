package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import org.springframework.data.domain.Sort;

import java.security.Principal;
import java.util.List;

public interface ApplicationService {
    Application create(CreateApplicationDto createApplicationDto, Principal principal);

    Application update(int applicationId, CreateApplicationDto createApplicationDto, Principal principal);

    Application send(int applicationId, Principal principals);

    Application accept(int id);

    Application reject(int id);

    List<Application> getUserApplications(int page, int size, Sort.Direction sort, Principal principal);

    List<Application> getSentApplications(String findText, int page, int size, Sort.Direction sort);
}
