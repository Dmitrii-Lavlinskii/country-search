package com.country.search.services;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SearchService {
    private final ValidationService validationService;

    public SearchService(ValidationService validationService) {
        this.validationService = validationService;
    }

    public SearchResponse search(SearchRequest request) {
        SearchResponse response = new SearchResponse();

        try
        {
            validationService.validate(request);
        }
        catch (Exception exception) {
            // Simplified exception handling.
            // We definitely can at least separate validation errors from api timeouts or 4xx, 5xx and so on.
            // But for the sake of demo app I keep it simple. DL.

            // Return uuid to the service consumer.
            // In real life the user can reach out to the support team with this code.
            // We can find it in logs which would help to hunt down the issue.
            UUID uuid = UUID.randomUUID();
            log.error("an unhandled exception occurred, uuid=" + uuid, exception);

            response.setHasError(true);
            response.setErrorText(String.format("%s. Code: %s.", exception.getMessage(), uuid));
        }

        return response;
    }
}
