package com.sicredi.toolschallenge.utils;

import java.security.SecureRandom;

public class CodigoGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String gerarNSU() {
        return gerarCodigoNumerico(10);
    }

    public static String gerarCodigoAutorizacao() {
        return gerarCodigoNumerico(9);
    }

    private static String gerarCodigoNumerico(int tamanho) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            sb.append(secureRandom.nextInt(10));
        }

        return sb.toString();
    }
}
