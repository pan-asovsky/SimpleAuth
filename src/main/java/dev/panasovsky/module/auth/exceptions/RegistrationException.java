package dev.panasovsky.module.auth.exceptions;

public class RegistrationException extends RuntimeException {

    public RegistrationException(final String message) {
        super(message);
    }
}