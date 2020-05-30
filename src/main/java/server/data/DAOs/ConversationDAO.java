package server.data.DAOs;

import lombok.*;
import org.bson.types.ObjectId;
import org.mockito.internal.util.collections.Sets;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "Conversations")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ConversationDAO {
    @Id
    private ObjectId _id;
    private Long began;
    private Long ended;
    private Boolean isOngoing;
    private Set<UserShortDAO> participants;
    private Set<UserShortDAO> currentParticipants;
    private String key;

    public static ConversationDAO createStartedWith(String key, UserShortDAO userShortDAO) {
        return ConversationDAO.builder()
                .began(System.currentTimeMillis())
                .isOngoing(true)
                .key(key)
                .participants(Sets.newSet(userShortDAO))
                .build();
    }

    public boolean isParticipating(String username) {
        return participants.stream().anyMatch((e) -> e.getUsername().equals(username));
    }
}
