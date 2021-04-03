package com.country.search.domain;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private boolean success;
    private String errorText;
    private List<Country> countries;
    private Statistics statistics;
}
