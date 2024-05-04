package by.betrayal.scheduleservice.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        this("Error! Object is not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
