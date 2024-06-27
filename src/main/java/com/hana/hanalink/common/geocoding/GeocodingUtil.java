package com.hana.hanalink.common.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class GeocodingUtil {
    @Value("${google.geocoding.api-key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://maps.googleapis.com");

    // 주어진 위도, 경도로 Google Geocoding api 호출, 주소 반환
    public Mono<String> getAddress(double latitude, double longitude){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/maps/api/geocode/json")
                        .queryParam("latlng", latitude + "," + longitude)
                        .queryParam("key", apiKey)
                        .queryParam("language", "ko")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractAddressFromResponse);
    }

    // api 응답에서 주소 추출
    private String extractAddressFromResponse(String response){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode resultsNode = rootNode.path("results");
            if (resultsNode.isArray() && resultsNode.size() > 0) {
                JsonNode firstResult = resultsNode.get(0);
                JsonNode formattedAddressNode = firstResult.path("formatted_address");
                if (!formattedAddressNode.isMissingNode()) {
                    return formattedAddressNode.asText();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
