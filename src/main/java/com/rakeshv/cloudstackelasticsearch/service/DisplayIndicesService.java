package com.rakeshv.cloudstackelasticsearch.service;

import java.io.IOException;
import java.util.List;

public interface DisplayIndicesService {
    public List<String> listAllIndices() throws IOException;
}
