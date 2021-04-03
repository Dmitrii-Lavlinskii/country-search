package com.country.search.domain;

import lombok.Data;

@Data
public class SearchRequest {
    public SearchRequest() {
        // Default to country name.
        type = SearchType.COUNTRY_NAME;
    }

    private SearchType type;
    private String value;
}
