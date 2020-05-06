package com.rest_providers;

import com.google.common.collect.Lists;
import com.models.LoginForm;
import com.models.RegistrationForm;
import com.models.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserProviderImpl extends RestTemplateConfiguration implements UserProvider {
    final String url = "https://server-voip.herokuapp.com//users";

    @Autowired
    private RestTemplate restTemplate;

    public UserTO register(RegistrationForm registerForm) {
        String endpointUrl = url + "/register";
        return restTemplate.postForObject(endpointUrl, registerForm, UserTO.class);
    }

    public UserTO login(LoginForm loginForm) {
        String endpointUrl = url + "/login";
        return restTemplate.postForObject(endpointUrl, loginForm, UserTO.class);
    }

    public List<UserTO> getAllUsers() {
        String endpointUrl = url + "/all";
        List<UserTO> users = Lists.newArrayList(restTemplate.getForObject(endpointUrl, UserTO[].class));
        return users;
    }
}
