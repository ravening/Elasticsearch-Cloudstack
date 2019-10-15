package com.rakeshv.cloudstackelasticsearch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateElasticConfigException extends RuntimeException {
    public DuplicateElasticConfigException() {
        super();
    }

    public DuplicateElasticConfigException(String message) {
        super(message);
    }
}
