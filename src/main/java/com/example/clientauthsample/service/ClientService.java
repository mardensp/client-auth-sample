package com.example.clientauthsample.service;

import com.example.clientauthsample.dto.SampleDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {

    @Value("${services.auth-sample.api-uri}")
    private String authSampleApiUri;

    @Qualifier("keycloak-auth-sample")
    private final KeycloakService keycloakService;

    public ClientService(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    public SampleDTO proxyToAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", keycloakService.getToken().getBearerToken());

        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleDTO> dto = restTemplate.exchange(authSampleApiUri, HttpMethod.GET, request, SampleDTO.class);

        return dto.getBody();
    }

}
