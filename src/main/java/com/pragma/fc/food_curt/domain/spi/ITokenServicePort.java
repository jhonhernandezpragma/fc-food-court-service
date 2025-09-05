package com.pragma.fc.food_curt.domain.spi;

public interface ITokenServicePort {
    boolean isAccessTokenValid(String token);

    String extractSubject(String token);

    String extractRole(String token);
}
