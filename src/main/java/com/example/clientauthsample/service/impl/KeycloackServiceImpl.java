package com.example.clientauthsample.service.impl;

import com.example.clientauthsample.service.KeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service("keycloak-auth-sample")
public class KeycloackServiceImpl implements KeycloakService {

    @Value("${services.keycloak.token-uri}")
    private String tokenUri;
    @Value("${services.keycloak.client-id}")
    private String clientId;
    @Value("${services.keycloak.client-secret}")
    private String clientSecret;

    @Override
    public Token getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", getAuthorization());

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, header);

        Token token = restTemplate.postForObject(tokenUri, request, Token.class);

        return token;
    }

    private String getAuthorization() {
        return "Basic ".concat(Base64.getEncoder().encodeToString(clientId.concat(":").concat(clientSecret).getBytes(StandardCharsets.UTF_8)));
    }


}
