package rest_providers;

public interface UserProvider {
    void register(Object registerForm);

    void login(Object loginForm);

    void obtainAll();
}
