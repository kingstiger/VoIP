package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.DTOs.UserFavouritesTO;
import server.data.DTOs.UserShortTO;
import server.services.SecurityService;
import server.services.UserService;
import server.utility.Validator;
import server.utility.exceptions.WrongFormatException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    //debug only
    @GetMapping(value = "/test")
    public boolean test() {
        return true;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllUsers(
            @RequestParam("userID") String userID,
            @RequestHeader("token") String token) {
        if (securityService.isTokenValid(userID, token)) {
            List<UserShortTO> allUsers = userService.getAllUsers(userID);
            return ResponseEntity.ok(allUsers);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getMeXD(
            @RequestParam("userID") String userID,
            @RequestHeader("token") String token) {
        if (securityService.isTokenValid(userID, token)) {
            return ResponseEntity.ok(userService.getUser(userID));
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    //debug only
    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        if (Validator.isUsernameValid(username)) {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        }
        throw new WrongFormatException("Invalid username!");
    }

    @GetMapping(value = "/favourites")
    public ResponseEntity<?> getFavouritesOfUser(@RequestParam("userID") String userID,
                                                 @RequestHeader("token") String token) {
        if (securityService.isTokenValid(userID, token)) {
            UserFavouritesTO favouritesOfUser = userService.getFavouritesOfUser(userID);
            return ResponseEntity.ok(favouritesOfUser);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/favourites")
    public ResponseEntity<?> addUserToFavourites(
            @RequestParam("userID") String userID,
            @RequestParam("favUsername") String favUsername,
            @RequestHeader("token") String token) {
        if (securityService.isTokenValid(userID, token)) {
            UserFavouritesTO userFavouritesTO = userService.addToFavourites(userID, favUsername);
            return ResponseEntity.ok(userFavouritesTO);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/favourites")
    public ResponseEntity<?> removeUserFromFavourites(
            @RequestParam("userID") String userID,
            @RequestParam("favUsername") String favUsername,
            @RequestHeader("token") String token) {
        if (securityService.isTokenValid(userID, token)) {
            UserFavouritesTO userFavouritesTO = userService.deleteFavourite(userID, favUsername);
            return ResponseEntity.ok(userFavouritesTO);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    //debug only
    @DeleteMapping(value = "/all")
    public ResponseEntity<?> removeAllUsers() {
        userService.deleteAllUsers();

        return ResponseEntity.ok("All users deleted");
    }

    @DeleteMapping(value = "/{userID}")
    public ResponseEntity<?> removeUser(@PathVariable String userID,
                                        @RequestHeader("token") String token) {
        if (securityService.isTokenValid(userID, token)) {
            userService.deleteUser(userID);
            return ResponseEntity.ok("Deleted user");
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/favourites/all")
    public ResponseEntity<?> deleteAllFavouritesOfUser(@RequestParam("userID") String userID,
                                                       @RequestHeader("token") String token) {
        userService.deleteAllFavouritesOfUser(userID);

        return ResponseEntity.ok("Deleted all favourites");
    }
}
