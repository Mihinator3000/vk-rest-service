package org.itmo.utils;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageRoute extends RouteBuilder {

    private static final String VK_API = "https://api.vk.com/method/";

    @Override
    public void configure() {
        from("direct:user_get")
                .setHeader(Exchange.HTTP_QUERY, simple("user_ids=${header.user_id}&fields=nickname&access_token=${header.access_token}&v=5.131"))
                .to(VK_API + "users.get");

        from("direct:group_is_member")
                .setHeader(Exchange.HTTP_QUERY, simple("user_id=${header.user_id}&group_id=${header.group_id}&access_token=${header.access_token}&v=5.131"))
                .to(VK_API + "groups.isMember");
    }
}
