package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {
    private String userID;

    private boolean active = false;
    private boolean favourite = false;

    private String username;
    private String email;
    private List<UserShortDAO> favourites;
    @JsonProperty("ipaddress")
    private String IPAddress;

    public UserShortTO map() {
        return UserShortTO.builder()
                    .userID(getUserID())
                     .username(getUsername())
                     .favourite(isFavourite())
                     .IPAddress(getIPAddress())
                     .build();
    }
}
