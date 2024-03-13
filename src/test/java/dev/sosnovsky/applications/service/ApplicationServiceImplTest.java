package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.repository.ApplicationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {
    private ApplicationService applicationService;
    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    @DisplayName("Создание заявки")
    void create() {

    }

    @Test
    @DisplayName("Обновление заявки")
    void update() {
    }

    @Test
    void send() {
    }

    @Test
    void accept() {
    }

    @Test
    void reject() {
    }

    @Test
    void getUserApplications() {
    }

    @Test
    void getSentApplications() {
    }
}