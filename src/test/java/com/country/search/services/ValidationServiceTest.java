package com.country.search.services;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.country.search.services.ValidationService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ValidationServiceTest {

    private ValidationService testSubject;

    @BeforeEach
    public void setUp() {
        testSubject = new ValidationService();
    }

    @Test
    public void validate() {
        // build a valid request.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue("Andorra");

        assertNull(testSubject.validate(request));
    }

    @Test
    public void validate_nullRequest() {
        assertEquals(NULL_REQUEST, testSubject.validate(null));
    }

    @Test
    public void validate_nullType() {
        SearchRequest request = new SearchRequest();
        request.setType(null);
        assertEquals(NULL_TYPE, testSubject.validate(request));
    }

    @Test
    public void validate_nullValue() {
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue(null);
        assertEquals(EMPTY_SEARCH, testSubject.validate(request));
    }

    @Test
    public void validate_emptyValue() {
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue("");
        assertEquals(EMPTY_SEARCH, testSubject.validate(request));
    }
}