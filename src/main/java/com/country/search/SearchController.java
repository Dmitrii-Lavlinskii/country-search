package com.country.search;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/")
    public String index(final SearchRequest request, Model model) {
        return "index";
    }

    @ModelAttribute(value = "searchRequest")
    public SearchRequest newRequest()
    {
        return new SearchRequest();
    }

    @ModelAttribute(value = "searchResponse")
    public SearchResponse newResponse()
    {
        return new SearchResponse();
    }
}
