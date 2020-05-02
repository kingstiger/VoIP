package server.services;

import org.springframework.stereotype.Service;
import server.data.DAOs.UserDAO;
import server.data.DTOs.LoginForm;
import server.data.DTOs.RegistrationForm;
import server.data.DTOs.UserTO;
import server.repositories.UsersRepository;
import server.utility.EmailUtility;
import server.utility.exceptions.CannotConfirmEmailException;
import server.utility.exceptions.CannotLogInException;
import server.utility.exceptions.CannotRegisterException;
import server.utility.exceptions.NoSuchUserException;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserTO getUserByUsername(String username) {
        UserDAO userDAO = usersRepository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::new);

        return UserTO.map(userDAO);
    }

    public UserTO tryToLogIn(LoginForm loginForm) {
        UserDAO userDAO = usersRepository.findByUsername(loginForm.getUsername())
                .orElseThrow(() -> new CannotLogInException("Invalid username!"));

        if (loginForm.getPassword().equals(userDAO.getPassword())) {
            updateUserIP(userDAO, loginForm);
            return UserTO.map(userDAO);
        }
        throw new CannotLogInException("Invalid password!");
    }

    private void updateUserIP(UserDAO userDAO, LoginForm loginForm) {
        userDAO.setIPAddress(loginForm.getIPAddress());
        usersRepository.save(userDAO);
    }

    public UserTO tryToRegister(RegistrationForm registrationForm) {
        usersRepository.findByUsername(registrationForm.getUsername())
                .ifPresent((e) -> {
                    throw new CannotRegisterException("Username " + e.getUsername() + " taken, try different username!");
                });
        usersRepository.findByEmail(registrationForm.getEmail())
                .ifPresent((e) -> {
                    throw new CannotRegisterException("Email " + e.getEmail() + " already taken, try different email!");
                });

        UserDAO savedUser = registerNewUser(registrationForm);
        EmailUtility.sendConfirmationEmail(registrationForm, savedUser.get_id().toString());
        return UserTO.map(savedUser);
    }


    public boolean tryToConfirmEmail(String ID) {
        try {
            UserDAO userDAO = usersRepository.findById(ID)
                    .orElseThrow(CannotConfirmEmailException::new);
            userDAO.setEmailValidated(true);
            usersRepository.save(userDAO);
        } catch (CannotConfirmEmailException ex) {
            return false;
        }
        return true;
    }

    private UserDAO registerNewUser(RegistrationForm registrationForm) {
        return usersRepository.save(UserDAO.builder()
                .email(registrationForm.getEmail())
                .password(registrationForm.getPassword())
                .username(registrationForm.getUsername())
                .IPAddress(registrationForm.getIPAddress())
                .build()
        );
    }
}
