package com.country.search.services;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final ValidationService validationService;

    public SearchService(ValidationService validationService) {
        this.validationService = validationService;
    }

    public SearchResponse search(SearchRequest request) {
        validationService.validate(request);
        return new SearchResponse();
    }
}
