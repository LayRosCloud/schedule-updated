package by.betrayal.accountservice.exception;

public class NotAuthorizationException extends RuntimeException {

    public NotAuthorizationException() {
        super("User is not authorized");
    }

    public NotAuthorizationException(String message) {
        super(message);
    }
}
