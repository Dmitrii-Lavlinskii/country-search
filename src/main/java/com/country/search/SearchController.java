package com.country.search;

import com.country.search.domain.SearchRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SearchController {

    @ModelAttribute(value = "searchRequest")
    public SearchRequest newRequest()
    {
        return new SearchRequest();
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
}
