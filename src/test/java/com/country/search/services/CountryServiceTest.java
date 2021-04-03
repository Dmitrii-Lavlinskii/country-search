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

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {
    private CountryService testSubject;

    @Mock private RestOperations restOperations;
    @Mock ResponseEntity<Country> singleResponseEntity;
    @Mock ResponseEntity<Country[]> multipleResponseEntity;

    private static final String CODE_URL = "I am country-by-code endpoint";
    private static final String COUNTRY_NAME_URL = "I am country-by-name endpoint";
    private static final String FULL_NAME_URL = "I am country-by-full-name endpoint";

    @Before
    public void setUp() {
        testSubject = new CountryService(restOperations, CODE_URL, FULL_NAME_URL, COUNTRY_NAME_URL);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(restOperations, singleResponseEntity, multipleResponseEntity);
    }

    @Test
    public void retrieve_code() {
        Country[] countries = new Country[1];
        String searchText = "I am a country code";
        countries[0] = new Country();

        when(restOperations.getForEntity(CODE_URL, Country.class, searchText)).thenReturn(singleResponseEntity);
        when(singleResponseEntity.getBody()).thenReturn(countries[0]);

        var actual = testSubject.retrieve(SearchType.CODE, searchText);
        assertArrayEquals(countries, actual);
        verify(restOperations).getForEntity(CODE_URL, Country.class, searchText);
        verify(singleResponseEntity).getBody();
    }

    @Test
    public void retrieve_countryName() {
        Country[] countries = new Country[1];
        String searchText = "I am a country name";
        countries[0] = new Country();

        when(restOperations.getForEntity(COUNTRY_NAME_URL, Country[].class, searchText)).thenReturn(multipleResponseEntity);
        when(multipleResponseEntity.getBody()).thenReturn(countries);

        var actual = testSubject.retrieve(SearchType.COUNTRY_NAME, searchText);
        assertArrayEquals(countries, actual);
        verify(restOperations).getForEntity(COUNTRY_NAME_URL, Country[].class, searchText);
        verify(multipleResponseEntity).getBody();
    }

    @Test
    public void retrieve_fullName() {
        Country[] countries = new Country[1];
        String searchText = "I am a full name";
        countries[0] = new Country();

        when(restOperations.getForEntity(FULL_NAME_URL, Country[].class, searchText)).thenReturn(multipleResponseEntity);
        when(multipleResponseEntity.getBody()).thenReturn(countries);

        var actual = testSubject.retrieve(SearchType.FULL_NAME, searchText);
        assertArrayEquals(countries, actual);
        verify(restOperations).getForEntity(FULL_NAME_URL, Country[].class, searchText);
        verify(multipleResponseEntity).getBody();
    }
}