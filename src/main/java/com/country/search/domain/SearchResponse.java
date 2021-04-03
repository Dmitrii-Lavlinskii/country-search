package com.country.search.domain;

import lombok.Data;

@Data
public class SearchResponse {
    private boolean success;
    private String errorText;
    private Country[] countries;
    private Statistics statistics;
}
