package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.security.JwtService;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OrderRequest request) {
        
        String token = authHeader.substring(7);
        Long userId = jwtService.extractClaim(token, claims -> claims.get("userId", Long.class));
        
        return ResponseEntity.ok(orderService.placeOrder(userId, request));
    }
}
