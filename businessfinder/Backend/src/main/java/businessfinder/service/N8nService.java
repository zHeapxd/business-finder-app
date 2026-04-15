package businessfinder.service;

import businessfinder.model.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class N8nService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendToN8n(List<Company> companies) {
        String webhookUrl = "https://zheapxd.app.n8n.cloud/webhook-test/add-businesses";

        try {
            restTemplate.postForObject(webhookUrl, companies, String.class);
        } catch (Exception e) {
            System.out.println("Error sending data to n8n: " + e.getMessage());
        }
    }
}
