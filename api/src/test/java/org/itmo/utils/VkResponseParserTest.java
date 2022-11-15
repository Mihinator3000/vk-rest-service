package org.itmo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itmo.dto.UserMembershipDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VkResponseParserTest {

    private VkResponseParser vkResponseParser;

    @BeforeEach()
    public void setup() {
        vkResponseParser =  new VkResponseParser(new ObjectMapper());
    }

    @Test
    public void parseValidResponse() {
        String userGetResponse = "{\"response\":[{\"id\":203639406,\"nickname\":\"\",\"first_name\":\"Mikhail\",\"last_name\":\"Koshkin\",\"can_access_closed\":true,\"is_closed\":false}]}";
        String groupIsMemberResponse = "{\"response\":1}";

        UserMembershipDto actualUserMembershipDto = vkResponseParser.parse(userGetResponse, groupIsMemberResponse);

        assertTrue(actualUserMembershipDto.isMember());
        assertEquals("Mikhail", actualUserMembershipDto.getFirstName());
        assertEquals("Koshkin", actualUserMembershipDto.getLastName());
    }
}
