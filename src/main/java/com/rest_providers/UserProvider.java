package com.rest_providers;

import com.models.LoginForm;
import com.models.RegistrationForm;
import com.models.UserTO;

import java.util.List;

public interface UserProvider {
    UserTO register(RegistrationForm registerForm);

    UserTO login(LoginForm loginForm);

    List<UserTO> getAllUsers();
}
