package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.SearchType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.List;

@Service
public class CountryService {
    private final RestOperations restTemplate;
    private final String countryByCodeUrl;

    public CountryService(RestOperations restTemplate, @Value("${country_by_code_url}") String countryByCodeUrl) {
        this.restTemplate = restTemplate;
        this.countryByCodeUrl = countryByCodeUrl;
    }

    public List<Country> retrieve(SearchType type, String value) {
        switch (type) {
            case CODE:
                var response = restTemplate.getForEntity(countryByCodeUrl, Country.class, value);
                return Collections.singletonList(response.getBody());
        }

        return Collections.emptyList();
    }
}
