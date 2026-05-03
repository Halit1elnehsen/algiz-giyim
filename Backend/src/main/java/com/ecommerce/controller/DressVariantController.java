package com.ecommerce.controller;

import com.ecommerce.entity.DressVariant;
import com.ecommerce.repository.DressVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dress/variants")
@RequiredArgsConstructor
public class DressVariantController {

    private final DressVariantRepository repository;

    @GetMapping
    public ResponseEntity<List<DressVariant>> getVariants() {
        return ResponseEntity.ok(repository.findAll());
    }
}
