package server.utility;

public class EnvironmentalVariables {
    private static final String EMAIL_KEY = "EMAIL";
    private static final String EMAIL_PASS_KEY = "EMAIL_PASS";
    private static final String MONGO_ADDRESS = "MONGO_ADDRESS";

    public static String getEmailAddress() {
        return System.getenv(EMAIL_KEY);
    }

    public static String getEmailPassword() {
        return System.getenv(EMAIL_PASS_KEY);
    }

    public static String getMongoConnector() {
        return System.getenv(MONGO_ADDRESS) + "@voipdb-ul854.mongodb.net/test?retryWrites=true&w=majority";
    }
}
