package com.prueba.nter.error.exception;

import com.prueba.nter.error.CustomError;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileReadingException extends RuntimeException{
    private final CustomError customError;

    public FileReadingException(String mensaje, int httpCode) {
        super(mensaje);
        customError = new CustomError(httpCode, mensaje);
    }
}
