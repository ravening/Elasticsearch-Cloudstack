package com.rakeshv.cloudstackelasticsearch.controllers;

import com.rakeshv.cloudstackelasticsearch.model.ElasticConfig;
import com.rakeshv.cloudstackelasticsearch.service.ElasticConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/config")
@Slf4j
public class ElasticConfigController {
    @Autowired
    ElasticConfigServiceImpl elasticConfigService;

    @GetMapping
    public ResponseEntity<ElasticConfig> getElasticConfig() {
        return new ResponseEntity<>(elasticConfigService.getElasticConfig(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ElasticConfig> modifyElasticConfig(@Valid @RequestBody ElasticConfig elasticConfig) {
        return new ResponseEntity<>(elasticConfigService.editElasticConfig(elasticConfig), HttpStatus.OK);
    }

    @GetMapping("/updateindex/{name}")
    public ResponseEntity changeIndex(@PathVariable("name") String indexName) {
        ElasticConfig elasticConfig = elasticConfigService.getElasticConfig();
        elasticConfig.setIndexName(indexName);
        elasticConfigService.saveElasticConfig(elasticConfig);

        return ResponseEntity.ok("Index updated successfully");
    }

    @GetMapping("/update/querysize/{value}")
    public ResponseEntity<String> changeQuerySize(@PathVariable("value") int value) {
        ElasticConfig elasticConfig = elasticConfigService.getElasticConfig();
        elasticConfig.setQuerySize(value);
        elasticConfigService.saveElasticConfig(elasticConfig);
        return ResponseEntity.ok("Query size changed to " + value);
    }
}
