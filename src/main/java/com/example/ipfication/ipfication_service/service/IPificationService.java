package com.example.ipfication.ipfication_service.service;

import com.example.ipfication.ipfication_service.dto.TokenResponseDTO;
import com.example.ipfication.ipfication_service.dto.UserResponse;
import com.example.ipfication.ipfication_service.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class IPificationService {

    private static final Logger logger = LoggerFactory.getLogger(IPificationService.class);

    @Value("${ipification.client.id}")
    private String clientId;

    @Value("${ipification.client.secret}")
    private String clientSecret;

    @Value("${ipification.redirect.uri}")
    private String redirectUri;

    @Value("${ipification.token.uri}")
    private String tokenUrl;

    @Value("${ipification.user.info.uri}")
    private String userInfoUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<TokenResponseDTO> exchangeToken(String code) {
        if (clientId == null || clientSecret == null || redirectUri == null || tokenUrl == null) {
            return Mono.error(new IllegalArgumentException("IPification credentials or URLs are not properly configured"));
        }
        logger.info("Exchanging token for code: {}", code);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String body = "client_id=" + clientId +
                      "&client_secret=" + clientSecret +
                      "&redirect_uri=" + redirectUri +
                      "&grant_type=authorization_code" +
                      "&code=" + code;

        return HttpUtil.sendPost(tokenUrl, headers, body)
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        try {
                            return Mono.just(objectMapper.readValue(response.getBody(), TokenResponseDTO.class));
                        } catch (Exception e) {
                            logger.error("Error parsing token response", e);
                            return Mono.error(new Exception("Error parsing token response", e));
                        }
                    } else {
                        logger.error("Error exchanging token: {}", response.getBody());
                        return Mono.error(new Exception("Error exchanging token: " + response.getBody()));
                    }
                });
    }

    public Mono<UserResponse> getUserInfo(String accessToken) {
        if (userInfoUrl == null) {
            return Mono.error(new IllegalArgumentException("User info URL is not properly configured"));
        }
        logger.info("Fetching user info for access token: {}", accessToken);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        return HttpUtil.sendGet(userInfoUrl, headers)
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        try {
                            return Mono.just(objectMapper.readValue(response.getBody(), UserResponse.class));
                        } catch (Exception e) {
                            logger.error("Error parsing user info response", e);
                            return Mono.error(new Exception("Error parsing user info response", e));
                        }
                    } else {
                        logger.error("Error fetching user info: {}", response.getBody());
                        return Mono.error(new Exception("Error fetching user info: " + response.getBody()));
                    }
                });
    }
}