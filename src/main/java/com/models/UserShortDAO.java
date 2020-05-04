package com.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserShortDAO {
    private String userID;
    private String username;
    private String email;
    private String IPAddress;
}
