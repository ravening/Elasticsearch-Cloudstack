package com.rakeshv.cloudstackelasticsearch.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Logs {
    private String username;
    private String description;
    private String platform;
    private String timestamp;
    private String domain;
    private String type;
}
