package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dto.UserDto;
import dev.sosnovsky.applications.exception.NotFoundException;
import dev.sosnovsky.applications.exception.RoleAlreadyExistsException;
import dev.sosnovsky.applications.model.Role;
import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public List<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto setOperatorRole(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Невозможно установить роль оператора пользователю с id = " + id + ". Пользователь не найден"));
        /*if (!user.getRole().contains(Role.OPERATOR)) {
            user.getRole().add(Role.OPERATOR);
            user.getRole().remove(Role.USER);
        } else {
            throw new RoleAlreadyExistsException("У пользователя с id = " + id + " уже имеется роль " + Role.OPERATOR);
        }*/
        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }
}
