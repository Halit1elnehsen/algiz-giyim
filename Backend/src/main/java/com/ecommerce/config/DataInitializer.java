package com.ecommerce.config;

import com.ecommerce.entity.DressVariant;
import com.ecommerce.repository.DressVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DressVariantRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        BigDecimal price = new BigDecimal("129.99");

        // Reliable, color-accurate dress images from Unsplash
        String imgBlack = "https://images.unsplash.com/photo-1568252542512-9fe8fe9c87bb?w=800&h=1200&fit=crop";
        String imgRed   = "https://images.unsplash.com/photo-1518622358385-8ea7d0794bf6?w=800&h=1200&fit=crop";
        String imgWhite = "https://images.unsplash.com/photo-1594552072238-b8a33785b261?w=800&h=1200&fit=crop";

        List<DressVariant> variants = List.of(
            // Black
            new DressVariant("Black", "S", 10, price, imgBlack),
            new DressVariant("Black", "M", 15, price, imgBlack),
            new DressVariant("Black", "L", 5, price, imgBlack),
            new DressVariant("Black", "XL", 0, price, imgBlack),
            
            // Red
            new DressVariant("Red", "S", 5, price, imgRed),
            new DressVariant("Red", "M", 8, price, imgRed),
            new DressVariant("Red", "L", 2, price, imgRed),
            
            // White
            new DressVariant("White", "S", 20, price, imgWhite),
            new DressVariant("White", "M", 25, price, imgWhite),
            new DressVariant("White", "L", 10, price, imgWhite)
        );

        repository.saveAll(variants);
        System.out.println("✅ Data initialized with dress variants!");
    }
}
