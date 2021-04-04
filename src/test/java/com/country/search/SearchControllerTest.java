package com.country.search;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import com.country.search.services.SearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static com.country.search.SearchController.HOME_PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {
    private SearchController testSubject;
    @Mock SearchService searchService;
    @Mock Model model;

    @BeforeEach
    public void setUp() {
        testSubject = new SearchController(searchService);
    }

    @AfterEach
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