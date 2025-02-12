package com.example.ipfication.ipfication_service.controller;

import com.example.ipfication.ipfication_service.dto.TokenResponseDTO;
import com.example.ipfication.ipfication_service.dto.UserResponse;
import com.example.ipfication.ipfication_service.service.IPificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ipification")
public class IPificationController {
    private final IPificationService ipificationService;

    public IPificationController(IPificationService ipificationService) {
        this.ipificationService = ipificationService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> exchangeToken(@RequestParam String code) {
        try {
            TokenResponseDTO tokenResponse = ipificationService.exchangeToken(code);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error exchanging token: " + e.getMessage());
        }
    }

    @GetMapping("/user_info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authorization) {
        try {
            String accessToken = authorization.replace("Bearer ", "");
            UserResponse userInfoResponse = ipificationService.getUserInfo(accessToken);
            return ResponseEntity.ok(userInfoResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching user info: " + e.getMessage());
        }
    }
}

