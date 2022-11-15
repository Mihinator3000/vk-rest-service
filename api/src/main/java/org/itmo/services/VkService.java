package org.itmo.services;

import lombok.RequiredArgsConstructor;
import org.apache.camel.FluentProducerTemplate;
import org.itmo.dto.UserGroupDto;
import org.itmo.dto.UserMembershipDto;
import org.itmo.utils.VkResponseParser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VkService {

    private final FluentProducerTemplate producerTemplate;
    private final VkResponseParser responseParser;

    public UserMembershipDto userMembership(String vkServiceToken, UserGroupDto userGroupDto) {
        String userGetResponse = producerTemplate
                .to("direct:user_get")
                .withHeader("access_token", vkServiceToken)
                .withHeader("user_id", userGroupDto.getUserId())
                .request(String.class);

        String groupIsMemberResponse = producerTemplate
                .to("direct:group_is_member")
                .withHeader("access_token", vkServiceToken)
                .withHeader("user_id", userGroupDto.getUserId())
                .withHeader("group_id", userGroupDto.getGroupId())
                .request(String.class);

        return responseParser.parse(userGetResponse, groupIsMemberResponse);
    }
}
