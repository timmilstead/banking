package me.milstead.banking.error;

import java.time.Instant;
import java.util.Map;

/**
 * For handling validation errors in the exception handler.
 * @param status
 * @param message
 * @param fieldErrors
 * @param timestamp
 */
public record ApiError(
        int status,
        String message,
        Map<String, String> fieldErrors,
        Instant timestamp) {}
