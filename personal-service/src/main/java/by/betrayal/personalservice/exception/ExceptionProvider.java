package by.betrayal.personalservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionProvider {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
