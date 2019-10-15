package com.rakeshv.cloudstackelasticsearch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElasticConfigNotFoundException extends RuntimeException{
    public ElasticConfigNotFoundException() {
        super();
    }

    public ElasticConfigNotFoundException(String message) {
        super(message);
    }
}
