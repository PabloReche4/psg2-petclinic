package org.springframework.samples.petclinic.photo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CatApiService {

    private static final String BASE_URL = "https://api.thecatapi.com/v1/images/search";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${petclinic.catapi.apiKey:}")
    private String apiKey;

    public List<CatImageResponse> fetchSuggestions(int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 10));
        String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("limit", safeLimit)
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.hasText(apiKey)) {
            headers.add("x-api-key", apiKey);
        }

        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<CatImageResponse[]> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                CatImageResponse[].class
            );

            CatImageResponse[] body = response.getBody();
            if (body == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(body);
        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }
}
