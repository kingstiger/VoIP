package com.rest_providers;

import com.models.CurrentConversationTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
public class ConversationProvider {
    final static String url = "https://server-voip.herokuapp.com";
    private static RestTemplate restTemplate = RestTemplateConfiguration.restTemplate();

    public static CurrentConversationTO getCurrentConversation(String currentConversationID, String userID, String token) {
        String current = url + "/call/currentConversation";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(current)
                .queryParam("userID", userID)
                .queryParam("conversationID", currentConversationID);

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<CurrentConversationTO> entity = new HttpEntity<>(headers);

        return Objects.requireNonNull(
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        CurrentConversationTO.class
                ).getBody()
        );
    }

    public static void hangUp(String conversationID, String userID, String token) {
        String current = url + "/call/hangUp";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(current)
                .queryParam("userID", userID)
                .queryParam("conversationID", conversationID);

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.postForLocation(
                builder.toUriString(),
                HttpMethod.GET,
                entity
        );
    }
}
