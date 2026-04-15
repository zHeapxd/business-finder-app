package businessfinder.mapper;

import businessfinder.model.Company;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class GooglePlacesMapper {

    private final JsonMapper jsonMapper = new JsonMapper();

    public List<Company> mapToCompanies(String rawJson) {
        List<Company> companies = new ArrayList<>();

        try {
            JsonNode root = jsonMapper.readTree(rawJson);
            JsonNode results = root.path("results");

            for (JsonNode item : results) {
                Company company = new Company();

                company.setName(item.path("name").asText(null));
                company.setAddress(item.path("formatted_address").asText(null));

                if (!item.path("rating").isMissingNode() && !item.path("rating").isNull()) {
                    company.setRating(item.path("rating").asDouble());
                }

                if (!item.path("user_ratings_total").isMissingNode() && !item.path("user_ratings_total").isNull()) {
                    company.setReviews(item.path("user_ratings_total").asInt());
                }

                String placeId = item.path("place_id").asText(null);
                company.setPlaceId(placeId);

                if (placeId != null && !placeId.isBlank()) {
                    company.setMapsUrl("https://www.google.com/maps/place/?q=place_id:" + placeId);
                } else {
                    company.setMapsUrl(null);
                }

                JsonNode location = item.path("geometry").path("location");
                if (!location.isMissingNode()) {
                    if (!location.path("lat").isMissingNode() && !location.path("lat").isNull()) {
                        company.setLatitude(location.path("lat").asDouble());
                    }
                    if (!location.path("lng").isMissingNode() && !location.path("lng").isNull()) {
                        company.setLongitude(location.path("lng").asDouble());
                    }
                }

                companies.add(company);
            }

            return companies;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping Google Places response", e);
        }
    }
}
