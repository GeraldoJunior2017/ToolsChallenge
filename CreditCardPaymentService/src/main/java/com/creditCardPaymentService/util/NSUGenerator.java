package com.creditCardPaymentService.util;

import java.util.Random;
import java.util.UUID;

public class NSUGenerator {
    public static String gerarNsu() {
        // Gera um número aleatório de 6 dígitos para o NSU
        Random random = new Random();
        int nsuInt = random.nextInt(900000) + 100000; // Garante um NSU de 6 dígitos
        return String.valueOf(nsuInt);
    }
}