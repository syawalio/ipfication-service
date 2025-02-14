package com.example.ipfication.ipfication_service.util;

// import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class HttpUtil {
    private static final WebClient webClient = WebClient.builder().build();

    public static Mono<ResponseEntity<String>> sendPost(String url, Map<String, String> headers, String body) {
        return webClient.post()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }

    public static Mono<ResponseEntity<String>> sendGet(String url, Map<String, String> headers) {
        return webClient.get()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .retrieve()
                .toEntity(String.class);
    }
}
