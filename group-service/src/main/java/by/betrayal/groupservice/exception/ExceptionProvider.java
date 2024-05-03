package by.betrayal.groupservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionProvider {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return handle(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException exception) {
        return handle(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException exception) {
        return handle(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> handle(Exception exception, HttpStatus status) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), status);
    }
}
