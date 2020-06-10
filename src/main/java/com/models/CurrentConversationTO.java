package com.models;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class CurrentConversationTO {
    private String conversationID;
    private Long began;
    private Long ended;
    private Boolean isOngoing;
    private Set<UserShortDAO> participants;
    private Set<UserShortDAO> currentParticipants;
}