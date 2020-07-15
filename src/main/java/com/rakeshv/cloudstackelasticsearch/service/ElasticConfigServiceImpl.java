package com.rakeshv.cloudstackelasticsearch.service;

import com.rakeshv.cloudstackelasticsearch.model.ElasticConfig;
import com.rakeshv.cloudstackelasticsearch.repository.ElasticConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticConfigServiceImpl implements ElasticConfigService {
    @Autowired
    ElasticConfigRepository elasticConfigRepository;

    @Override
    public ElasticConfig getElasticConfig() {
        return elasticConfigRepository.findAll().get(0);
    }

    @Override
    public ElasticConfig editElasticConfig(ElasticConfig elasticConfig) {
        ElasticConfig savedElasticConfig = getElasticConfig();
        savedElasticConfig.setIndexName(elasticConfig.getIndexName());
        savedElasticConfig.setType(elasticConfig.getType());
        savedElasticConfig.setFieldName(elasticConfig.getFieldName());
        savedElasticConfig.setQuerySize(elasticConfig.getQuerySize());
        savedElasticConfig.setSortField(elasticConfig.getSortField());
        savedElasticConfig.setDescending(elasticConfig.isDescending());

        elasticConfigRepository.save(savedElasticConfig);
        return elasticConfig;
    }

    @Override
    public void saveElasticConfig(ElasticConfig elasticConfig) {
        elasticConfigRepository.save(elasticConfig);
    }
}
