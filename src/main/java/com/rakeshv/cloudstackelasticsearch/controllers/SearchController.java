package com.rakeshv.cloudstackelasticsearch.controllers;

import com.rakeshv.cloudstackelasticsearch.service.ElasticSearchService;
import com.rakeshv.cloudstackelasticsearch.model.Logs;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private ElasticSearchService searchService;

    @Autowired
    public SearchController(ElasticSearchService service) {
        this.searchService = service;
    }

    @GetMapping("/listall")
    public ResponseEntity<List<Logs>> getAllLogs() throws IOException {
        return new ResponseEntity<>(searchService.findAllLogs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logs> findById(@PathVariable("id") String id) throws IOException {
        return new ResponseEntity<>(searchService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/query/{query}")
    public ResponseEntity<List<Logs>> findByQuery(@PathVariable("query") String query) throws IOException {
        return new ResponseEntity<>(searchService.findByMessageString(query), HttpStatus.OK);
    }
}
