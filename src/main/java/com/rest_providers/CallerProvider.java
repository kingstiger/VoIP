package com.rest_providers;

import com.models.UserTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallerProvider {
    private String port = ":8080/internal";

    private RestTemplate restTemplate = RestTemplateConfiguration.restTemplate();

    public void callTo(UserTO user) {
        String url = "http://" + user.getIPAddress() + port + "/call";
        String response = restTemplate.postForObject(url, user, String.class);

        System.out.println(response);
    }
}
