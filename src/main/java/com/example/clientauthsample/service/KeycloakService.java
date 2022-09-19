package com.example.clientauthsample.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface KeycloakService {

    Token getToken();

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @NoArgsConstructor
    public static class Token {
        @Getter
        @Setter
        private String accessToken;

        public String getBearerToken() {
            return "Bearer ".concat(accessToken);
        }
    }

}
