package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.exception.NotCreatorException;
import dev.sosnovsky.applications.exception.NotFoundException;
import dev.sosnovsky.applications.exception.StatusException;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.Sort;
import dev.sosnovsky.applications.model.StatusOfApplications;
import dev.sosnovsky.applications.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ModelMapper mapper;

    @Override
    public Application create(int userId, CreateApplicationDto createApplicationDto) {
        Application application = mapper.map(createApplicationDto, Application.class);
        application.setStatus(StatusOfApplications.DRAFT);
        application.setCreatorsId(userId);
        application.setCreateDate(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    @Override
    public Application update(int applicationId, int userId, CreateApplicationDto createApplicationDto) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        if (application.getCreatorsId() != userId) {
            throw new NotCreatorException("Пользователь с id = " + userId + " не является создателем заявки с id = "
                    + applicationId + ". Обновление заявки невозможно");
        }
        application.setDescription(createApplicationDto.getDescription());
        application.setName(createApplicationDto.getName());
        application.setPhoneNumber(createApplicationDto.getPhoneNumber());
        return applicationRepository.save(application);
    }

    @Override
    public Application send(int applicationId, int userId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        if (application.getCreatorsId() != userId) {
            throw new NotCreatorException("Пользователь с id = " + userId + " не является создателем заявки с id = "
                    + applicationId + ". Отправка заявки невозможна");
        }
        application.setStatus(StatusOfApplications.SENT);
        return applicationRepository.save(application);
    }

    @Override
    public Application accept(int applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        if (application.getStatus().equals(StatusOfApplications.ACCEPTED)) {
            throw new StatusException(
                    "У заявки с id = " + applicationId + " уже установлен статус " + StatusOfApplications.ACCEPTED);
        }
        application.setStatus(StatusOfApplications.ACCEPTED);
        return applicationRepository.save(application);
    }

    @Override
    public Application reject(int applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        if (application.getStatus().equals(StatusOfApplications.REJECTED)) {
            throw new StatusException(
                    "У заявки с id = " + applicationId + " уже установлен статус " + StatusOfApplications.REJECTED);
        }
        application.setStatus(StatusOfApplications.REJECTED);
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getUserApplications(int userId, Sort sort, Pageable pageable) {
        List<Application> userApplicationsList =
                applicationRepository.findAllByCreatorsIdOrderByCreateDateDesc(userId, pageable);
        if (userApplicationsList.isEmpty()) {
            throw new NotFoundException("У пользователя с id = " + userId + " отсутствуют созданные заявки");
        }
        if (sort.equals(Sort.ASC)) {
            Collections.reverse(userApplicationsList);
        }
        return userApplicationsList;
    }

    @Override
    public List<Application> getSentApplications(String findText, Sort sort, Pageable pageable) {
        // todo использовать спецификацию или два отдельных запроса
        /*if (findText != null) {
            Specification<User> findTextSpecification = (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + findText.toLowerCase() + "%");
        }
        Specification<Application> findSentSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("status")), StatusOfApplications.SENT);*/
        return null;
    }
}
