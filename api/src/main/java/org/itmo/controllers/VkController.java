package org.itmo.controllers;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserGroupDto;
import org.itmo.dto.UserMembershipDto;
import org.itmo.services.VkService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/vk")
@RequiredArgsConstructor
public class VkController {

    private final VkService service;

    @GetMapping("/user-membership")
    public UserMembershipDto userMembership(
            @RequestHeader("vk_service_token") @Size(min = 1, max = 100) String vkServiceToken,
            @RequestBody @Valid UserGroupDto userGroupDto) {
        return service.userMembership(vkServiceToken, userGroupDto);
    }
}
