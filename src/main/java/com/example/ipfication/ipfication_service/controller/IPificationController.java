package com.example.ipfication.ipfication_service.controller;

import com.example.ipfication.ipfication_service.dto.TokenResponseDTO;
import com.example.ipfication.ipfication_service.dto.UserResponse;
import com.example.ipfication.ipfication_service.service.IPificationService;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ipification")
public class IPificationController {

    private final IPificationService ipificationService;

    public IPificationController(IPificationService ipificationService) {
        this.ipificationService = ipificationService;
    }

    @PostMapping("/token")
    public Mono<ResponseEntity<TokenResponseDTO>> exchangeToken(@RequestBody String code) {
        return ipificationService.exchangeToken(code)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(new TokenResponseDTO("Error exchanging token: " + e.getMessage()))));
    }

    @GetMapping("/user_info")
    public Mono<ResponseEntity<UserResponse>> getUserInfo(@RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.replace("Bearer ", "");
        return ipificationService.getUserInfo(accessToken)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(new UserResponse("Error fetching user info: " + e.getMessage()))));
    }
}

