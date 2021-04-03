package com.country.search;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchApplicationConfigurationTest {

    @Test
    public void restOperations() {
        SearchApplicationConfiguration testSubject = new SearchApplicationConfiguration();
        assertNotNull(testSubject.restOperations());
    }
}