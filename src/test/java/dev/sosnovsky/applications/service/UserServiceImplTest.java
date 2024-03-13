package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Создание токена")
    void createAuthToken() {

    }

    @Test
    @DisplayName("Получение списка пользователей")
    void getUsers() {
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        List<User> userList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> userDtoList = userService.getUsers(pageable);

        verify(userRepository).findAll(pageable);
        assertTrue(userDtoList.isEmpty());
    }

    @Test
    @DisplayName("Назначение роли оператора")
    void setOperatorRole() {
    }
}