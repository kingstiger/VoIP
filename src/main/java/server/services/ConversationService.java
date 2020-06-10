package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import server.data.DAOs.ConversationDAO;
import server.data.DAOs.UserDAO;
import server.data.DAOs.UserShortDAO;
import server.data.DTOs.ConversationTO;
import server.data.DTOs.CurrentConversationTO;
import server.data.DTOs.DHRequestTO;
import server.repositories.ConversationRepository;
import server.repositories.UsersRepository;
import server.utility.exceptions.DHException;
import server.utility.exceptions.NoSuchUserException;

import java.util.*;
import java.util.stream.Collectors;

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

    public void handleHangUpRequest(String userID, String conversationID) {
        ConversationDAO conversationDAO = conversationRepository.findById(conversationID)
                .orElseThrow(RuntimeException::new);

        HashMap<UserShortDAO, Long> currentParticipants = conversationDAO.getCurrentParticipants();

        if(currentParticipants == null) {
            conversationDAO.setEnded(System.currentTimeMillis());
            conversationDAO.setIsOngoing(false);
            return;
        }

        Optional<UserShortDAO> userShortDAO = currentParticipants
                .keySet()
                .stream()
                .filter(e -> e.getUserID().equals(userID))
                .findFirst();


        userShortDAO.ifPresent(currentParticipants::remove);

        if (currentParticipants.size() < 1) {
            conversationDAO.setEnded(System.currentTimeMillis());
            conversationDAO.setIsOngoing(false);
        } else {
            conversationDAO.setCurrentParticipants(currentParticipants);
        }

        conversationRepository.save(conversationDAO);
    }

    public CurrentConversationTO getCurrentConversation(String conversationID, String userID) {
        ConversationDAO conversationDAO = conversationRepository.findById(conversationID)
                .orElseThrow(RuntimeException::new);

        HashMap<UserShortDAO, Long> currentParticipants = conversationDAO.getCurrentParticipants();

        List<Map.Entry<UserShortDAO, Long>> entryList = currentParticipants.entrySet()
                .stream()
                .filter(e -> e.getValue() < System.currentTimeMillis())
                .collect(Collectors.toList());
        currentParticipants.entrySet().removeIf(entryList::contains);

        long currentTime = System.currentTimeMillis();
        currentParticipants.entrySet()
                .stream()
                .filter(e -> e.getKey().getUserID().equals(userID))
                .peek(e -> e.setValue(currentTime + 20000));

        conversationDAO.setCurrentParticipants(currentParticipants);

        return CurrentConversationTO.map(conversationDAO);
    }

    private void addToParticipants(UserDAO userDAO, ConversationDAO conversationDAO) {
        Set<UserShortDAO> participants = conversationDAO.getParticipants();
        participants.add(UserShortDAO.map(userDAO));
        conversationDAO.setParticipants(participants);
        conversationDAO.getCurrentParticipants().put(UserShortDAO.map(userDAO), System.currentTimeMillis());
        conversationRepository.save(conversationDAO);
    }

    public List<CurrentConversationTO> getAllConversationsOfUser(String userID) {
        return conversationRepository.findAll()
                .stream()
                .filter((e) -> e.getParticipants()
                        .stream()
                        .anyMatch(userShortDAO -> userShortDAO
                                .getUserID()
                                .equals(userID)
                        )
                )
                .map(CurrentConversationTO::map)
                .collect(Collectors.toList());
    }
}
