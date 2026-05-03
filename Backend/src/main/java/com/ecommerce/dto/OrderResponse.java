package com.ecommerce.dto;

public class OrderResponse {
    private Long orderId;
    private String message;
    private java.math.BigDecimal totalAmount;
    public OrderResponse(Long orderId, String message, java.math.BigDecimal totalAmount) {
        this.orderId = orderId; this.message = message; this.totalAmount = totalAmount;
    }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public java.math.BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(java.math.BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
