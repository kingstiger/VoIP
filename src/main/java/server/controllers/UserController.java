package server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.DTOs.LoginForm;
import server.data.DTOs.RegistrationForm;
import server.data.DTOs.UserFavouritesTO;
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

    @GetMapping(value = "/favourites")
    public ResponseEntity<UserFavouritesTO> getFavouritesOfUser(@RequestParam("userID") String userID) {
        UserFavouritesTO favouritesOfUser = userService.getFavouritesOfUser(userID);

        return ResponseEntity.ok(favouritesOfUser);
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
        if (Validator.isRegistrationFormValid(registrationForm)) {
            return ResponseEntity.ok(userService.tryToRegister(registrationForm));
        }
        throw new WrongFormatException("Invalid data format!");
    }

    @PostMapping(value = "/favourites")
    public ResponseEntity<UserFavouritesTO> addUserToFavourites(
            @RequestParam("userID") String userID,
            @RequestParam("favUsername") String favUsername) {
        UserFavouritesTO userFavouritesTO = userService.addToFavourites(userID, favUsername);

        return ResponseEntity.ok(userFavouritesTO);
    }

    @DeleteMapping(value = "/favourites")
    public ResponseEntity<UserFavouritesTO> removeUserFromFavourites(
            @RequestParam("userID") String userID,
            @RequestParam("favUsername") String favUserID) {
        UserFavouritesTO userFavouritesTO = userService.addToFavourites(userID, favUserID);

        return ResponseEntity.ok(userFavouritesTO);
    }
}
