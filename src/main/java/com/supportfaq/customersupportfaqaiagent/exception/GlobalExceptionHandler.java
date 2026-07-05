package com.supportfaq.customersupportfaqaiagent.exception;

import com.supportfaq.customersupportfaqaiagent.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final AuditLogService auditLogService;

    public GlobalExceptionHandler(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(ResourceNotFoundException exception) {
        return new ApiErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({BadRequestException.class, AiConfigurationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequest(RuntimeException exception) {
        return new ApiErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ApiErrorResponse handleRateLimit(RateLimitExceededException exception) {
        return new ApiErrorResponse(exception.getMessage(), HttpStatus.TOO_MANY_REQUESTS.value());
    }

    @ExceptionHandler(BlockedIpException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleBlockedIp(BlockedIpException exception) {
        return new ApiErrorResponse(exception.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");
        return new ApiErrorResponse(message, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMalformedJson(HttpMessageNotReadableException exception) {
        return new ApiErrorResponse("Malformed JSON request.", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMissingParameter(MissingServletRequestParameterException exception) {
        return new ApiErrorResponse("Missing required request parameter: " + exception.getParameterName(),
                HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ApiErrorResponse("Invalid value for request parameter or path variable: " + exception.getName(),
                HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleDatabase(DataAccessException exception) {
        return new ApiErrorResponse("Database error. Please check the backend logs.", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public org.springframework.http.ResponseEntity<ApiErrorResponse> handleResponseStatus(ResponseStatusException exception) {
        int status = exception.getStatusCode().value();
        String reason = exception.getReason();
        String message = reason == null || reason.isBlank() ? "Request failed." : reason;
        return org.springframework.http.ResponseEntity
                .status(exception.getStatusCode())
                .body(new ApiErrorResponse(message, status));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneral(Exception exception, HttpServletRequest request) {
        logSecurityError(exception, request);
        return new ApiErrorResponse("Unexpected server error. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private void logSecurityError(Exception exception, HttpServletRequest request) {
        try {
            auditLogService.log("SECURITY_ERROR", "HIGH",
                    "Unhandled server error: " + exception.getClass().getSimpleName(),
                    request);
        } catch (RuntimeException ignored) {
            // Keep the sanitized error response stable even if audit persistence is unavailable.
        }
    }
}
