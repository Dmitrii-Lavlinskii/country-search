package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import com.country.search.domain.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SearchService {
    public static final String NOT_FOUND_MESSAGE = "Countries not found.";
    private final ValidationService validationService;
    private final CountryService countryService;
    private final StatisticsService statisticsService;

    public SearchService(ValidationService validationService, CountryService countryService, StatisticsService statisticsService) {
        this.validationService = validationService;
        this.countryService = countryService;
        this.statisticsService = statisticsService;
    }

    public SearchResponse search(SearchRequest request) {
        // validate request.
        String validationError = validationService.validate(request);
        SearchResponse response = new SearchResponse();
        if (validationError != null) {
            response.setErrorText(validationError);
            return response;
        }

        try
        {
            // retrieve countries and calculate statistics.
            List<Country> countries = countryService.retrieve(request.getType(), request.getValue());
            Statistics statistics = statisticsService.calculateStatistics(countries);

            response.setCountries(countries);
            response.setStatistics(statistics);
            response.setSuccess(true);
        }
        catch (HttpClientErrorException.NotFound notFound) {
            log.info("Country not found. Type: {}. Value: {}", request.getType(), request.getValue());
            response.setErrorText(NOT_FOUND_MESSAGE);
        }
        catch (HttpClientErrorException.BadRequest badRequest) {
            log.warn("Bad request. Likely user error. Type: {}. Value: {}", request.getType(), request.getValue());
            response.setErrorText("Incorrect search string.");
        }
        catch (Exception exception) {
            // Return uuid to the service consumer.
            // In real life the user can reach out to the support team with this code.
            // We can find it in logs which would help to hunt down the issue.
            UUID uuid = UUID.randomUUID();
            log.error("an unhandled exception occurred, uuid=" + uuid, exception);

            response.setErrorText(String.format("%s. Code: %s.", exception.getMessage(), uuid));
        }

        return response;
    }
}
