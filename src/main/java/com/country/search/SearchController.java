package com.country.search;

import com.country.search.domain.SearchRequest;
import com.country.search.domain.SearchResponse;
import com.country.search.services.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController {
    public static final String HOME_PAGE = "index";
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return HOME_PAGE;
    }

    @PostMapping("/")
    public String search(final SearchRequest request, Model model) {
        SearchResponse response = searchService.search(request);
        model.addAttribute(response);
        return HOME_PAGE;
    }

    @ModelAttribute(value = "searchRequest")
    public SearchRequest defaultRequest()
    {
        return new SearchRequest();
    }

    @ModelAttribute(value = "searchResponse")
    public SearchResponse defaultResponse()
    {
        SearchResponse response = new SearchResponse();
        response.setSuccess(true);

        return response;
    }
}
