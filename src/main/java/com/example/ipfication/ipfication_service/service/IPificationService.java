package com.example.ipfication.ipfication_service.service;

import com.example.ipfication.ipfication_service.util.HttpUtil;
import com.example.ipfication.ipfication_service.dto.TokenResponseDTO;
import com.example.ipfication.ipfication_service.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IPificationService {

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

    public TokenResponseDTO exchangeToken(String code) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String body = "client_id=" + clientId +
                      "&client_secret=" + clientSecret +
                      "&redirect_uri=" + redirectUri +
                      "&grant_type=authorization_code" +
                      "&code=" + code;

        ResponseEntity<String> response = HttpUtil.sendPost(tokenUrl, headers, body);
        return objectMapper.readValue(response.getBody(), TokenResponseDTO.class);
    }

    public UserResponse getUserInfo(String accessToken) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        ResponseEntity<String> response = HttpUtil.sendGet(userInfoUrl, headers);
        return objectMapper.readValue(response.getBody(), UserResponse.class);
    }
}
