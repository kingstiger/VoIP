package rest_providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class UserProviderImpl implements UserProvider {
    final String url = "127.0.0.1:8080/users/register";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void register(Object registerForm) {
        String xd = restTemplate.postForObject(url, registerForm, String.class);
        System.out.println(xd);
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void login(Object loginForm) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void obtainAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
