package hu.unideb.timi15.mybookshelf.advice;

import hu.unideb.timi15.mybookshelf.exception.AlreadyExistException;
import hu.unideb.timi15.mybookshelf.exception.AlreadyInListException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.exception.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = (err instanceof FieldError fe)
                    ? fe.getField()
                    : err.getObjectName();

            errors.computeIfAbsent(field,
                    (k) -> new ArrayList<>()).add(err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(Map.of(
                "title", "constraint violation",
                "status", "400",
                "errors", errors,
                "timestamp", System.currentTimeMillis()
        ));

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            NotFoundException ex) {

        return ResponseEntity.status(404).body(Map.of(
                "title", "not found",
                "status", "404",
                "message", ex.getMessage(),
                "timestamp", System.currentTimeMillis()
        ));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExist(AlreadyExistException ex) {

        return ResponseEntity.status(409).body(Map.of(
                "title", "date already exist",
                "status", "409",
                "message", ex.getMessage(),
                "timestamp", System.currentTimeMillis()
        ));
    }

    @ExceptionHandler(AlreadyInListException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyInList(AlreadyInListException ex) {

        return ResponseEntity.status(409).body(Map.of(
                "title", "date already in list",
                "status", "409",
                "message", ex.getMessage(),
                "timestamp", System.currentTimeMillis()
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {

        return ResponseEntity.status(401).body(Map.of(
                "title", "Unauthorized",
                "status", "401",
                "message", ex.getMessage(),
                "timestamp", System.currentTimeMillis()
        ));
    }
}
