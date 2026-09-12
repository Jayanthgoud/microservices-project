package com.main.event;

import java.util.UUID;

public class OrderCreatedEvent {
    private UUID eventId;
    private Long orderId;
    @Override
    public String toString() {
        return "OrderCreatedEvent [eventId=" + eventId + ", orderId=" + orderId + ", userId=" + userId
                + ", totalAmount=" + totalAmount + "]";
    }

    private Long userId;
    private Double totalAmount;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(UUID eventId, Long orderId, Long userId, Double totalAmount) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
