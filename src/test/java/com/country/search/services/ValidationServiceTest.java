package com.country.search.services;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchType;
import org.junit.Before;
import org.junit.Test;

public class ValidationServiceTest {

    private ValidationService testSubject;

    @Before
    public void setUp() {
        testSubject = new ValidationService();
    }

    @Test
    public void validate() {
        // build a valid request.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue("Andorra");

        testSubject.validate(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullRequest() {
        testSubject.validate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullType() {
        SearchRequest request = new SearchRequest();
        request.setType(null);
        testSubject.validate(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullValue() {
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue(null);
        testSubject.validate(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_emptyValue() {
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue("");
        testSubject.validate(request);
    }
}