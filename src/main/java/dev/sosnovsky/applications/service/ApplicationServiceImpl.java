package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.exception.NotCreatorException;
import dev.sosnovsky.applications.exception.NotFoundException;
import dev.sosnovsky.applications.exception.StatusException;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.Sort;
import dev.sosnovsky.applications.model.StatusOfApplications;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.ApplicationRepository;
import dev.sosnovsky.applications.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public Application create(CreateApplicationDto createApplicationDto, UserDetails userDetails) {
        Application application = mapper.map(createApplicationDto, Application.class);
        application.setStatus(StatusOfApplications.DRAFT);
        application.setCreatorsId(getUserIdFromUserDetails(userDetails));
        application.setCreateDate(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public Application update(int applicationId, CreateApplicationDto createApplicationDto, UserDetails userDetails) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        int userId = getUserIdFromUserDetails(userDetails);
        if (application.getCreatorsId() != userId) {
            throw new NotCreatorException("Пользователь с id = " + userId + " не является создателем заявки с id = "
                    + applicationId + ". Обновление заявки невозможно");
        }
        application.setDescription(createApplicationDto.getDescription());
        application.setName(createApplicationDto.getName());
        application.setPhoneNumber(createApplicationDto.getPhoneNumber());
        return applicationRepository.save(application);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public Application send(int applicationId, UserDetails userDetails) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        int userId = getUserIdFromUserDetails(userDetails);
        if (application.getCreatorsId() != userId) {
            throw new NotCreatorException("Пользователь с id = " + userId + " не является создателем заявки с id = "
                    + applicationId + ". Отправка заявки невозможна");
        }
        application.setStatus(StatusOfApplications.SENT);
        return applicationRepository.save(application);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @Override
    public Application accept(int applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        // todo одобрить или отклонить можно только заявку со статусом SENT. добавить проверку
        if (application.getStatus().equals(StatusOfApplications.SENT)) {
            application.setStatus(StatusOfApplications.ACCEPTED);
        } else {
            throw new StatusException(
                    "Установить статус " + StatusOfApplications.ACCEPTED + " возможно только для заявки," +
                            " имеющей статус " + StatusOfApplications.SENT + ". Заявка с id = " + applicationId +
                    " имеет статус отличный от " + StatusOfApplications.SENT);
        }
        return applicationRepository.save(application);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @Override
    public Application reject(int applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));

        if (application.getStatus().equals(StatusOfApplications.SENT)) {
            application.setStatus(StatusOfApplications.REJECTED);
        } else {
            throw new StatusException(
                    "Установить статус " + StatusOfApplications.REJECTED + " возможно только для заявки," +
                            " имеющей статус " + StatusOfApplications.SENT + ". Заявка с id = " + applicationId +
                            " имеет статус отличный от " + StatusOfApplications.SENT);
        }
        return applicationRepository.save(application);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public List<Application> getUserApplications(Sort sort, Pageable pageable, UserDetails userDetails) {
        int userId = getUserIdFromUserDetails(userDetails);
        List<Application> userApplicationsList =
                applicationRepository.findAllByCreatorsIdOrderByCreateDateDesc(userId, pageable);
        if (userApplicationsList.isEmpty()) {
            throw new NotFoundException("У пользователя с id = " + userId + " отсутствуют созданные заявки");
        }
        if (sort != null && sort.equals(Sort.ASC)) {
            Collections.reverse(userApplicationsList);
        }
        return userApplicationsList;
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
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

    @Override
    // todo метод нельзя сделать privat
    public Integer getUserIdFromUserDetails(UserDetails userDetails) {
        String phoneNumber = userDetails.getUsername();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Пользователь с номером телефона " + phoneNumber
                        + " не найден"));
        return user.getId();
    }
}
