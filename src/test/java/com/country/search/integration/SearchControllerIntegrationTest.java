package com.country.search.integration;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.country.search.services.SearchService.BAD_REQUEST_MESSAGE;
import static com.country.search.services.SearchService.NOT_FOUND_MESSAGE;
import static com.country.search.services.ValidationService.EMPTY_SEARCH;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Test
    public void home() throws Exception {
        // sanity check: assert that the page displays the h2.
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Country Search")));
    }

    @Test
    public void search_empty() throws Exception {
        // Submit empty string and see if validation works.
        mockMvc.perform(postSearch(new SearchRequest())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(EMPTY_SEARCH)));
    }

    @Test
    public void search_badRequest() throws Exception {
        // Submit long code.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.CODE);
        request.setValue("very long code");

        mockMvc.perform(postSearch(request)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(BAD_REQUEST_MESSAGE)));
    }

    @Test
    public void search_byCode() throws Exception {
        // Search by code RUS. Should be Russian Federation but not Belarus.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.CODE);
        request.setValue("RUS");

        mockMvc.perform(postSearch(request)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Russian Federation")))
                .andExpect(content().string(CoreMatchers.not(containsString("Belarus"))));
    }

    @Test
    public void search_byName() throws Exception {
        // Search by name RUS. Should be Russian Federation AND Belarus.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.COUNTRY_NAME);
        request.setValue("RUS");

        mockMvc.perform(postSearch(request)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Russian Federation")))
                .andExpect(content().string(containsString("Belarus")));
    }

    @Test
    public void search_byFullName_notFound() throws Exception {
        // Search by full name RUS. Should be not found.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.FULL_NAME);
        request.setValue("RUS");

        mockMvc.perform(postSearch(request)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(NOT_FOUND_MESSAGE)));
    }

    @Test
    public void search_byFullName() throws Exception {
        // Search by full name Russian Federation. Should be found.
        SearchRequest request = new SearchRequest();
        request.setType(SearchType.FULL_NAME);
        request.setValue("Russian Federation");

        mockMvc.perform(postSearch(request)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Russian Federation")))
                .andExpect(content().string(CoreMatchers.not(containsString(NOT_FOUND_MESSAGE))));
    }

    private MockHttpServletRequestBuilder postSearch(SearchRequest request) {
        return post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", request.getType().toString())
                .param("value", request.getValue());
    }
}
