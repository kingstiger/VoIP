package com.rest_providers;

import com.google.common.collect.Lists;
import com.models.UserTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserProviderImpl {
    final String url = "https://server-voip.herokuapp.com/users";
    private final RestTemplate restTemplate = RestTemplateConfiguration.restTemplate();

    public List<UserTO> getAllUsers(String token) {
        String endpointUrl = url + "/all";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<UserTO> users = Lists.newArrayList(restTemplate.exchange(endpointUrl,
                                                                      HttpMethod.GET,
                                                                      entity,
                                                                      UserTO[].class)
                                                            .getBody());
        return users;
    }
}
