package com.country.search.services;

import com.country.search.domain.SearchRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ValidationService {
    public void validate(SearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null.");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("request type cannot be null.");
        }

        if (!StringUtils.hasText(request.getValue())) {
            throw new IllegalArgumentException("request value must contain text");
        }
    }
}
