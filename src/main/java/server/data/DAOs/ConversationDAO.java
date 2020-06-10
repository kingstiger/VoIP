package server.data.DAOs;

import lombok.*;
import org.bson.types.ObjectId;
import org.mockito.internal.util.collections.Sets;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
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
    @Builder.Default
    private Long ended = 0L;
    private Boolean isOngoing;
    private Set<UserShortDAO> participants;
    private HashMap<UserShortDAO, Long> currentParticipants;
    private String key;

    public static ConversationDAO createStartedWith(String key, UserShortDAO userShortDAO) {
        ConversationDAO build = ConversationDAO.builder()
                .began(System.currentTimeMillis())
                .isOngoing(true)
                .key(key)
                .participants(Sets.newSet(userShortDAO))
                .build();
        HashMap<UserShortDAO, Long> current = new HashMap<>();
        current.put(userShortDAO, System.currentTimeMillis());
        build.setCurrentParticipants(current);
        return build;
    }

    public boolean isParticipating(String username) {
        return participants.stream().anyMatch((e) -> e.getUsername().equals(username));
    }
}
