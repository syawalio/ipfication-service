package com.example.ipfication.ipfication_service.dto;

public class TokenResponseDTO {
    private String accessToken;
    private String tokenType;
    private String expiresIn;

    public TokenResponseDTO(String accessToken, String tokenType, String expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
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
}
