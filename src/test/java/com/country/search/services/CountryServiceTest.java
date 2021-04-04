package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.SearchType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
    private CountryService testSubject;

    @Mock private RestOperations restOperations;
    @Mock ResponseEntity<Country> singleResponseEntity;
    @Mock ResponseEntity<Country[]> multipleResponseEntity;

    private static final String CODE_URL = "I am country-by-code endpoint";
    private static final String COUNTRY_NAME_URL = "I am country-by-name endpoint";
    private static final String FULL_NAME_URL = "I am country-by-full-name endpoint";

    @BeforeEach
    public void setUp() {
        testSubject = new CountryService(restOperations, CODE_URL, FULL_NAME_URL, COUNTRY_NAME_URL);
    }

    @AfterEach
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
        Country[] countries = new Country[3];
        String searchText = "I am a country name";
        countries[0] = new Country();
        countries[1] = new Country();
        countries[2] = new Country();

        // set countries in different population order to test sorting.
        countries[0].setPopulation(5);
        countries[1].setPopulation(3);
        countries[2].setPopulation(8);

        when(restOperations.getForEntity(COUNTRY_NAME_URL, Country[].class, searchText)).thenReturn(multipleResponseEntity);
        when(multipleResponseEntity.getBody()).thenReturn(countries);

        var actual = testSubject.retrieve(SearchType.COUNTRY_NAME, searchText);
        assertArrayEquals(countries, actual);

        // assert expected order.
        assertEquals(8, actual[0].getPopulation());
        assertEquals(5, actual[1].getPopulation());
        assertEquals(3, actual[2].getPopulation());

        verify(restOperations).getForEntity(COUNTRY_NAME_URL, Country[].class, searchText);
        verify(multipleResponseEntity).getBody();
    }

    @Test
    public void retrieve_fullName() {
        Country[] countries = new Country[3];
        String searchText = "I am a full name";
        countries[0] = new Country();
        countries[1] = new Country();
        countries[2] = new Country();

        // set countries in different population order to test sorting.
        countries[0].setPopulation(9);
        countries[1].setPopulation(8);
        countries[2].setPopulation(11);

        when(restOperations.getForEntity(FULL_NAME_URL, Country[].class, searchText)).thenReturn(multipleResponseEntity);
        when(multipleResponseEntity.getBody()).thenReturn(countries);

        var actual = testSubject.retrieve(SearchType.FULL_NAME, searchText);
        assertArrayEquals(countries, actual);

        // assert expected order.
        assertEquals(11, actual[0].getPopulation());
        assertEquals(9, actual[1].getPopulation());
        assertEquals(8, actual[2].getPopulation());

        verify(restOperations).getForEntity(FULL_NAME_URL, Country[].class, searchText);
        verify(multipleResponseEntity).getBody();
    }
}