package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ApplicationService {
    Application create(CreateApplicationDto createApplicationDto, UserDetails userDetails);

    Application update(int applicationId, CreateApplicationDto createApplicationDto, UserDetails userDetails);

    Application send(int applicationId, UserDetails userDetails);

    Application accept(int id);

    Application reject(int id);

    List<Application> getUserApplications(Sort sort, Pageable pageable, UserDetails userDetails);

    List<Application> getSentApplications(String findText, Sort sort, Pageable pageable);
    Integer getUserIdFromUserDetails(UserDetails userDetails);
}
