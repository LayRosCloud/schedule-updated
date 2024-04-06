package by.betrayal.accountservice.utils;

import by.betrayal.accountservice.exception.BadRequestException;
import by.betrayal.accountservice.exception.NotFoundException;
import by.betrayal.accountservice.exception.NotAuthorizationException;

public class ThrowableUtils {

    public static BadRequestException getBadRequestException() {
        return new BadRequestException();
    }

    public static NotAuthorizationException getNotAuthorizationException(String message, Object... args) {
        return new NotAuthorizationException(String.format(message, args));
    }

    public static NotAuthorizationException getNotAuthorizationException() {
        return new NotAuthorizationException();
    }

    public static BadRequestException getBadRequestException(String message, Object... args) {
        return new BadRequestException(String.format(message, args));
    }

    public static NotFoundException getNotFoundException() {
        return new NotFoundException();
    }

    public static NotFoundException getNotFoundException(String message, Object... args) {
        return new NotFoundException(String.format(message, args));
    }
}
