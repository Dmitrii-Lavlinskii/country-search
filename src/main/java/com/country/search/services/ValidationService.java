package com.country.search.services;

import com.country.search.domain.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ValidationService {
    public void validate(SearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Request type cannot be null.");
        }

        if (!StringUtils.hasText(request.getValue())) {
            throw new IllegalArgumentException("Request value must contain text.");
        }

        log.info("Request type: {}, value: {}. Validation passed.", request.getType(), request.getValue());
    }
}
