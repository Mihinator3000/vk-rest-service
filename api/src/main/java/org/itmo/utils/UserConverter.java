package org.itmo.utils;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserDto;
import org.itmo.models.User;
import org.itmo.services.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User toModel(UserDto user) {
        var builder = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() != null)
            builder.roles(user.getRoles()
                .stream()
                .map(roleService::getByName)
                .collect(Collectors.toList()));

        return builder.build();
    }
}
