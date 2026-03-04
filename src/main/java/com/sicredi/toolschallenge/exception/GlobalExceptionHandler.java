package com.sicredi.toolschallenge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServicoException.class)
    public ResponseEntity<Map<String, Object>> handleServicoException(ServicoException ex) {

        Map<String, Object> erro = new HashMap<>();
        erro.put("status", ex.getStatus().value());
        erro.put("error", ex.getStatus().getReasonPhrase());
        erro.put("message", ex.getMessage());
        erro.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(erro, ex.getStatus());
    }
}
