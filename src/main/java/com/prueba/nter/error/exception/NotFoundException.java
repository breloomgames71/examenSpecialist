package com.prueba.nter.error.exception;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
    public static final String MESSAGE = "{0} with id {1} not found!";

    public static Supplier<NotFoundException> supply(String entity, UUID id) {
        return () -> new NotFoundException(
                MessageFormat.format(
                        MESSAGE,
                        entity,
                        id)
        );
    }
}

