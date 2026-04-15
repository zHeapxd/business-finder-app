package businessfinder.service;

import businessfinder.client.GooglePlacesClient;
import businessfinder.mapper.GooglePlaceDetailsMapper;
import businessfinder.mapper.GooglePlacesMapper;
import businessfinder.model.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GooglePlacesService {

    private final GooglePlacesClient googlePlacesClient;
    private final GooglePlacesMapper googlePlacesMapper;
    private final GooglePlaceDetailsMapper googlePlaceDetailsMapper;

    public GooglePlacesService(GooglePlacesClient googlePlacesClient,
                               GooglePlacesMapper googlePlacesMapper,
                               GooglePlaceDetailsMapper googlePlaceDetailsMapper) {
        this.googlePlacesClient = googlePlacesClient;
        this.googlePlacesMapper = googlePlacesMapper;
        this.googlePlaceDetailsMapper = googlePlaceDetailsMapper;
    }

    public List<Company> searchBusinesses(String query) {
        String rawJson = googlePlacesClient.searchText(query);
        List<Company> basicCompanies = googlePlacesMapper.mapToCompanies(rawJson);

        List<Company> enrichedCompanies = new ArrayList<>();

        for (Company company : basicCompanies) {
            if (company.getPlaceId() == null || company.getPlaceId().isBlank()) {
                enrichedCompanies.add(company);
                continue;
            }

            String detailsJson = googlePlacesClient.getPlaceDetails(company.getPlaceId());
            Company enriched = googlePlaceDetailsMapper.enrichCompany(detailsJson, company);
            enrichedCompanies.add(enriched);
        }

        return enrichedCompanies;
    }
}
