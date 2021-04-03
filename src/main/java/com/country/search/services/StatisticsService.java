package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Service
public class StatisticsService {
    public Statistics calculateStatistics(Country[] countries) {
        Statistics statistics = new Statistics();
        statistics.setNumberOfCountries(countries.length);
        HashMap<String, Integer> regions = new HashMap<>();
        HashMap<String, Integer> subRegions = new HashMap<>();

        for (Country country : countries) {
            if (StringUtils.hasText(country.getRegion())) {
                Integer value = regions.getOrDefault(country.getRegion(), 0);
                regions.put(country.getRegion(), value + 1);
            }

            if (StringUtils.hasText(country.getSubregion())) {
                Integer value = subRegions.getOrDefault(country.getSubregion(), 0);
                subRegions.put(country.getSubregion(), value + 1);
            }
        }

        statistics.setRegions(regions);
        statistics.setSubregions(subRegions);

        return statistics;
    }
}
