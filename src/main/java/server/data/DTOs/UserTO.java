package server.data.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import server.data.DAOs.UserShortDAO;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class UserTO {
    private String username;
    private String email;
    private List<UserShortDAO> favourites;
    private boolean isValid;
}
