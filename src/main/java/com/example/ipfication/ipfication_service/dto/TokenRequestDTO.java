package com.example.ipfication.ipfication_service.dto;

public class TokenRequestDTO {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
    private String grantType;

    public TokenRequestDTO(String clientId, String clientSecret, String code, String redirectUri, String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirectUri = redirectUri;
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getRedirectUri() {
        return redirectUri;
    }
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
    public String getGrantType() {
        return grantType;
    }
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}