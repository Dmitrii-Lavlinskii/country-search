package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.SearchType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.Comparator;

@Service
public class CountryService {
    private final RestOperations restTemplate;
    private final String countryByCodeUrl;
    private final String countryByFullNameUrl;
    private final String countryByCountryNameUrl;

    public CountryService(RestOperations restTemplate,
                          @Value("${country_by_code_url}") String countryByCodeUrl,
                          @Value("${country_by_full_name_url}") String countryByFullNameUrl,
                          @Value("${country_by_country_name_url}") String countryByCountryNameUrl
                          ) {
        this.restTemplate = restTemplate;
        this.countryByCodeUrl = countryByCodeUrl;
        this.countryByFullNameUrl = countryByFullNameUrl;
        this.countryByCountryNameUrl = countryByCountryNameUrl;
    }

    public Country[] retrieve(SearchType type, String value) {
        Country[] countries;

        switch (type) {
            case CODE:
                var response = restTemplate.getForEntity(countryByCodeUrl, Country.class, value);
                countries = new Country[]{response.getBody()};
                break;
            case FULL_NAME:
                countries = restTemplate.getForEntity(countryByFullNameUrl, Country[].class, value).getBody();
                break;
            case COUNTRY_NAME:
                countries = restTemplate.getForEntity(countryByCountryNameUrl, Country[].class, value).getBody();
                break;
            default:
                throw new IllegalArgumentException("Search type is not supported.");
        }

        if (countries == null) {
            countries = new Country[0];
        }

        Arrays.sort(countries, Comparator.comparing(Country::getPopulation).reversed());
        return countries;
    }
}
