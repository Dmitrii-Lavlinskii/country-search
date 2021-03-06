package com.country.search.domain;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CountryTest {

    @Test
    public void getLanguageNames() {
        Country testSubject = new Country();
        List<Language> languages = new ArrayList<>();
        Language language = new Language();
        language.setName("abc");
        languages.add(language);

        language = new Language();
        language.setName("def");
        languages.add(language);

        testSubject.setLanguages(languages);
        assertEquals("abc, def", testSubject.getLanguageNames());
    }
}