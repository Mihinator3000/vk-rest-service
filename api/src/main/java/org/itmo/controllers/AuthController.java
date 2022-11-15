package org.itmo.controllers;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserDto;
import org.itmo.services.UserService;
import org.itmo.utils.UserConverter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final UserConverter converter;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody UserDto user) {
        service.save(converter.toModel(user));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@RequestBody UserDto user) {
        service.save(converter.toModel(user));
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        if (service.isCurrentUserNotAnAdmin() && service.getCurrentId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.delete(id);
    }
}
