package com.country.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchApplicationConfigurationTest {

    @Test
    public void restOperations() {
        SearchApplicationConfiguration testSubject = new SearchApplicationConfiguration();
        assertNotNull(testSubject.restOperations());
    }
}