package com.abhishek.movieexplorer.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.abhishek.movieexplorer.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException exception) {

        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        response.put("message", "Validation failed");
        response.put("description", "One or more fields have invalid values.");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Resource already exists",
                        exception.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Unauthorized",
                        exception.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TmdbApiException.class)
    public ResponseEntity<ErrorResponse> handleTmdb(TmdbApiException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "External service error",
                        exception.getMessage()),
                HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedBody(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        "Invalid request body",
                        "The request body is missing or incorrectly formatted."));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMedia(HttpMediaTypeNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
                new ErrorResponse(
                        "Unsupported media type",
                        "The content type is not supported. Please use a valid media type."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException exception) {
        log.error("Unexpected error", exception);
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Internal server error",
                        "An unexpected error occurred. Please try again later."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
