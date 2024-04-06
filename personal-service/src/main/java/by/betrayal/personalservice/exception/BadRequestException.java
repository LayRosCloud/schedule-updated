package by.betrayal.personalservice.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        this("Request has bad fields");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
