package by.betrayal.groupservice.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        this("Object is not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
