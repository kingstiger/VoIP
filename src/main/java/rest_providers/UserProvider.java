package rest_providers;

import com.models.UserShortDAO;

public interface UserProvider {
    void register(UserShortDAO registerForm);

    void login(Object loginForm);

    void obtainAll();
}
