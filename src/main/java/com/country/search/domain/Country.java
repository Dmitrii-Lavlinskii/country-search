package com.country.search.domain;

import lombok.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Data
public class Country {
    public Country() {
        languages = new ArrayList<>();
    }

    private String name;
    private String alpha2Code;
    private String alpha3Code;
    private URL flag;
    private String region;
    private String subregion;
    private int population;
    private List<String> languages;
}
