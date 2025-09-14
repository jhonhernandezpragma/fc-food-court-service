package com.pragma.fc.food_curt.domain.spi;

public interface INotificationClientPort {
    void sendOrderReadyNotification(String phoneNumber, String orderId, String otp);
}
