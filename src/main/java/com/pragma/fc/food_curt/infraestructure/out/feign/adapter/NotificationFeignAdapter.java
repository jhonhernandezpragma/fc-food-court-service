package com.pragma.fc.food_curt.infraestructure.out.feign.adapter;

import com.pragma.fc.food_curt.domain.spi.INotificationClientPort;
import com.pragma.fc.food_curt.infraestructure.input.security.entity.JwtAuthenticationToken;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.INotificationClientFeign;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.request.SendSmsRequestBody;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

public class NotificationFeignAdapter implements INotificationClientPort {
    private final INotificationClientFeign notificationClientFeign;
    private final MessageSource messageSource;

    public NotificationFeignAdapter(INotificationClientFeign notificationClientFeign, MessageSource messageSource) {
        this.notificationClientFeign = notificationClientFeign;
        this.messageSource = messageSource;
    }

    @Override
    public void sendOrderReadyNotification(String phoneNumber, String orderId, String otp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            token = jwtAuthenticationToken.getToken();
        }

        String message = messageSource.getMessage(
                "otp.order-ready.message",
                new Object[]{orderId, otp},
                Locale.getDefault()
        );

        SendSmsRequestBody body = new SendSmsRequestBody();
        body.setPhoneNumber(phoneNumber);
        body.setMessage(message);
        notificationClientFeign.sendSms(body, "Bearer " + token);
    }
}
