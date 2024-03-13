package dev.sosnovsky.applications.repository;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import dev.sosnovsky.applications.model.Application;
import dev.sosnovsky.applications.model.StatusOfApplications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ApplicationRepositoryTest {
    @Autowired
    private ApplicationRepository applicationRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Test
    @DisplayName("Поиск заявок по номеру id создателя")
    public void findAllByCreatorsIdTest() {
        int creatorsId = 3;
        int page = 0;
        int size = 2;

        List<Application> testList = applicationRepository.findAllByCreatorsId(
                creatorsId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
        assertTrue(testList.isEmpty());

        CreateApplicationDto createApplicationDto1 = new CreateApplicationDto(
                "Test Description 1", "Test Name 1", "+71238904432");
        Application application1 = mapper.map(createApplicationDto1, Application.class);
        application1.setStatus(StatusOfApplications.DRAFT);
        application1.setCreatorsId(creatorsId);
        application1.setCreateDate(LocalDateTime.of(2018, 12, 15, 7, 38, 0));

        applicationRepository.save(application1);

        List<Application> testList1 = applicationRepository.findAllByCreatorsId(
                creatorsId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));

        assertEquals(1, testList1.size());
        assertEquals(createApplicationDto1.getPhoneNumber(), testList1.get(0).getPhoneNumber());

        CreateApplicationDto createApplicationDto2 = new CreateApplicationDto(
                "Test Description 2", "Test Name 2", "+72238908816");
        Application application2 = mapper.map(createApplicationDto2, Application.class);
        application2.setStatus(StatusOfApplications.DRAFT);
        application2.setCreatorsId(creatorsId);
        application2.setCreateDate(LocalDateTime.of(2022, 8, 25, 3, 18, 30));

        applicationRepository.save(application2);

        List<Application> testList2 = applicationRepository.findAllByCreatorsId(
                creatorsId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));

        assertEquals(2, testList2.size());
        assertEquals(createApplicationDto2.getPhoneNumber(), testList2.get(0).getPhoneNumber());

        List<Application> testList3 = applicationRepository.findAllByCreatorsId(
                creatorsId, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createDate")));

        assertEquals(createApplicationDto2.getPhoneNumber(), testList2.get(0).getPhoneNumber());

        assertTrue(applicationRepository.findAllByCreatorsId(
                    1892, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createDate")))
                .isEmpty());
    }

    @Test
    @DisplayName("Поиск заявок по статусу и/или части имени")
    public void findAllByStatusAndNameContainsIgnoreCaseTest() {
        StatusOfApplications status = StatusOfApplications.SENT;
        String text = "";
        int creatorsId = 3;
        int page = 0;
        int size = 2;

        List<Application> testList = applicationRepository
                .findAllByStatusInAndNameContainsIgnoreCase(List.of(status), text,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
        assertTrue(testList.isEmpty());

        CreateApplicationDto createApplicationDto1 = new CreateApplicationDto(
                "Test Description 1", "Test Name 1", "+71238904432");
        Application application1 = mapper.map(createApplicationDto1, Application.class);
        application1.setStatus(StatusOfApplications.SENT);
        application1.setCreatorsId(creatorsId);
        application1.setCreateDate(LocalDateTime.of(2018, 12, 15, 7, 38, 0));

        applicationRepository.save(application1);

        List<Application> testList1 = applicationRepository
                .findAllByStatusInAndNameContainsIgnoreCase(List.of(status), text,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));

        assertEquals(1, testList1.size());
        assertEquals(createApplicationDto1.getPhoneNumber(), testList1.get(0).getPhoneNumber());

        CreateApplicationDto createApplicationDto2 = new CreateApplicationDto(
                "Test Description 2", "Test Name 2", "+72238908816");
        Application application2 = mapper.map(createApplicationDto2, Application.class);
        application2.setStatus(StatusOfApplications.DRAFT);
        application2.setCreatorsId(creatorsId);
        application2.setCreateDate(LocalDateTime.of(2022, 8, 25, 3, 18, 30));

        applicationRepository.save(application2);

        List<Application> testList2 = applicationRepository
                .findAllByStatusInAndNameContainsIgnoreCase(List.of(status), text,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));

        assertEquals(1, testList2.size());
        application2.setStatus(StatusOfApplications.SENT);
        applicationRepository.save(application2);

        testList2 = applicationRepository
                .findAllByStatusInAndNameContainsIgnoreCase(List.of(status), text,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
        assertEquals(2, testList2.size());
        assertEquals(createApplicationDto2.getPhoneNumber(), testList2.get(0).getPhoneNumber());

        CreateApplicationDto createApplicationDto3 = new CreateApplicationDto(
                "Test Description 3", "John", "+79938909033");
        Application application3 = mapper.map(createApplicationDto3, Application.class);
        application3.setStatus(StatusOfApplications.SENT);
        application3.setCreatorsId(creatorsId);
        application3.setCreateDate(LocalDateTime.of(2024, 1, 11, 14, 12, 59));

        applicationRepository.save(application3);
        text = "Hn";

        List<Application> testList3 = applicationRepository
                .findAllByStatusInAndNameContainsIgnoreCase(List.of(status), text,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
        assertEquals(1, testList3.size());
        assertEquals(createApplicationDto3.getPhoneNumber(), testList3.get(0).getPhoneNumber());
    }
}

