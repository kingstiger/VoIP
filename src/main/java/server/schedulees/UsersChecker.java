package server.schedulees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.data.DAOs.ConversationDAO;
import server.repositories.ConversationRepository;
import server.utility.TokenServiceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableScheduling
@Component
public class UsersChecker {

    @Scheduled(fixedRate = 100, initialDelay = 200) //15s
    public void updateUsers() {
        HashMap<String, Pair<String, Long>> tokensWithUserIDsAndExpires = TokenServiceUtils.tokensWithUserIDsAndExpires;
        for (Map.Entry<String, Pair<String, Long>> userIDTokenExpires : tokensWithUserIDsAndExpires.entrySet()) {
            if(userIDTokenExpires.getValue().getSecond() > System.currentTimeMillis()) {
                TokenServiceUtils.usersActive.put(userIDTokenExpires.getKey(), true);
            } else {
                TokenServiceUtils.usersActive.put(userIDTokenExpires.getKey(), false);
            }
        }
    }
}
