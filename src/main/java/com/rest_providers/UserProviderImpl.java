package com.rest_providers;

import com.models.LoginForm;
import com.models.RegistrationForm;
import com.models.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserProviderImpl extends RestTemplateConfiguration implements UserProvider {
    final String url = "https://server-voip.herokuapp.com//users/register";

    @Autowired
    private RestTemplate restTemplate;

    public UserTO register(RegistrationForm registerForm) {
        return restTemplate.postForObject(url, registerForm, UserTO.class);
    }

    public void login(LoginForm loginForm) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void obtainAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
