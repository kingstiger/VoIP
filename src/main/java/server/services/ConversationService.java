package server.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.configuration.RestTemplateConfiguration;
import server.data.DAOs.ConversationDAO;
import server.data.DAOs.UserDAO;
import server.data.DAOs.UserShortDAO;
import server.data.DTOs.CallRequestTO;
import server.data.DTOs.ConversationTO;
import server.data.DTOs.DHRequestTO;
import server.data.DTOs.UserShortTO;
import server.repositories.ConversationRepository;
import server.repositories.UsersRepository;
import server.utility.exceptions.DHException;
import server.utility.exceptions.NoSuchUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private DHService dhService;
    @Autowired
    private UsersRepository usersRepository;

    private RestTemplate restTemplate = RestTemplateConfiguration.restTemplate();
    private String port = ":8080";

    public ConversationTO handleConversationRequest(String userID, DHRequestTO dhRequestTO) {
        String callerUsername = usersRepository.findById(userID)
                .orElseThrow(NoSuchUserException::new).getUsername();

        if (dhRequestTO.getWhoIWantToCall() != null) {
            return handleCaller(dhRequestTO, callerUsername);

        } else {
            return handleCalled(dhRequestTO);
        }
    }

    public void handleAddToConversationRequest(String userID, DHRequestTO dhRequestTO) {
        String callerUsername = usersRepository.findById(userID)
                .orElseThrow(NoSuchUserException::new).getUsername();

        handleAdder(dhRequestTO, callerUsername);
    }

    private ConversationTO handleAdded(DHRequestTO dhRequestTO) {
        return null;
    }

    private void handleAdder(DHRequestTO dhRequestTO, String callerUsername) {
        ConversationDAO conversationDAO = conversationRepository.findById(dhRequestTO.getID())
                .orElseThrow(DHException::new);

        List<UserShortTO> whoWillBePresent = conversationDAO.getParticipants()
                .stream()
                .map(UserShortTO::map)
                .collect(Collectors.toList());

        for (String username : dhRequestTO.getWhoIWantToCall()) {
            if (!conversationDAO.isParticipating(username)) {
                Optional<UserDAO> called = usersRepository.findByUsername(username);
                if (called.isPresent()) {
                    UserDAO userDAO = called.get();
                    Boolean isAnswering = tellUserAboutCall(userDAO, conversationDAO.get_id().toString(), callerUsername);
                    if (isAnswering) {
                        whoWillBePresent.add(UserShortTO.map(userDAO));
                    }
                }
            }
        }
        conversationDAO.setParticipants(
                whoWillBePresent
                        .stream()
                        .map(UserShortDAO::map)
                        .collect(Collectors.toSet())
        );
        conversationRepository.save(conversationDAO);
    }

    @NotNull
    private ConversationTO handleCalled(DHRequestTO dhRequestTO) {
        ConversationTO conversationTO;
        Pair<ConversationTO, Long> conversationTOSecretPair = dhService.handleCrypto(dhRequestTO, false);
        conversationTO = conversationTOSecretPair.getFirst();
        conversationTO.encryptKey(() ->
                dhService.encryptKey(
                        conversationTOSecretPair.getSecond().toString(),
                        conversationTO.getKey())
        );
        return conversationTO;
    }

    @NotNull
    private ConversationTO handleCaller(DHRequestTO dhRequestTO, String callerUsername) {
        ConversationTO conversationTO;
        List<String> whoIWantToCall = dhRequestTO.getWhoIWantToCall();
        List<UserShortTO> whoWillBePresent;

        Pair<ConversationTO, Long> conversationTOSecretPair = dhService.handleCrypto(dhRequestTO, true);
        conversationTO = conversationTOSecretPair.getFirst();

        ConversationDAO saved = conversationRepository.save(ConversationDAO.createStartedWith(conversationTO.getKey()));

        whoWillBePresent = handleDHWithOtherUsers(whoIWantToCall, saved.get_id().toString(), callerUsername);

        conversationTO.setMembers(whoWillBePresent);
        conversationTO.setID(saved.get_id().toString());
        conversationTO.encryptKey(() ->
                dhService.encryptKey(
                        conversationTOSecretPair.getSecond().toString(),
                        conversationTO.getKey())
        );
        return conversationTO;
    }

    @NotNull
    private List<UserShortTO> handleDHWithOtherUsers(List<String> whoIWantToCall, String conversationID, String callerUsername) {
        List<UserShortTO> whoWillBePresent = new ArrayList<>();

        for (String username : whoIWantToCall) {
            Optional<UserDAO> member = usersRepository.findByUsername(username);
            if (member.isPresent()) {
                UserDAO userDAO = member.get();
                Boolean isAnswering = tellUserAboutCall(userDAO, conversationID, callerUsername);
                if (isAnswering) {
                    whoWillBePresent.add(UserShortTO.map(userDAO));
                }
            }
        }
        return whoWillBePresent;
    }

    private Boolean tellUserAboutCall(UserDAO calledDAO, String conversationID, String callerUsername) {
        HttpEntity<CallRequestTO> entity = new HttpEntity<>(
                CallRequestTO.builder()
                        .caller(callerUsername)
                        .ID(conversationID)
                        .build()
        );

        String url = "http://" + calledDAO.getIPAddress() + port + "/dhSession";
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(url, null, Boolean.class, entity);

        return responseEntity.getBody();
    }
}
