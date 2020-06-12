package com.example.productservice.exception.handler;

import com.example.productservice.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Custom exception handler
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiError> notFound(RuntimeException ex) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, new Date(), ex.getMessage());

        LOGGER.debug("Exception handled={}", apiError);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String mapAsString = errors.keySet().stream().map(key -> key + "=" + errors.get(key))
                .collect(Collectors.joining(", ", "{", "}"));

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, new Date(), mapAsString);

        LOGGER.debug("Exception handleValidationExceptions={}", apiError);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
