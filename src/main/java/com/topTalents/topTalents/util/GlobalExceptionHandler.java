package com.topTalents.topTalents.util;

import com.topTalents.topTalents.data.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ErrorResponse body = new ErrorResponse(
                status.value(),
                "Validation Failed",
                "Има грешки във входните данни",
                request.getDescription(false).replace("uri=", "")
        );
        body.setValidationErrors(errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex,
            WebRequest request
    ) {
        ErrorResponse body = new ErrorResponse(
                HttpStatusCode.valueOf(404).value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,
            WebRequest request
    ) {
        ErrorResponse body = new ErrorResponse(
                HttpStatusCode.valueOf(500).value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(500).body(body);
    }
}
