package com.country.search.services;

import com.country.search.domain.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ValidationService {
    public static final String NULL_REQUEST =  "Search request cannot be null.";
    public static final String NULL_TYPE =  "Search type cannot be null.";
    public static final String EMPTY_SEARCH =  "Search string cannot be empty.";
    public String validate(SearchRequest request) {
        if (request == null) {
            return NULL_REQUEST;
        }

        if (request.getType() == null) {
            return NULL_TYPE;
        }

        if (!StringUtils.hasText(request.getValue())) {
            return EMPTY_SEARCH;
        }

        return null;
    }
}
