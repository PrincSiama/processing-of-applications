package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.exception.IncorrectPhoneException;
import dev.sosnovsky.applications.exception.NotCreatorException;
import dev.sosnovsky.applications.exception.NotFoundException;
import dev.sosnovsky.applications.exception.StatusException;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.StatusOfApplications;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.ApplicationRepository;
import dev.sosnovsky.applications.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PhoneDetailsService phoneDetailsService;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final static List<StatusOfApplications> FIND_STATUSES_FOR_ADMIN = List.of(
            StatusOfApplications.SENT, StatusOfApplications.ACCEPTED, StatusOfApplications.REJECTED);

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public Application create(CreateApplicationDto createApplicationDto, Principal principal) {
        if (!phoneDetailsService.isCorrectPhone(createApplicationDto.getPhoneNumber())) {
            throw new IncorrectPhoneException("Указанный телефон отсутствует в базе сервиса DaData");
        }
        Application application = mapper.map(createApplicationDto, Application.class);
        application.setStatus(StatusOfApplications.DRAFT);
        application.setCreatorsId(getUserIdFromPrincipal(principal));
        application.setCreateDate(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public Application update(int applicationId, CreateApplicationDto createApplicationDto, Principal principal) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        int userId = getUserIdFromPrincipal(principal);
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
    @PreAuthorize("hasAuthority('USER')")
    public Application send(int applicationId, Principal principal) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
        int userId = getUserIdFromPrincipal(principal);
        if (application.getCreatorsId() != userId) {
            throw new NotCreatorException("Пользователь с id = " + userId + " не является создателем заявки с id = "
                    + applicationId + ". Отправка заявки невозможна");
        }
        application.setStatus(StatusOfApplications.SENT);
        return applicationRepository.save(application);
    }

    @Override
    @PreAuthorize("hasAuthority('OPERATOR')")
    public Application accept(int applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Заявка с id = " + applicationId + " не найдена"));
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


    @Override
    @PreAuthorize("hasAuthority('OPERATOR')")
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

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public List<Application> getUserApplications(int page, int size, Sort.Direction sort, Principal principal) {
        int userId = getUserIdFromPrincipal(principal);
        List<Application> userApplicationsList =
                applicationRepository.findAllByCreatorsId(userId,
                        PageRequest.of(page, size, Sort.by(sort, "createDate")));
        if (userApplicationsList.isEmpty()) {
            throw new NotFoundException("У пользователя с id = " + userId + " отсутствуют созданные заявки");
        }

        return userApplicationsList;
    }

    @Override
    @PreAuthorize("hasAuthority('OPERATOR')")
    public List<Application> getSentApplications(String text, int page, int size, Sort.Direction sort) {
        List<Application> sentApplicationsList =
                applicationRepository.findAllByStatusInAndNameContainsIgnoreCase(
                        List.of(StatusOfApplications.SENT), text,
                        PageRequest.of(page, size, Sort.by(sort, "createDate")));
        if (sentApplicationsList.isEmpty()) {
            throw new NotFoundException("Заявки со статусом " + StatusOfApplications.SENT + " отсутствуют");
        }
        return sentApplicationsList;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public List<Application> getAllApplications(
            List<StatusOfApplications> statuses, String text, int page, int size, Sort.Direction sort) {

        if (!FIND_STATUSES_FOR_ADMIN.containsAll(statuses)) {
            throw new IllegalArgumentException("Поиск доступен только по статусам " + FIND_STATUSES_FOR_ADMIN);
        }
        List<StatusOfApplications> statusesForFind = statuses.isEmpty() ? FIND_STATUSES_FOR_ADMIN : statuses;

        return applicationRepository.findAllByStatusInAndNameContainsIgnoreCase(statusesForFind, text,
                PageRequest.of(page, size, Sort.by(sort, "createDate")));
    }

    private Integer getUserIdFromPrincipal(Principal principal) {
        String phoneNumber = principal.getName();
        User user = userRepository.findAllByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Пользователь с номером телефона " + phoneNumber
                        + " не найден"));
        return user.getId();
    }
}
