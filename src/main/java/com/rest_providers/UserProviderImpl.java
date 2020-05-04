package com.rest_providers;

import com.models.UserShortDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rest_providers.UserProvider;

@Component
public class UserProviderImpl extends RestTemplateConfiguration implements UserProvider {
    final String url = "127.0.0.1:8080/users/register";

    @Autowired
    private RestTemplate restTemplate;

    public void register(UserShortDAO registerForm) {
        new Thread(() -> {
            String xd = restTemplate.postForObject(url, registerForm, String.class);
            System.out.println(xd);

        }).start();
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void login(Object loginForm) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void obtainAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
