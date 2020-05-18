package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import server.data.DAOs.ConversationDAO;
import server.data.DAOs.UserDAO;
import server.data.DAOs.UserShortDAO;
import server.data.DTOs.ConversationTO;
import server.data.DTOs.DHRequestTO;
import server.repositories.ConversationRepository;
import server.repositories.UsersRepository;
import server.utility.exceptions.DHException;
import server.utility.exceptions.NoSuchUserException;

import java.util.Set;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private DHService dhService;
    @Autowired
    private UsersRepository usersRepository;

    public ConversationTO handleConversationRequest(String userID, DHRequestTO dhRequestTO) {
        UserDAO userDAO = usersRepository.findById(userID)
                .orElseThrow(NoSuchUserException::new);

        Pair<ConversationTO, Long> conversationTOSecretPair = dhService.handleCrypto(dhRequestTO, true);
        ConversationTO conversationTO = conversationTOSecretPair.getFirst();

        ConversationDAO saved = conversationRepository.save(ConversationDAO
                .createStartedWith(
                        conversationTO.getKey(),
                        UserShortDAO.map(userDAO)
                )
        );
        conversationTO.setID(saved.get_id().toString());
        conversationTO.encryptKey(() ->
                dhService.encryptKey(
                        conversationTOSecretPair.getSecond().toString(),
                        conversationTO.getKey())
        );

        return conversationTO;
    }

    public ConversationTO handleAddToConversationRequest(String userID, DHRequestTO dhRequestTO) {
        UserDAO userDAO = usersRepository.findById(userID)
                .orElseThrow(NoSuchUserException::new);

        ConversationDAO conversationDAO = conversationRepository.findById(dhRequestTO.getID())
                .orElseThrow(DHException::new);

        Pair<ConversationTO, Long> conversationTOSecretPair = dhService.handleCrypto(dhRequestTO, false);
        ConversationTO conversationTO = conversationTOSecretPair.getFirst();

        addToParticipants(userDAO, conversationDAO);

        conversationTO.encryptKey(() ->
                dhService.encryptKey(
                        conversationTOSecretPair.getSecond().toString(),
                        conversationTO.getKey())
        );

        return conversationTO;
    }

    private void addToParticipants(UserDAO userDAO, ConversationDAO conversationDAO) {
        Set<UserShortDAO> participants = conversationDAO.getParticipants();
        participants.add(UserShortDAO.map(userDAO));
        conversationDAO.setParticipants(participants);
        conversationRepository.save(conversationDAO);
    }
}
