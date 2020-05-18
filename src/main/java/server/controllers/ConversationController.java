package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.DTOs.ConversationTO;
import server.data.DTOs.DHRequestTO;
import server.services.ConversationService;
import server.services.SecurityService;
import server.services.UserService;

@RestController
@RequestMapping("/call")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    /*
    Someone who wants to begin conversation with someone else starts here DH (gives p, g, A and list of people who he wants to call)
    */
    @PostMapping(value = "/calling")
    public ResponseEntity<ConversationTO> startConversation(
            @RequestParam("userID") String userID,
            @RequestHeader("token") String token,
            @RequestBody DHRequestTO dhRequestTO
    ) {
        if (securityService.isTokenValid(userID, token)) {
            ConversationTO conversationTO = conversationService.handleConversationRequest(userID, dhRequestTO);
            return ResponseEntity.ok(conversationTO);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/beingCalled")
    public ResponseEntity<ConversationTO> addToConversation(
            @RequestParam("userID") String userID,
            @RequestHeader("token") String token,
            @RequestBody DHRequestTO dhRequestTO
    ) {
        if (securityService.isTokenValid(userID, token)) {
            ConversationTO conversationTO = conversationService.handleAddToConversationRequest(userID, dhRequestTO);
            return ResponseEntity.ok(conversationTO);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
