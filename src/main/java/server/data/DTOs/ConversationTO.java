package server.data.DTOs;

import lombok.*;

import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class ConversationTO {
    private String ID;
    private String key;
    private Long S;
    private List<UserShortTO> members;

    public String encryptKey(Supplier<String> run) {
        key = run.get();
        return key;
    }
}
