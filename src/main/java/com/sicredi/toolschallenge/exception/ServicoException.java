package com.sicredi.toolschallenge.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServicoException extends RuntimeException {

    private final HttpStatus status;

    public ServicoException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}