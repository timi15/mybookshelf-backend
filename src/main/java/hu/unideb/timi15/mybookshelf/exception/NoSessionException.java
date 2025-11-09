package hu.unideb.timi15.mybookshelf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NoSessionException extends RuntimeException {

    public NoSessionException() {
    }

    public NoSessionException(String message) {
        super(message);
    }
}
