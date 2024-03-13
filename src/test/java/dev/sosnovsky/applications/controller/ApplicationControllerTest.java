package dev.sosnovsky.applications.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

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