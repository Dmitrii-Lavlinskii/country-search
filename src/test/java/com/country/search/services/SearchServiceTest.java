package com.country.search.services;


import com.country.search.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.country.search.services.SearchService.BAD_REQUEST_MESSAGE;
import static com.country.search.services.SearchService.NOT_FOUND_MESSAGE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

    private SearchService testSubject;
    @Mock ValidationService validationService;
    @Mock CountryService countryService;
    @Mock StatisticsService statisticsService;

    @Before
    public void setUp() {
        testSubject = new SearchService(validationService, countryService, statisticsService);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(validationService, countryService, statisticsService);
    }

    @Test
    public void search() {
        SearchRequest searchRequest = buildRequest();
        List<Country> countries = new ArrayList<>();
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