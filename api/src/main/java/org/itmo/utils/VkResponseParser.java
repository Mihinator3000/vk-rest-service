package org.itmo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserMembershipDto;
import org.itmo.utils.exceptions.VkException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkResponseParser {

    private final ObjectMapper mapper;

    public UserMembershipDto parse(String userGetResponse, String groupIsMemberResponse) {
        var builder = UserMembershipDto.builder();

        JsonNode root = null;
        try {

            System.out.println(groupIsMemberResponse);
            root = readResponse(userGetResponse).get(0);

            String firstName = root.path("first_name").asText();
            if (firstName.equals("DELETED"))
                throw new VkException("User is either not created or deleted");

            builder.firstName(firstName)
                    .middleName(root.path("nickname").asText())
                    .lastName(root.path("last_name").asText());

            root = readResponse(groupIsMemberResponse);

            builder.member(root.asInt() != 0);
        } catch (JsonProcessingException e) {
            parseErrorResponse(root);
        }

        return builder.build();
    }

    private JsonNode readResponse(String responseTree) throws JsonProcessingException {
        return mapper.readTree(responseTree).path("response").require();
    }

    private void parseErrorResponse(JsonNode root) {
        throw new VkException(root.path("error").path("error_msg").asText());
    }
}
