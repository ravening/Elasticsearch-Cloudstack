package com.rakeshv.cloudstackelasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.cloudstackelasticsearch.model.ElasticConfig;
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
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService{
    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Autowired
    ElasticConfigServiceImpl elasticConfigService;

    @Autowired
    public ElasticSearchServiceImpl(RestHighLevelClient client, ObjectMapper mapper) {
        this.client = client;
        this.objectMapper = mapper;
    }

    @Override
    public List<Logs> findAllLogs() throws IOException {
        ElasticConfig elasticConfig = elasticConfigService.getElasticConfig();
        String indexName = elasticConfig.getIndexName();
        String typeName = elasticConfig.getType();
        int querySize = elasticConfig.getQuerySize();
        String sortField = elasticConfig.getSortField();
        SortOrder sortOrder = elasticConfig.isDescending() ? SortOrder.DESC : SortOrder.ASC;

        SearchRequest searchRequest = buildSearchRequest(indexName, typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(querySize);
        searchSourceBuilder.sort(new FieldSortBuilder(sortField).order(sortOrder));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }

    @Override
    public Logs findById(String id) throws IOException {
        ElasticConfig elasticConfig = elasticConfigService.getElasticConfig();
        String indexName = elasticConfig.getIndexName();
        String typeName = elasticConfig.getType();
        GetRequest getRequest = new GetRequest(indexName, typeName, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

//        return convertStringToLogsObject(getResponse.getSourceAsString());
        return convertMapToLogsObject(getResponse.getSource());
    }

    @Override
    public List<Logs> findByMessageString(String query) throws IOException {
        ElasticConfig elasticConfig = elasticConfigService.getElasticConfig();
        String indexName = elasticConfig.getIndexName();
        String typeName = elasticConfig.getType();
        int querySize = elasticConfig.getQuerySize();
        String fieldName = elasticConfig.getFieldName();
        String sortField = elasticConfig.getSortField();
        SortOrder sortOrder = elasticConfig.isDescending() ? SortOrder.DESC : SortOrder.ASC;

        SearchRequest searchRequest = buildSearchRequest(indexName, typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, query);
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.size(querySize);

        searchSourceBuilder.sort(new FieldSortBuilder(sortField).order(sortOrder));
        searchRequest.source(searchSourceBuilder);
        //searchRequest.scroll(new TimeValue(10, TimeUnit.MINUTES));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }

    private SearchRequest buildSearchRequest(String index, String type) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
//        searchRequest.types(type);

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
