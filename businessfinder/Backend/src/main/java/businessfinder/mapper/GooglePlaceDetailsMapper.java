package businessfinder.mapper;

import businessfinder.model.Company;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@Component
public class GooglePlaceDetailsMapper {

    private final JsonMapper jsonMapper = new JsonMapper();

    public Company enrichCompany(String rawJson, Company company) {
        try {
            JsonNode root = jsonMapper.readTree(rawJson);
            JsonNode result = root.path("result");

            String phone = result.path("formatted_phone_number").asText(null);
            String website = result.path("website").asText(null);

            company.setPhone(phone);
            company.setWebsite(website);
            company.setHasPhone(phone != null && !phone.isBlank());
            company.setHasWebsite(website != null && !website.isBlank());

            JsonNode location = result.path("geometry").path("location");
            if (!location.isMissingNode()) {
                if (!location.path("lat").isMissingNode() && !location.path("lat").isNull()) {
                    company.setLatitude(location.path("lat").asDouble());
                }
                if (!location.path("lng").isMissingNode() && !location.path("lng").isNull()) {
                    company.setLongitude(location.path("lng").asDouble());
                }
            }

            return company;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping Google Place Details response", e);
        }
    }
}
