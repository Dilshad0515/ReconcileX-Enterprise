package com.reconcilex.exception;

import com.reconcilex.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleInvalidRequest(
                        HttpMessageNotReadableException exception,
                        HttpServletRequest request) {

                return buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                "Invalid request data",
                                request.getRequestURI());
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ApiErrorResponse> handleDatabaseConstraintViolation(
                        DataIntegrityViolationException exception,
                        HttpServletRequest request) {

                return buildErrorResponse(
                                HttpStatus.CONFLICT,
                                "The operation violates a database constraint",
                                request.getRequestURI());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(
                        Exception exception,
                        HttpServletRequest request) {

                logger.error(
                                "Unexpected error while processing request: {}",
                                request.getRequestURI(),
                                exception);

                return buildErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "An unexpected error occurred",
                                request.getRequestURI());
        }

        private ResponseEntity<ApiErrorResponse> buildErrorResponse(
                        HttpStatus status,
                        String message,
                        String path) {

                ApiErrorResponse errorResponse = new ApiErrorResponse(
                                OffsetDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                message,
                                path);

                return ResponseEntity.status(status).body(errorResponse);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
                        IllegalArgumentException exception,
                        HttpServletRequest request) {

                return buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                exception.getMessage(),
                                request.getRequestURI());
        }
}
