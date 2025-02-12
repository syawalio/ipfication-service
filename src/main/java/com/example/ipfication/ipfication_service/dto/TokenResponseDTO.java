package com.example.ipfication.ipfication_service.dto;

public class TokenResponseDTO {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String idToken;

    public TokenResponseDTO(String accessToken, String tokenType, String expiresIn, String refreshToken, String idToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getIdToken() {
        return idToken;
    }
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}