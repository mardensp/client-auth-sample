package com.example.clientauthsample.service;

import com.example.clientauthsample.dto.SampleDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Service
public class ClientService {

    private RestTemplateBuilder rtBuilder = new RestTemplateBuilder();

    public SampleDTO proxyToAuth() {

        String token = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        System.out.println(token);
        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = rtBuilder.build();
        ResponseEntity<SampleDTO> dto = restTemplate.exchange("http://localhost:8081/auth-sample/api/sample", HttpMethod.GET, request, SampleDTO.class);

        return dto.getBody();
    }

    private String getToken() {
        RestTemplate restTemplate = rtBuilder.build();
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic ".concat(Base64.getEncoder().encodeToString(new String("sample-app:fBeAqABXvlTvT1OxW84B0sL64wsfjHxv").getBytes(StandardCharsets.UTF_8))));

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, header);

        Token token = restTemplate.postForObject("https://keycloak-mardensp-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/realms/sample-realm/protocol/openid-connect/token", request, Token.class);

        return token.getAccessToken();
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Token {
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
