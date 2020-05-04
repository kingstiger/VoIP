package com.rest_providers;

import com.models.LoginForm;
import com.models.RegistrationForm;
import com.models.UserTO;

public interface UserProvider {
    UserTO register(RegistrationForm registerForm);

    void login(LoginForm loginForm);

    void obtainAll();
}
