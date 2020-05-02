package server.utility.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super("No such user!");
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
