package server.data.DTOs;

import lombok.*;
import server.data.DAOs.ConversationDAO;
import server.data.DAOs.UserShortDAO;

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

    public static CurrentConversationTO map(ConversationDAO conversationDAO) {
        return CurrentConversationTO.builder()
                .conversationID(conversationDAO.get_id().toString())
                .currentParticipants(conversationDAO.getCurrentParticipants().keySet())
                .began(conversationDAO.getBegan())
                .ended(conversationDAO.getEnded())
                .isOngoing(conversationDAO.getIsOngoing())
                .participants(conversationDAO.getParticipants())
                .build();
    }
}
