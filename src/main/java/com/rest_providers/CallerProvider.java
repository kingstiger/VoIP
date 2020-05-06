package com.rest_providers;

import com.models.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallerProvider extends RestTemplateConfiguration {
    private String port = ":8080/internal";

    @Autowired
    private RestTemplate restTemplate2;

    public void callTo(UserTO user) {
        String url = user.getIPAddress() + port + "/call";
        String response = restTemplate2.postForObject(url, user, String.class);

        System.out.println(response);
    }
}
