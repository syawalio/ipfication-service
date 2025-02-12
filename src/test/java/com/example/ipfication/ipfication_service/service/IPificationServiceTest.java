package com.example.ipfication.ipfication_service.service;

import com.example.ipfication.ipfication_service.dto.TokenResponseDTO;
import com.example.ipfication.ipfication_service.dto.UserResponse;
import com.example.ipfication.ipfication_service.util.HttpUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class IPificationServiceTest {

    @InjectMocks
    private IPificationService ipificationService;

    @Mock
    private HttpUtil httpUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExchangeToken() throws Exception {
        String code = "testCode";
        String tokenUrl = "https://api.ipification.com/oauth/realms/ipification/protocol/openid-connect/token";
        String responseBody = "{\"accessToken\":\"testAccessToken\",\"tokenType\":\"Bearer\",\"expiresIn\":\"3600\",\"refreshToken\":\"testRefreshToken\",\"idToken\":\"testIdToken\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        when(HttpUtil.sendPost(eq(tokenUrl), eq(headers), anyString())).thenReturn(ResponseEntity.ok(responseBody));

        TokenResponseDTO tokenResponse = ipificationService.exchangeToken(code);

        assertNotNull(tokenResponse);
        assertEquals("testAccessToken", tokenResponse.getAccessToken());
    }

    @Test
    void testGetUserInfo() throws Exception {
        String accessToken = "testAccessToken";
        String userInfoUrl = "https://api.ipification.com/oauth/realms/ipification/protocol/openid-connect/userinfo";
        String responseBody = "{\"sub\":\"testSub\",\"phoneNumberVerified\":true,\"phoneNumber\":\"1234567890\",\"loginHint\":\"testLoginHint\",\"mobileId\":\"testMobileId\"}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        when(HttpUtil.sendGet(eq(userInfoUrl), eq(headers))).thenReturn(ResponseEntity.ok(responseBody));

        UserResponse userResponse = ipificationService.getUserInfo(accessToken);

        assertNotNull(userResponse);
        assertEquals("testSub", userResponse.getSub());
    }
}