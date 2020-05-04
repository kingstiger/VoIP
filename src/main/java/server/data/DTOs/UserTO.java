package server.data.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import server.data.DAOs.UserDAO;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class UserTO {
    private String username;
    private String email;
    private List<String> favourites;
    private String IPAddress;

    public static UserTO map(UserDAO userDAO) {
        return UserTO.builder()
                .username(userDAO.getUsername())
                .email(userDAO.getEmail())
                .favourites(userDAO.getFavourites())
                .IPAddress(userDAO.getIPAddress())
                .build();
    }
}
