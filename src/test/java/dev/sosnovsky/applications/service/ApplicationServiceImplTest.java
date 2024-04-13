package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.exception.NotFoundException;
import dev.sosnovsky.applications.exception.StatusException;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.StatusOfApplications;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.ApplicationRepository;
import dev.sosnovsky.applications.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {
    private ApplicationService applicationService;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    PhoneDetailsService phoneDetailsService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        applicationService = new ApplicationServiceImpl(applicationRepository, phoneDetailsService,
                userRepository, new ModelMapper());
    }

    @Test
    @DisplayName("Создание заявки")
    void createTest() {
        int testUserId = 16;
        CreateApplicationDto createApplicationDto1 = new CreateApplicationDto(
                "Test Description 1", "Test Name 1", "+79094513816");
        Principal principal = () -> "+79604043599";

        User testUser = new User();
        testUser.setId(testUserId);

        when(userRepository.findAllByPhoneNumber(principal.getName())).thenReturn(Optional.of(testUser));
        when(applicationRepository.save(any(Application.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, Application.class));
        when(phoneDetailsService.isCorrectPhone(anyString())).thenReturn(true);

        Application application = applicationService.create(createApplicationDto1, principal);

        assertEquals(testUserId, application.getCreatorsId());
        assertEquals(createApplicationDto1.getDescription(), application.getDescription());
        assertEquals(createApplicationDto1.getPhoneNumber(), application.getPhoneNumber());
    }

    @Test
    @DisplayName("Обновление заявки")
    void updateTest() {
        int testUserId = 5;
        Principal principal = () -> "+71239547226";

        User testUser = new User();
        testUser.setId(testUserId);

        when(userRepository.findAllByPhoneNumber(principal.getName())).thenReturn(Optional.of(testUser));
        when(applicationRepository.save(any(Application.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, Application.class));
        Application application = new Application(1, "Test Description 1", "+71238904432",
                "Test Name 1", testUserId, StatusOfApplications.DRAFT, LocalDateTime.now());
        when(applicationRepository.findById(anyInt())).thenReturn(Optional.of(application));

        CreateApplicationDto createApplicationDtoUpdate = new CreateApplicationDto(
                "Test Description Update", "Test Name 1 upd", "+71238904433");

        Application updateApp = applicationService.update(application.getId(), createApplicationDtoUpdate, principal);

        assertEquals(testUserId, updateApp.getCreatorsId());
        assertEquals(createApplicationDtoUpdate.getDescription(), updateApp.getDescription());
    }

    @Test
    @DisplayName("Обновление несуществующей заявки")
    void incorrectUpdateTest() {
        assertThrows(NotFoundException.class,
                () -> applicationService.update(578, null, null));
    }

    @Test
    @DisplayName("Принятие заявки")
    void acceptTest() {
        int testUserId = 5;
        Principal principal = () -> "+71239547226";

        User testUser = new User();
        testUser.setId(testUserId);

        when(applicationRepository.save(any(Application.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, Application.class));
        Application application = new Application(1, "Test Description 1", "+71238904432",
                "Test Name 1", testUserId, StatusOfApplications.DRAFT, LocalDateTime.now());
        when(applicationRepository.findById(anyInt())).thenReturn(Optional.of(application));

        assertThrows(StatusException.class,
                () -> applicationService.accept(application.getId()));

        application.setStatus(StatusOfApplications.SENT);

        Application acceptApp = applicationService.accept(application.getId());

        assertEquals(StatusOfApplications.ACCEPTED, acceptApp.getStatus());
        assertEquals(testUserId, acceptApp.getCreatorsId());
    }

    @Test
    @DisplayName("Поиск заявок по пустому списку статусов")
    void getAllApplicationsTest() {

        List<Application> testList1 = applicationService.getAllApplications(
                List.of(), "", 0, 2, Sort.Direction.DESC);

        ArgumentCaptor<List<StatusOfApplications>> captor = ArgumentCaptor.forClass(List.class);
        verify(applicationRepository).findAllByStatusInAndNameContainsIgnoreCase(
                captor.capture(), anyString(), any(PageRequest.class));
        assertEquals(3, captor.getValue().size());
    }

    @Test
    @DisplayName("Поиск заявок по параметрам")
    void getAllApplicationsTest2() {

        List<Application> testList2 = applicationService.getAllApplications(
                List.of(StatusOfApplications.ACCEPTED), "", 0, 2, Sort.Direction.DESC);

        ArgumentCaptor<List<StatusOfApplications>> captor1 = ArgumentCaptor.forClass(List.class);
        verify(applicationRepository).findAllByStatusInAndNameContainsIgnoreCase(
                captor1.capture(), anyString(), any(PageRequest.class));
        assertEquals(1, captor1.getValue().size());

        assertThrows(IllegalArgumentException.class, () -> applicationService.getAllApplications(
                List.of(StatusOfApplications.DRAFT), "", 0, 2, Sort.Direction.DESC));
    }

    @Test
    @DisplayName("Поиск заявок по невалидному статусу")
    void getAllApplicationsTest3() {

        assertThrows(IllegalArgumentException.class, () -> applicationService.getAllApplications(
                List.of(StatusOfApplications.DRAFT), "", 0, 2, Sort.Direction.DESC));
    }
}