package org.itmo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.FluentProducerTemplate;
import org.itmo.dto.UserGroupDto;
import org.itmo.utils.VkResponseParser;
import org.itmo.utils.exceptions.VkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VkServiceTest {

    private VkService service;
    private FluentProducerTemplate producerTemplate;

    @BeforeEach()
    public void setup() {
        producerTemplate = mock(FluentProducerTemplate.class);
        service = new VkService(producerTemplate, new VkResponseParser(new ObjectMapper()));
        when(producerTemplate.to(anyString())).thenReturn(producerTemplate);
        when(producerTemplate.withHeader(anyString(), any())).thenReturn(producerTemplate);
    }

    @Test
    public void vkInvalidResponseThrowException() {
        when(producerTemplate.request(String.class)).thenReturn("Invalid response");

        assertThrows(NullPointerException.class, () -> service.userMembership("token_example", new UserGroupDto(10, 10)));
    }

    @Test
    public void vkErrorResponseThrowException() {
        when(producerTemplate.request(String.class)).thenReturn("{\"error\":{\"error_code\":15,\"error_msg\":\"Access denied: no access to this group\",\"request_params\":[{\"key\":\"user_id\",\"value\":\"203639406\"},{\"key\":\"group_id\",\"value\":\"432423423\"},{\"key\":\"v\",\"value\":\"5.131\"},{\"key\":\"method\",\"value\":\"groups.isMember\"},{\"key\":\"oauth\",\"value\":\"1\"}]}}");

        assertThrows(IllegalArgumentException.class, () -> service.userMembership("token_example", new UserGroupDto(10, 10)));
    }
}
