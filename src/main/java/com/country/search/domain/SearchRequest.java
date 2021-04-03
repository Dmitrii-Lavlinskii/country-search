package com.country.search.domain;

public class SearchRequest {
    public SearchRequest() {
        // Default to country name.
        type = SearchType.COUNTRY_NAME;
    }

    private SearchType type;
    private String value;

    public SearchType getType() {
        return type;
    }

    public void setType(SearchType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
