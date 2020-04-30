package server.services;

import org.springframework.stereotype.Service;
import server.data.DAOs.UserDAO;
import server.data.DTOs.UserTO;
import server.repositories.UsersRepository;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserTO getUserByUsername(String username) {
        UserDAO userDAO = usersRepository
                .findByUsername(username)
                .orElseGet(() -> {
                    throw new NoSuchElementException("No such user!");
                });

        return UserTO.builder()
                .username(userDAO.getUsername())
                .email(userDAO.getEmail())
                .favourites(userDAO.getFavourites())
                .isValid(true)
                .build();
    }
}
