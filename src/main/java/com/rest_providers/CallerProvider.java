package com.rest_providers;

import com.models.MessageTO;
import com.models.UserTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallerProvider {
    private String port = ":8080/internal";

    private RestTemplate restTemplate = RestTemplateConfiguration.restTemplate();

    public MessageTO callTo(UserTO user) {
        String url = "http://" + user.getIPAddress() + port + "/call";
        return restTemplate.postForObject(url, user, MessageTO.class);
    }
}
