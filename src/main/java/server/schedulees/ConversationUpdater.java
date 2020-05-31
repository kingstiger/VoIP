package server.schedulees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.DAOs.ConversationDAO;
import server.repositories.ConversationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableScheduling
@Component
public class ConversationUpdater {

    @Autowired
    private ConversationRepository conversationRepository;

    @Scheduled(fixedRate = 15000) //15s
    public void updateConversations() {
        List<ConversationDAO> allByIsOngoing = conversationRepository.findAllByIsOngoing(true);
        Stream<ConversationDAO> stream = allByIsOngoing
                .stream()
                .filter(e -> e.getCurrentParticipants() != null)
                .peek(e -> e.setIsOngoing(!e.getCurrentParticipants().isEmpty() || e.getCurrentParticipants() == null))
                .peek(e -> {
                    if (e.getCurrentParticipants() != null && !e.getCurrentParticipants().isEmpty()) {
                        e.getCurrentParticipants()
                                .entrySet()
                                .removeIf((p) -> p.getValue() < System.currentTimeMillis());
                    }
                })
                .peek(e -> {
                    if (e.getCurrentParticipants().isEmpty()) {
                        e.setIsOngoing(false);
                        e.setEnded(System.currentTimeMillis());
                    }
                });

        if (stream.anyMatch(Objects::nonNull)) {
            conversationRepository.saveAll(stream.collect(Collectors.toList()));
        }
    }
}
