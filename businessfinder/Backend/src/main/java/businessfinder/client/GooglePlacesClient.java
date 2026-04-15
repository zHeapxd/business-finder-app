package businessfinder.client;

import businessfinder.config.GoogleApiConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GooglePlacesClient {

    private final GoogleApiConfig googleApiConfig;
    private final RestTemplate restTemplate;

    public GooglePlacesClient(GoogleApiConfig googleApiConfig) {
        this.googleApiConfig = googleApiConfig;
        this.restTemplate = new RestTemplate();
    }

    public String searchText(String query) {
        String url = UriComponentsBuilder
                .fromUriString(googleApiConfig.getPlacesBaseUrl() + "/textsearch/json")
                .queryParam("query", query)
                .queryParam("key", googleApiConfig.getApiKey())
                .build()
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

    public String getPlaceDetails(String placeId) {
        String url = UriComponentsBuilder
                .fromUriString(googleApiConfig.getPlacesBaseUrl() + "/details/json")
                .queryParam("place_id", placeId)
                .queryParam("fields", "place_id,name,formatted_address,formatted_phone_number,website,geometry")
                .queryParam("key", googleApiConfig.getApiKey())
                .build()
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}