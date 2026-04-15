package businessfinder.controller;

import businessfinder.dto.request.SearchRequestDto;
import businessfinder.model.Company;
import businessfinder.service.GooglePlacesService;
import businessfinder.service.N8nService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final GooglePlacesService googlePlacesService;
    private final N8nService n8nService;

    public SearchController(GooglePlacesService googlePlacesService, N8nService n8nService) {
        this.googlePlacesService = googlePlacesService;
        this.n8nService = n8nService;
    }

    @PostMapping("/search")
    public List<Company> searchBusinesses(@RequestBody SearchRequestDto request) {

        List<Company> companies = googlePlacesService.searchBusinesses(request.getQuery());

        n8nService.sendToN8n(companies);

        return companies;
    }
}
