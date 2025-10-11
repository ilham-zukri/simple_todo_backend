package com.dreamdev.simple_todo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloobalExceptionHandler {

    // @ExceptionHandler(DataIntegrityViolationException.class)
    // public ResponseEntity<Map<String, Object>> handleDuplicateData(DataIntegrityViolationException ex) {
    //     Map<String, Object> response = new HashMap<>();
    //     response.put("timestamp", LocalDateTime.now());
    //     if (ex.getMessage().contains("username")) {
    //         response.put("message", "Username already exists.");
    //     } else if (ex.getMessage().contains("email")) {
    //         response.put("message", "Email already exists.");
    //     } else {
    //         response.put("message", "Duplicate data detected.");
    //     }
    //     return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    // }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);    
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
