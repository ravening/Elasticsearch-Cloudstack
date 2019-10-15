package com.rakeshv.cloudstackelasticsearch.repository;

import com.rakeshv.cloudstackelasticsearch.model.ElasticConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticConfigRepository extends JpaRepository<ElasticConfig, Long> {
}
