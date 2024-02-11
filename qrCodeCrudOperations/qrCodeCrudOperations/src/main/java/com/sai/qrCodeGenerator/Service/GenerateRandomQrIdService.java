package com.sai.qrCodeGenerator.Service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class GenerateRandomQrIdService {

    private static final int QR_ID_LENGTH = 6;

    private final Set<String> usedQrIds = new HashSet<>();
    private final Random random = new Random();

    public synchronized String generateRandomQrId() {
        StringBuilder qrIdBuilder = new StringBuilder();

        do {
            for (int i = 0; i < QR_ID_LENGTH; i++) {
                int digit = random.nextInt(10);
                qrIdBuilder.append(digit);
            }
        } while (usedQrIds.contains(qrIdBuilder.toString()));

        String qrId = qrIdBuilder.toString();
        usedQrIds.add(qrId);
        return qrId;
    }
}
