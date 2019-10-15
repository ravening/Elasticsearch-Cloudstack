package com.rakeshv.cloudstackelasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DisplayIndicesServiceImpl
 */
@Service
public class DisplayIndicesServiceImpl implements DisplayIndicesService {
    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Autowired
    public DisplayIndicesServiceImpl(RestHighLevelClient rClient, ObjectMapper mappper) {
        this.client = rClient;
        this.objectMapper = mappper;
    }

    @Override
    public List<String> listAllIndices() throws IOException {
        GetIndexRequest request = new GetIndexRequest().indices("*");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        String[] indices = response.getIndices();

        return Arrays.asList(indices);
    }
}
