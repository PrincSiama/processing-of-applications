package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.model.User;
import dev.sosnovsky.applications.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findAllByPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException(
                "Пользователь с номером телефона " + username + " не найден"));
        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                user.getRole().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet())
        );
    }
}
