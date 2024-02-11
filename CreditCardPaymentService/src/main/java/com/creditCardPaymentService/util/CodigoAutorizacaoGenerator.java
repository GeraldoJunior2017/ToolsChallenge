package com.creditCardPaymentService.util;

import java.util.Random;
import java.util.UUID;

public class CodigoAutorizacaoGenerator {
    public static String gerarCodigoAutorizacao() {
        // Gera um número aleatório de 6 dígitos para o código de autorização
        Random random = new Random();
        int codigoAutorizacaoInt = random.nextInt(900000) + 100000; // Garante um código de autorização de 6 dígitos
        return String.valueOf(codigoAutorizacaoInt);
    }
}

