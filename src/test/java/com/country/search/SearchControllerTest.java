package com.country.search;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import com.country.search.services.SearchService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static com.country.search.SearchController.HOME_PAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {
    private SearchController testSubject;
    @Mock SearchService searchService;
    @Mock Model model;

    @Before
    public void setUp() {
        testSubject = new SearchController(searchService);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(searchService);
    }

    @Test
    public void index() {
        assertEquals(HOME_PAGE, testSubject.index(null));
    }

    @Test
    public void search() {
        SearchRequest request = new SearchRequest();
        SearchResponse expected = new SearchResponse();
        when(searchService.search(request)).thenReturn(expected);

        assertEquals(HOME_PAGE, testSubject.search(request, model));
        verify(model).addAttribute(expected);
        verify(searchService).search(request);
    }

    @Test
    public void defaultRequest() {
        assertEquals(new SearchRequest(), testSubject.defaultRequest());
    }

    @Test
    public void defaultResponse() {
        SearchResponse expected = new SearchResponse();
        expected.setSuccess(true);

        SearchResponse actual = testSubject.defaultResponse();
        assertEquals(expected, actual);
    }
}