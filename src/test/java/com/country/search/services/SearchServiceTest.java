package com.country.search.services;


import com.country.search.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import static com.country.search.services.SearchService.BAD_REQUEST_MESSAGE;
import static com.country.search.services.SearchService.NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    private SearchService testSubject;
    @Mock ValidationService validationService;
    @Mock CountryService countryService;
    @Mock StatisticsService statisticsService;

    @BeforeEach
    public void setUp() {
        testSubject = new SearchService(validationService, countryService, statisticsService);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(validationService, countryService, statisticsService);
    }

    @Test
    public void search() {
        SearchRequest searchRequest = buildRequest();
        Country[] countries = new Country[0];
        Statistics statistics = new Statistics();
        when(countryService.retrieve(searchRequest.getType(), searchRequest.getValue())).thenReturn(countries);
        when(statisticsService.calculateStatistics(countries)).thenReturn(statistics);

        SearchResponse response = testSubject.search(searchRequest);
        assertTrue(response.isSuccess());
        assertNull(response.getErrorText());
        assertEquals(countries, response.getCountries());
        assertEquals(statistics, response.getStatistics());

        verify(validationService).validate(searchRequest);
        verify(statisticsService).calculateStatistics(countries);
        verify(countryService).retrieve(searchRequest.getType(), searchRequest.getValue());
    }

    @Test
    public void search_notFound() {
        SearchRequest request = buildRequest();

        doThrow(HttpClientErrorException.NotFound.class)
                .when(countryService).retrieve(request.getType(), request.getValue());

        SearchResponse response = testSubject.search(request);
        assertFalse(response.isSuccess());
        assertEquals(NOT_FOUND_MESSAGE, response.getErrorText());

        verify(validationService).validate(request);
        verify(countryService).retrieve(request.getType(), request.getValue());
    }

    @Test
    public void search_badRequest() {
        SearchRequest request = buildRequest();

        doThrow(HttpClientErrorException.BadRequest.class)
                .when(countryService).retrieve(request.getType(), request.getValue());

        SearchResponse response = testSubject.search(request);
        assertFalse(response.isSuccess());
        assertEquals(BAD_REQUEST_MESSAGE, response.getErrorText());

        verify(validationService).validate(request);
        verify(countryService).retrieve(request.getType(), request.getValue());
    }

    @Test
    public void search_otherException() {
        SearchRequest request = buildRequest();
        Exception exception = new RuntimeException("I am an exception");

        doThrow(exception)
                .when(countryService).retrieve(request.getType(), request.getValue());

        SearchResponse response = testSubject.search(request);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrorText().contains(exception.getMessage()));

        verify(validationService).validate(request);
        verify(countryService).retrieve(request.getType(), request.getValue());
    }

    private SearchRequest buildRequest() {
        SearchRequest request = new SearchRequest();
        request.setValue("abc");
        request.setType(SearchType.COUNTRY_NAME);

        return request;
    }
}