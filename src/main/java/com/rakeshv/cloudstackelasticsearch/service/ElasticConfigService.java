package com.rakeshv.cloudstackelasticsearch.service;

import com.rakeshv.cloudstackelasticsearch.model.ElasticConfig;

public interface ElasticConfigService {
    public ElasticConfig getElasticConfig();

    public ElasticConfig editElasticConfig(ElasticConfig elasticConfig);

    public void saveElasticConfig(ElasticConfig elasticConfig);
}
