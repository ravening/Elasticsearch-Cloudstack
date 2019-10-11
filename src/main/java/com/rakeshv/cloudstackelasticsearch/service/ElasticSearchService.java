package com.rakeshv.cloudstackelasticsearch.service;

import com.rakeshv.cloudstackelasticsearch.model.Logs;
import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {
    public List<Logs> findAllLogs() throws IOException;
    public Logs findById(String id) throws IOException;
}
