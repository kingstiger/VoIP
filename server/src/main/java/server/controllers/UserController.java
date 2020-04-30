package server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.data.DTOs.UserTO;
import server.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/test")
    public boolean test() {
        return true;
    }

    @GetMapping(value = "/{username}")
    public UserTO getUserByUsername(@PathVariable("username") String username) {
        if (!username.matches("[a-zA-Z0-9]+") || username.isEmpty()) {
            return null;
        } else {
            return userService.getUserByUsername(username);
        }
    }
}
