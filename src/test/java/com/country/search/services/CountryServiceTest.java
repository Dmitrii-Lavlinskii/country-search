package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.SearchType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {
    private CountryService testSubject;
    @Mock private RestOperations restOperations;
    @Mock ResponseEntity<Country> responseEntity;
    private static final String CODE_URL = "I am country-by-code endpoint";

    @Before
    public void setUp() {
        testSubject = new CountryService(restOperations, CODE_URL);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(restOperations);
    }

    @Test
    public void retrieve_code() {
        List<Country> countries = new ArrayList<>();
        String searchText = "I am a country code";
        countries.add(new Country());

        when(restOperations.getForEntity(CODE_URL, Country.class, searchText)).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(countries.get(0));

        var actual = testSubject.retrieve(SearchType.CODE, searchText);
        assertEquals(countries, actual);
        verify(restOperations).getForEntity(CODE_URL, Country.class, searchText);
    }

    @Test
    public void retrieve_countryName() {
        var actual = testSubject.retrieve(SearchType.COUNTRY_NAME, "abc");
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void retrieve_fullName() {
        var actual = testSubject.retrieve(SearchType.FULL_NAME, "def");
        assertEquals(Collections.emptyList(), actual);
    }
}