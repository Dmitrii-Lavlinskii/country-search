package com.country.search.services;


import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    public void search_exception() {
        SearchRequest searchRequest = new SearchRequest();
        Exception expectedException = new RuntimeException("I am a fake exception");
        doThrow(expectedException).when(validationService).validate(searchRequest);

        SearchResponse response = testSubject.search(searchRequest);
        assertFalse(response.isSuccess());
        assertTrue(response.getErrorText().contains(expectedException.getMessage()));

        verify(validationService).validate(searchRequest);
    }
}