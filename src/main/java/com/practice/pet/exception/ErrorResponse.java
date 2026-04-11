package com.practice.pet.exception;

import java.time.LocalDateTime;

/**
 * Uniform JSON error payload returned by {@link GlobalExceptionHandler} for all error responses.
 * Using a record keeps it immutable and serialisation-friendly out of the box.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}
