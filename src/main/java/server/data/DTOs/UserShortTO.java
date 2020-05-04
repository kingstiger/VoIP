package server.data.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserShortTO {
    private String UserID;
    private String username;
    private boolean isFavourite;
}
