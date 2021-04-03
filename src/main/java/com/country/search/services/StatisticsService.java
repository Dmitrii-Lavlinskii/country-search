package com.country.search.services;

import com.country.search.domain.Country;
import com.country.search.domain.Statistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    public Statistics calculateStatistics(List<Country> countries) {
        return new Statistics(); // TODO.
    }
}
