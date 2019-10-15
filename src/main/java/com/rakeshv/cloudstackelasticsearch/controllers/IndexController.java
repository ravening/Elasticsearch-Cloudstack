package com.rakeshv.cloudstackelasticsearch.controllers;

import com.rakeshv.cloudstackelasticsearch.service.DisplayIndicesServiceImpl;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/index")
public class IndexController {
    @Autowired
    DisplayIndicesServiceImpl displayIndicesService;

    @GetMapping
    public ResponseEntity<List<String>> listIndices() throws IOException {
        return new ResponseEntity<>(displayIndicesService.listAllIndices(), HttpStatus.OK);
    }
}
