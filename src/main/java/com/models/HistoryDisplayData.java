package com.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class HistoryDisplayData {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private String began;
    private String ended;
    private String participants;

    public static HistoryDisplayData map(CurrentConversationTO currentConversationTO) {
        return HistoryDisplayData.builder()
                .began(sdf.format(new Date(currentConversationTO.getBegan())))
                .ended(sdf.format(new Date(currentConversationTO.getEnded())))
                .participants(currentConversationTO.getParticipants()
                        .stream()
                        .map(UserShortDAO::getUsername)
                        .reduce((allPrevious, next) -> allPrevious.concat("\n").concat(next))
                        .orElse("")
                ).build();
    }
}
