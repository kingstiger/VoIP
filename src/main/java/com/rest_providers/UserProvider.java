package com.rest_providers;

import com.models.LoginForm;
import com.models.RegistrationForm;

public interface UserProvider {
    void register(RegistrationForm registerForm);

    void login(LoginForm loginForm);

    void obtainAll();
}
