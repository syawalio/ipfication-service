package com.example.ipfication.ipfication_service.service;

import com.example.ipfication.ipfication_service.config.IPificationProperties;
import com.example.ipfication.ipfication_service.dto.TokenResponseDTO;
import com.example.ipfication.ipfication_service.dto.UserResponse;
import com.example.ipfication.ipfication_service.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class IPificationService {

    private static final Logger logger = LoggerFactory.getLogger(IPificationService.class);

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenUrl;
    private final String userInfoUrl;

    private final WebClient webClient = WebClient.builder()
            .defaultHeader("User-Agent", "IPificationService")
            .build();

    public IPificationService(IPificationProperties properties) {
        this.clientId = properties.getClientId();
        this.clientSecret = properties.getClientSecret();
        this.redirectUri = properties.getRedirectUri();
        this.tokenUrl = properties.getTokenUri();
        this.userInfoUrl = properties.getUserInfoUri();
    }

    public Mono<TokenResponseDTO> exchangeToken(String code) {
        if (clientId == null || clientSecret == null || redirectUri == null || tokenUrl == null) {
            return Mono.just(new TokenResponseDTO("IPification credentials or URLs are not properly configured"));
        }
        logger.info("Exchanging token for code: {}", code);

        return webClient.post()
                .uri(tokenUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("grant_type", "authorization_code")
                        .with("code", code))
                .retrieve()
                .bodyToMono(TokenResponseDTO.class)
                .doOnError(e -> logger.error("Error exchanging token: {}", e.getMessage(), e))
                .onErrorResume(e -> Mono.just(new TokenResponseDTO("Error exchanging token: " + e.getMessage())));
    }

    public Mono<UserResponse> getUserInfo(String accessToken) {
        if (userInfoUrl == null) {
            return Mono.error(new IllegalArgumentException("User info URL is not properly configured"));
        }
        logger.info("Fetching user info for access token: {}", accessToken);

        return webClient.get()
                .uri(userInfoUrl)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnError(e -> logger.error("Error fetching user info: {}", e.getMessage(), e));
    }
}