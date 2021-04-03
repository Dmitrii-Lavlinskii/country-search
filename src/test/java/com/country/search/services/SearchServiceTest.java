package com.country.search.services;


import com.country.search.domain.SearchRequest;
import com.country.search.services.ValidationService;
import com.country.search.services.SearchService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

    private SearchService testSubject;
    @Mock ValidationService validationService;

    @Before
    public void setUp() {
        testSubject = new SearchService(validationService);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(validationService);
    }

    @Test
    public void search() {
        SearchRequest searchRequest = new SearchRequest();
        assertNotNull(testSubject.search(searchRequest));

        verify(validationService).validate(searchRequest);
    }
}