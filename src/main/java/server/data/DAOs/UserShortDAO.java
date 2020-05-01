package server.data.DAOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserShortDAO {
    private String userID;
    private String username;
    private String email;
    private String IPAddress;
}
