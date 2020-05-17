package server.data.DAOs;

import lombok.*;
import server.data.DTOs.UserShortTO;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserShortDAO {
    private String userID;
    private String username;
    private String IPAddress;

    public static UserShortDAO map(UserShortTO userShortTO) {
        return UserShortDAO.builder()
                .username(userShortTO.getUsername())
                .userID(userShortTO.getUserID())
                .IPAddress(userShortTO.getIPAddress())
                .build();
    }
}
