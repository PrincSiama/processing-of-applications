package dev.sosnovsky.applications.controller;

import dev.sosnovsky.applications.config.SecurityConfig;
import dev.sosnovsky.applications.model.Role;
import dev.sosnovsky.applications.service.ApplicationService;
import dev.sosnovsky.applications.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = SecurityConfig.class)
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ApplicationService applicationService;
    @MockBean
    private UserService userService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    /*@BeforeEach
    void setUp(ApplicationContext applicationContext) {
        this.mockMvc = applicationContext.getBean(MockMvc.class);
    }*/

    @Test
    void createAuthToken() throws Exception {
        mockMvc.perform(
                        get("/user/login"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/login")
                        .with(
                                user("+71234567890")
                                        .password("$2a$10$NAktC/XBl.16hEtLkkdkyuwcTUqbjhHaLelARpHF8hVrVFk16zuse")
                                        .roles(Role.USER.name())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    @DisplayName("Запрос списка пользователей пользователем с ролью ADMINISTRATOR")
    void getUsersWithRoleAdmin() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser()
    @DisplayName("Запрос списка пользователей пользователем с ролью USER")
    void getUsersWithRoleUser() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    @DisplayName("Назначение роли оператора пользователем с ролью ADMINISTRATOR")
    void setOperatorRoleWithRoleAdmin() throws Exception {
        mockMvc.perform(put("/user/1/set-role")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Назначение роли оператора пользователем с ролью USER")
    void setOperatorRoleWithRoleUser() throws Exception {
        mockMvc.perform(put("/user/1/set-role")).andExpect(status().isForbidden());
    }

}