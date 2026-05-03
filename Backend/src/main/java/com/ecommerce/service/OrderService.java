package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.DressVariant;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.DressVariantRepository;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DressVariantRepository variantRepository;

    @Transactional
    public OrderResponse placeOrder(Long userId, OrderRequest request) {
        
        // 1. Fetch variant with a Pessimistic Write Lock
        DressVariant variant = variantRepository.findByIdForUpdate(request.getVariantId())
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));

        // 2. Validate Stock
        if (variant.getStockQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Sorry, this specific size/color is out of stock!");
        }

        // 3. Deduct Stock
        variant.setStockQuantity(variant.getStockQuantity() - request.getQuantity());
        variantRepository.save(variant);

        // 4. Calculate Price and Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setDressVariantId(variant.getId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(variant.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus("PENDING");

        orderRepository.save(order);

        return new OrderResponse(order.getId(), "Order placed successfully!", order.getTotalPrice());
    }
}
