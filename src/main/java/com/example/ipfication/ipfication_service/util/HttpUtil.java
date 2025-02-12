package com.example.ipfication.ipfication_service.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

public class HttpUtil {
    public static ResponseEntity<String> sendPost(String url, Map<String, String> headers, String body) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    public static ResponseEntity<String> sendGet(String url, Map<String, String> headers) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }
}
