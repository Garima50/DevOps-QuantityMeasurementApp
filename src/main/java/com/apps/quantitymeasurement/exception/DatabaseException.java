package com.apps.quantitymeasurement.exception;

/**
 * Custom exception for database-related errors.
 */
public class DatabaseException extends RuntimeException {

    // Creates an exception with a custom message
    public DatabaseException(String message) {
        super(message);
    }

    // Creates an exception with a custom message and original cause
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}