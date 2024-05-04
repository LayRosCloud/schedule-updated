package by.betrayal.requestservice.utils;

import by.betrayal.requestservice.exception.NotFoundException;

public class ExceptionUtils {

    public static NotFoundException getNotFoundException() {
        return getNotFoundException("Object is not found");
    }

    public static NotFoundException getNotFoundIdException(String nameObject, Number id) {
        return getNotFoundException("%s with id %s is not found", nameObject, id);
    }

    public static NotFoundException getNotFoundException(String message, Object... args) {
        return new NotFoundException(String.format(message, args));
    }

    public static IllegalArgumentException getBadRequestException(String message, Object... args) {
        return new IllegalArgumentException(String.format(message, args));
    }

    public static IllegalArgumentException getBadRequestException() {
        return getBadRequestException("Object has bad fields");
    }
}
