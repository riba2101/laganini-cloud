package com.laganini.cloud.rmi.service;

import com.laganini.cloud.exception.BusinessException;
import com.laganini.cloud.exception.BusinessExceptionResponse;
import com.laganini.cloud.exception.ExceptionType;
import com.laganini.cloud.exception.detail.AbstractExceptionDetail;
import com.laganini.cloud.exception.detail.FieldExceptionDetail;
import com.laganini.cloud.exception.detail.GlobalExceptionDetail;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ServerControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception e) {
        log.error("An global error occurred", e);
        return buildResponseEntity(ExceptionType.INTERNAL_SERVER_ERROR, new HttpHeaders());
    }

    //RMI stuff
    @ExceptionHandler({
            NoFallbackAvailableException.class,
            TimeoutException.class,
            CallNotPermittedException.class,
            BulkheadFullException.class
    })
    public ResponseEntity<Object> handleCircuitBreaker(RuntimeException e) {
        log.error("An RMI error occurred", e);
        Throwable cause = e.getCause();
        if (cause instanceof BusinessException exception) {
            return handleBusinessException(exception);
        }

        return buildResponseEntity(ExceptionType.SERVICE_NOT_AVAILABLE, new HttpHeaders());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e) {
        log.error("An REMOTE error occurred", e);
        ExceptionType type = e.getType();
        return buildResponseEntity(
                BusinessExceptionResponse
                        .builder(type)
                        .details(e.getDetails())
                        .build(),
                new HttpHeaders()
        );
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Object> handleServerWebInputException(ServerWebInputException e) {
        log.error("An request error occurred", e);

        return buildResponseEntity(ExceptionType.REQUEST, new HttpHeaders());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintValidationException(ConstraintViolationException e) {
        log.error("An validation error occurred", e);
        ExceptionType type = ExceptionType.VALIDATION;
        BusinessExceptionResponse body = BusinessExceptionResponse
                .builder(type)
                .details(mapConstraintValidationErrors(e))
                .build();

        return buildResponseEntity(body, new HttpHeaders());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> handleWebExchangeBindException(WebExchangeBindException e) {
        log.error("An validation error occurred", e);
        ExceptionType type = ExceptionType.VALIDATION;
        BusinessExceptionResponse body = BusinessExceptionResponse
                .builder(type)
                .details(mapWebExchangeBindErrors(e))
                .build();

        return buildResponseEntity(body, new HttpHeaders());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException e) {
        Throwable cause1 = e.getCause();
        if (RollbackException.class.isAssignableFrom(cause1.getClass())) {
            Throwable cause2 = cause1.getCause();
            if (ConstraintViolationException.class.isAssignableFrom(cause2.getClass())) {
                return handleConstraintValidationException((ConstraintViolationException) cause2);
            }
        }

        return handleGlobalException((Exception) cause1);
    }

    private ResponseEntity<Object> buildResponseEntity(
            ExceptionType type,
            HttpHeaders headers
    )
    {
        return buildResponseEntity(
                BusinessExceptionResponse.builder(type).build(),
                headers
        );
    }

    private ResponseEntity<Object> buildResponseEntity(
            BusinessExceptionResponse body,
            HttpHeaders headers
    )
    {
        return buildResponseEntity(
                body,
                headers,
                HttpStatus.BAD_REQUEST
        );
    }

    //entity data
    private Collection<AbstractExceptionDetail> mapConstraintValidationErrors(ConstraintViolationException e) {
        return e
                .getConstraintViolations()
                .stream()
                .map(error -> new FieldExceptionDetail(
                        error.getMessage(),
                        error.getPropertyPath().toString(),
                        null,
                        error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()
                ))
                .collect(Collectors.toList());
    }

    private Collection<AbstractExceptionDetail> mapWebExchangeBindErrors(WebExchangeBindException e) {
        List<AbstractExceptionDetail> globalExceptionDetails = e
                .getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> new GlobalExceptionDetail(error.getDefaultMessage(), null))
                .collect(Collectors.toList());

        List<AbstractExceptionDetail> filedExceptionDetails = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldExceptionDetail(
                        error.getDefaultMessage(),
                        error.getField(),
                        null,
                        error.getCode()
                ))
                .collect(Collectors.toList());

        globalExceptionDetails.addAll(filedExceptionDetails);

        return globalExceptionDetails;
    }

    private ResponseEntity<Object> buildResponseEntity(
            Object body,
            HttpHeaders headers,
            HttpStatus status
    )
    {
        return new ResponseEntity<>(body, headers, status);
    }

}