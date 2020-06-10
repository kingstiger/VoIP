package server.utility;

import com.google.common.hash.Hashing;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import server.data.DAOs.SecurityInfoDAO;
import server.services.SecurityService;
import server.utility.exceptions.CannotRenewTokenException;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

public class TokenServiceUtils {
    @Autowired
    private static SecurityService securityService;

    public static HashMap<String, Pair<String, Long>> tokensWithUserIDsAndExpires = new HashMap<>();

    public static SecurityInfoDAO getNewToken(String userID) {
        long expires = System.currentTimeMillis() + 90 * 60 * 1000;
        String tokenString = getTokenString();
        tokensWithUserIDsAndExpires.put(userID, Pair.of(tokenString, expires));
        return SecurityInfoDAO.builder()
                .token(tokenString)
                .expires(expires)
                .userID(userID)
                ._id(new ObjectId())
                .username("username")
                .build();
    }


    private static String getTokenString() {
        String originalString = String.valueOf(System.currentTimeMillis());

        return Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }

    public static SecurityInfoDAO renewToken(String userID, String token) {
        Long expires = 0L;
        if(tokensWithUserIDsAndExpires.containsKey(userID)) {
            if(tokensWithUserIDsAndExpires.get(userID).getSecond() > System.currentTimeMillis()
                    && tokensWithUserIDsAndExpires.get(userID).getFirst().equals(token)) {
                expires = System.currentTimeMillis() + 90 * 60 * 1000;
                tokensWithUserIDsAndExpires.put(userID, Pair.of(token, System.currentTimeMillis() + 90 * 60 * 1000));
            }
            else {
                throw new CannotRenewTokenException("Current token invalid, login again");
            }
        } else {
            throw new CannotRenewTokenException("Current token invalid, login again");
        }
        return SecurityInfoDAO.builder()
                .token(token)
                .userID(userID)
                .expires(expires)
                .build();
    }
}
