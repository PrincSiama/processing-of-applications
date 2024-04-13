package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.Jwt.JwtRequest;
import dev.sosnovsky.applications.Jwt.JwtResponse;
import dev.sosnovsky.applications.Jwt.JwtTokenUtils;
import dev.sosnovsky.applications.dto.CreateUserDto;
import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.exception.LoginOrPasswordException;
import dev.sosnovsky.applications.exception.NotFoundException;
import dev.sosnovsky.applications.exception.RoleAlreadyExistsException;
import dev.sosnovsky.applications.model.PhoneDetails;
import dev.sosnovsky.applications.model.Role;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.UserRepository;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PhoneDetailsService phoneDetailsService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PermitAll
    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        PhoneDetails phoneDetails = phoneDetailsService.getPhoneDetailsFromDaData(createUserDto.getPhoneNumber());
        User user = mapper.map(createUserDto, User.class);
        user.setPhoneDetails(phoneDetails);
        user.setRole(Set.of(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUserName(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LoginOrPasswordException("Неправильный логин или пароль");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUserName());
        String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getNewAccessToken(String refreshToken) {
        String refreshUserName = jwtTokenUtils.getUserNameFromRefreshToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUserName);
        String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
        return new JwtResponse(accessToken, null);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public List<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public UserDto setOperatorRole(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Невозможно установить роль оператора пользователю с id = " + id + ". Пользователь не найден"));

        if (user.getRole().stream().noneMatch(role -> role.equals(Role.OPERATOR))) {
            user.getRole().add(Role.OPERATOR);
            user.getRole().remove(Role.USER);
        } else {
            throw new RoleAlreadyExistsException("У пользователя с id = " + id + " уже имеется роль OPERATOR");
        }
        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }
}
