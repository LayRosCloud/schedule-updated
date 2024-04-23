package by.betrayal.scheduleservice.utils;

import by.betrayal.scheduleservice.exception.NotFoundException;

public abstract class ThrowableUtils {

    public static NotFoundException getNotFoundException() {
        return new NotFoundException();
    }

    public static NotFoundException getNotFoundException(String message, Object... args) {
        return new NotFoundException(
                String.format(message, args)
        );
    }

    public static IllegalArgumentException getBadRequestException(String message, Object... args) {
        return new IllegalArgumentException(
                String.format(message, args)
        );
    }

    public static IllegalArgumentException getBadRequestException() {
        return new IllegalArgumentException();
    }
}
