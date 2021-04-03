package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.Statistics;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    public Statistics calculateStatistics(Country[] countries) {
        return new Statistics(); // TODO.
    }
}
