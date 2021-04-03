package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.Statistics;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsServiceTest {

    private StatisticsService testSubject;

    @Before
    public void setUp() {
        testSubject = new StatisticsService();
    }

    @Test
    public void calculateStatistics() {
        Country country1 = new Country();
        country1.setRegion("abc");
        country1.setSubregion("def");

        Country country2 = new Country();
        country2.setRegion("abc");
        country2.setSubregion("xyz");

        Country country3 = new Country();
        country3.setSubregion("def");

        Country country4 = new Country();
        country4.setRegion("def");
        country4.setSubregion("def");

        Country country5 = new Country();
        country5.setRegion("def");

        Statistics actual = testSubject.calculateStatistics(
                new Country[]{country1, country2, country3, country4, country5});

        assertEquals(5, actual.getNumberOfCountries());

        assertEquals(2, actual.getRegions().keySet().size());
        assertEquals(2, actual.getSubregions().keySet().size());

        assertEquals(2, actual.getRegions().get("abc").intValue());
        assertEquals(2, actual.getRegions().get("def").intValue());

        assertEquals(1, actual.getSubregions().get("xyz").intValue());
        assertEquals(3, actual.getSubregions().get("def").intValue());
    }
}