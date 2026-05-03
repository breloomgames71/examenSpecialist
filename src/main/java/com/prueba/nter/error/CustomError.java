package com.prueba.nter.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {
    private Date timestamp;
    private String mensaje;
    private int httpCode;

    public CustomError(int httpCode, String mensaje) {
        this.timestamp = new Date();
        this.httpCode = httpCode;
        this.mensaje = mensaje;
    }
}
