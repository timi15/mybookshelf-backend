package hu.unideb.timi15.mybookshelf.config;

import hu.unideb.timi15.mybookshelf.exception.AlreadyExistException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}
