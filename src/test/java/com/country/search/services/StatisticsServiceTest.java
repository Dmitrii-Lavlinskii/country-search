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
        country1.setRegion("region1");
        country1.setSubregion("subregion1");

        Country country2 = new Country();
        country2.setRegion("region1");
        country2.setSubregion("subregion2");

        Country country3 = new Country();
        country3.setSubregion("subregion1");

        Country country4 = new Country();
        country4.setRegion("region2");
        country4.setSubregion("subregion1");

        Country country5 = new Country();
        country5.setRegion("region3");

        Statistics actual = testSubject.calculateStatistics(
                new Country[]{country1, country2, country3, country4, country5});

        assertEquals(5, actual.getNumberOfCountries());

        assertEquals(3, actual.getRegions().keySet().size());
        assertEquals(2, actual.getSubregions().keySet().size());

        assertEquals(2, actual.getRegions().get("region1").intValue());
        assertEquals(1, actual.getRegions().get("region2").intValue());
        assertEquals(1, actual.getRegions().get("region3").intValue());

        assertEquals(3, actual.getSubregions().get("subregion1").intValue());
        assertEquals(1, actual.getSubregions().get("subregion2").intValue());
    }
}