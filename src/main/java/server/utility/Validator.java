package server.utility;

import server.data.DTOs.LoginForm;

public class Validator {
    public static boolean isUsernameValid(String username) {
        return username.matches("[a-zA-Z0-9]+") && !username.isEmpty();
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("[!@#$%^&*()/\"\\\\|;:'<>,.?]+")
                && password.matches("[A-Z]+")
                && password.matches("[a-z]+")
                && password.matches("[0-9]+");
    }
    public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    public static boolean isLoginFormValid(LoginForm loginForm) {
        return Validator.isPasswordValid(loginForm.getPassword())
                && Validator.isUsernameValid(loginForm.getUsername());
    }
}
