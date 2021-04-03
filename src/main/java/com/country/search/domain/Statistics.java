package com.country.search.domain;

import lombok.Data;

import java.util.Map;

@Data
public class Statistics {
    private int numberOfCountries;
    private Map<String, Integer> regions;
    private Map<String, Integer> subregions;
}
