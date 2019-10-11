package com.rakeshv.cloudstackelasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.cloudstackelasticsearch.model.Logs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService{
    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Value("${elasticsearch.index}")
    private String indexName;
    @Value("${elasticsearch.type}")
    private String typeName;
    @Value("${elasticsearch.querysize}")
    private int querySize;

    @Autowired
    public ElasticSearchServiceImpl(RestHighLevelClient client, ObjectMapper mapper) {
        this.client = client;
        this.objectMapper = mapper;
    }

    @Override
    public List<Logs> findAllLogs() throws IOException {
        SearchRequest searchRequest = buildSearchRequest(indexName, typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(querySize);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }

    @Override
    public Logs findById(String id) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, typeName, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        return convertStringToLogsObject(getResponse.getSourceAsString());
//        return convertMapToLogsObject(getResponse.getSource());
    }

    private SearchRequest buildSearchRequest(String index, String type) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);

        return searchRequest;
    }

    private List<Logs> getSearchResult(SearchResponse response) {
        SearchHit[] searchHit = response.getHits().getHits();
        List<Logs> logsList = new ArrayList<>();
        for (SearchHit hit : searchHit) {
            logsList.add(objectMapper.convertValue(hit.getSourceAsMap(), Logs.class));
        }

        return logsList;
    }

    private Logs convertStringToLogsObject(String response) throws JsonProcessingException {
        return objectMapper.readValue(response, Logs.class);
    }

    private Logs convertMapToLogsObject(Map<String, Object> map) {
        return objectMapper.convertValue(map, Logs.class);
    }
}
