package by.betrayal.personalservice.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Object is not found exception");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
