package businessfinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleApiConfig {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.places.base-url}")
    private String placesBaseUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getPlacesBaseUrl() {
        return placesBaseUrl;
    }
}
