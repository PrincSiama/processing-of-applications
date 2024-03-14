package dev.sosnovsky.applications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sosnovsky.applications.JWTaccess.JwtRequest;
import dev.sosnovsky.applications.JWTaccess.JwtResponse;
import dev.sosnovsky.applications.JWTaccess.JwtTokenUtils;
import dev.sosnovsky.applications.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private UserDetails userDetails;

    @Test
    @DisplayName("Успешное создание токена")
    void createAuthToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .content(objectMapper.writeValueAsString(
                                new JwtRequest("+71234567890", "password1")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JwtResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JwtResponse.class);
        assertFalse(response.getAccessToken().isBlank());
        assertFalse(response.getRefreshToken().isBlank());
    }

    @Test
    @DisplayName("Запрос списка пользователей пользователем с ролью ADMINISTRATOR")
    void getUsersWithRoleAdmin() throws Exception {
        when(userDetails.getUsername()).thenReturn("Admin");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.ADMINISTRATOR.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);
        mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Запрос списка пользователей пользователем с ролью USER")
    void getUsersWithRoleUser() throws Exception {
        when(userDetails.getUsername()).thenReturn("User");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.USER.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);

        mockMvc.perform(get("/user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Назначение роли оператора пользователем с ролью ADMINISTRATOR")
    void setOperatorRoleWithRoleAdmin() throws Exception {
        when(userDetails.getUsername()).thenReturn("Admin");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.ADMINISTRATOR.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);

        mockMvc.perform(put("/user/1/set-role")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Назначение роли оператора пользователем с ролью USER")
    void setOperatorRoleWithRoleUser() throws Exception {
        when(userDetails.getUsername()).thenReturn("User");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.USER.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);

        mockMvc.perform(put("/user/1/set-role")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}