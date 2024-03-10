package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ApplicationService {
    Application create(CreateApplicationDto createApplicationDto, UserDetails userDetails);

    Application update(int applicationId, CreateApplicationDto createApplicationDto, UserDetails userDetails);

    Application send(int applicationId, UserDetails userDetails);

    Application accept(int id);

    Application reject(int id);

    List<Application> getUserApplications(int page, int size, Sort.Direction sort, UserDetails userDetails);

    List<Application> getSentApplications(String findText, int page, int size, Sort.Direction sort);
}
