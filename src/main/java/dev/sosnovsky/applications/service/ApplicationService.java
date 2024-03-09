package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.Sort;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationService {
    Application create(int userId, CreateApplicationDto createApplicationDto);

    Application update(int applicationId, int userId, CreateApplicationDto createApplicationDto);

    Application send(int applicationId, int userId);

    Application accept(int id);

    Application reject(int id);

    List<Application> getUserApplications(int userId, Sort sort, Pageable pageable);

    List<Application> getSentApplications(String findText, Sort sort, Pageable pageable);
}
