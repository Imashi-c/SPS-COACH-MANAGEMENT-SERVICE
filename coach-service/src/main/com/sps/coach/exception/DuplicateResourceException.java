package com.sps.coach.exception;

/**
 * Exception thrown when trying to create a duplicate resource
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
