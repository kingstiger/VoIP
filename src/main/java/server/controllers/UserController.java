package server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.DTOs.LoginForm;
import server.data.DTOs.RegistrationForm;
import server.data.DTOs.UserTO;
import server.services.UserService;
import server.utility.Validator;
import server.utility.exceptions.WrongFormatException;

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
    public ResponseEntity<UserTO> getUserByUsername(@PathVariable("username") String username) {

        if (Validator.isUsernameValid(username)) {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        }
        throw new WrongFormatException("Invalid username!");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserTO> tryToLogin(@RequestBody LoginForm loginForm) {
        if (Validator.isLoginFormValid(loginForm)) {
            return ResponseEntity.ok(userService.tryToLogIn(loginForm));
        }
        throw new WrongFormatException("Invalid username and/or password!");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserTO> tryToRegister(@RequestBody RegistrationForm registrationForm) {
        if (Validator.isRegistrationFromValid(registrationForm)) {
            return ResponseEntity.ok(userService.tryToRegister(registrationForm));
        }
        throw new WrongFormatException("Invalid data format!");
    }
}
