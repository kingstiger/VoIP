package com.models;

import com.sun.corba.se.spi.ior.ObjectId;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDAO {
    private ObjectId _id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;

    @Builder.Default
    private boolean isEmailValidated = false;
    @Builder.Default
    private List<UserShortDAO> favourites = new ArrayList<>();
    private String IPAddress;
}
