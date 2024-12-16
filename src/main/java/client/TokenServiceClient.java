package client;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getUsernameFromToken(String token) {
        String url = "http://localhost:8081/authentication-server/api/validate-token";

        // Préparer le corps de la requête
        String requestBody = "{\"token\":\"" + token + "\"}";

        // Effectuer l'appel HTTP POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);

        try {
            // Analyser la réponse JSON
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            boolean isValid = jsonResponse.get("isValid").asBoolean();

            if (isValid) {
                return jsonResponse.get("username").asText();
            } else {
                throw new IllegalArgumentException("Invalid token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response from TokenService", e);
        }
    }
}
