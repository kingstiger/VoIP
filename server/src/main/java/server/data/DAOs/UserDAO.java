package server.data.DAOs;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Users")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDAO {

    @Id
    private String userID;

    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;

    private List<UserShortDAO> favourites;
}
