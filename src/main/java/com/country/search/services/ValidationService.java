package com.country.search.services;

import com.country.search.domain.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ValidationService {
    public String validate(SearchRequest request) {
        if (request == null) {
            return "Search request cannot be null.";
        }

        if (request.getType() == null) {
            return "Search type cannot be null.";
        }

        if (!StringUtils.hasText(request.getValue())) {
            return "Search string cannot be empty.";
        }

        return null;
    }
}
