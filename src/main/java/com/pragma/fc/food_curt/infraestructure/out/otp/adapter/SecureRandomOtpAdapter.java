package com.pragma.fc.food_curt.infraestructure.out.otp.adapter;

import com.pragma.fc.food_curt.domain.spi.IOtpServicePort;

import java.security.SecureRandom;

public class SecureRandomOtpAdapter implements IOtpServicePort {
    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    @Override
    public String generateOtp() {
        int number = random.nextInt((int) Math.pow(10, OTP_LENGTH));
        return String.format("%0" + OTP_LENGTH + "d", number);
    }
}
