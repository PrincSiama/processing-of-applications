package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.dto.CreateApplicationDto;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationControllerTest {
    MockMvc mockMvc;

    @BeforeEach
    void setUp(ApplicationContext applicationContext) {
        this.mockMvc = applicationContext.getBean(MockMvc.class);
    }

    /*@Test
    @WithMockUser
    @DisplayName("Создание заявки пользователем с ролью USER")
    void createApplication() throws Exception {
        CreateApplicationDto createApplicationDto = new CreateApplicationDto(
                "Test description", "Test name", "+71118552947");

        mockMvc.perform(post("/application")
                .contentType(MediaType.APPLICATION_JSON)
                .content());
    }
*/
    @Test
    void updateApplication() {
    }

    @Test
    void sendApplication() {
    }

    @Test
    void acceptApplication() {
    }

    @Test
    void rejectApplication() {
    }

    @Test
    void getUserApplications() {
    }

    @Test
    void getSentApplications() {
    }
}