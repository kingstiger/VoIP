package com.rest_providers;

import com.models.LoginForm;
import com.models.RegistrationForm;
import com.models.UserTO;
import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserProviderImpl extends RestTemplateConfiguration implements UserProvider {
    final String url = "https://server-voip.herokuapp.com//users/register";

    @Autowired
    private RestTemplate restTemplate;

    public void register(RegistrationForm registerForm) {
        new Thread(() -> Platform.runLater(() -> {
            try {
                UserTO user = restTemplate.postForObject(url, registerForm, UserTO.class);
                controllers.AlertController.showAlert(String.format("User %s registered successfully",
                                                                    user.getUsername()),
                                                      null,
                                                      "Now you can use application!");
            } catch (Exception e) {
                controllers.AlertController.showAlert("Failed to registered!",
                                                      null,
                                                      "Try to use another user or email.");
            }
        })).start();
    }

    public void login(LoginForm loginForm) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void obtainAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
