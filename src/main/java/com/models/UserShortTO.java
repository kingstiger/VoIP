package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserShortTO {
    private String userID;
    private String username;
    @JsonProperty("ipaddress")
    private String IPAddress;
    @JsonProperty("isFavourite")
    private boolean favourite;
    @JsonProperty("isActive")
    private boolean active;
}