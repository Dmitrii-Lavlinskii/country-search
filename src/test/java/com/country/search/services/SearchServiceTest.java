package com.country.search.services;


import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import com.country.search.domain.SearchType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;

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
        verifyNoMoreInteractions(validationService);
    }

    @Test
    public void search() {
        SearchRequest searchRequest = new SearchRequest();
        SearchResponse response = testSubject.search(searchRequest);
        assertTrue(response.isSuccess());

        verify(validationService).validate(searchRequest);
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
    }

    private SearchRequest buildRequest() {
        SearchRequest request = new SearchRequest();
        request.setValue("abc");
        request.setType(SearchType.COUNTRY_NAME);

        return request;
    }
}