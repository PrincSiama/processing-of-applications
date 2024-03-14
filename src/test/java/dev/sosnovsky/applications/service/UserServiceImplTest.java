package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.Jwt.JwtTokenUtils;
import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.model.Role;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @Mock
    private AuthenticationManager authenticationManager;
    private final ModelMapper mapper = new ModelMapper();

   @BeforeEach
   void setUp() {
       userService = new UserServiceImpl(userRepository, userDetailsService, jwtTokenUtils, authenticationManager, mapper);
   }

    @Test
    @DisplayName("Получение списка пользователей")
    void getUsers() {
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, Sort.unsorted());

        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(new User(), new User())));

        List<UserDto> userDtoList = userService.getUsers(pageable);

        verify(userRepository).findAll(pageable);
        assertEquals(2, userDtoList.size());
    }

    @Test
    @DisplayName("Назначение роли оператора")
    void setOperatorRole() {
        User testUser = new User();
        testUser.setId(1);
        testUser.setName("test user");
        testUser.setPhoneNumber("+79882134667");
        testUser.setPassword("password1");
        testUser.setRole(new HashSet<>(Set.of(Role.USER)));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        User testUser2 = new User();
        testUser2.setId(1);
        testUser2.setName("test user");
        testUser2.setPhoneNumber("+79882134667");
        testUser2.setPassword("password1");
        testUser2.setRole(new HashSet<>(Set.of(Role.OPERATOR)));
        when(userRepository.save(any(User.class))).thenReturn(testUser2);
        UserDto userDto = userService.setOperatorRole(testUser.getId());

        assertEquals(testUser2.getRole(), userDto.getRole());
    }
}