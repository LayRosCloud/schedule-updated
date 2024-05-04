package by.betrayal.groupservice.utils;

import by.betrayal.groupservice.exception.NotFoundException;

public abstract class ExceptionUtils {

    public static NotFoundException getNotFoundException(String message, Object... args) {
        return new NotFoundException(String.format(message, args));
    }

    public static NotFoundException getNotFoundIdException(String nameObject, Number id) {
        return getNotFoundException("%s with id %s is not found", id);
    }

    public static IllegalArgumentException getBadRequestException(String message, Object... args) {
        return new IllegalArgumentException(String.format(message, args));
    }
}
