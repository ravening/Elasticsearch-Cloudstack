package com.rakeshv.cloudstackelasticsearch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ElasticConfigNotFoundException.class)
    public final ResponseEntity handleElasticConfigNotFoundException(final ElasticConfigNotFoundException e) {
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateElasticConfigException.class)
    public final ResponseEntity handleDuplicateElasticConfig(final DuplicateElasticConfigException e) {
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }
}
