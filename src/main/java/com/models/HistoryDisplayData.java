package com.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDisplayData {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private String began;
    private String ended;
    private String participants;

    public static HistoryDisplayData map(CurrentConversationTO currentConversationTO) {
        HistoryDisplayDataBuilder participants = HistoryDisplayData.builder()
                .began(sdf.format(new Date(currentConversationTO.getBegan())))
                .participants(currentConversationTO.getParticipants()
                        .stream()
                        .map(UserShortDAO::getUsername)
                        .reduce((allPrevious, next) -> allPrevious.concat("\n").concat(next))
                        .orElse("")
                );

        if (Objects.isNull(currentConversationTO.getEnded())) {
            participants.ended("----");
        }else {
            participants.ended(sdf.format(new Date(currentConversationTO.getEnded())));
        }

        return participants.build();
    }
}
