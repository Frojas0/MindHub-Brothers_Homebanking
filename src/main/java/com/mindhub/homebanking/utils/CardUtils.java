package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {
    public static String getCardNumber() {
        Random randomNum = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int num = randomNum.nextInt(10000);
            sb.append(String.format("%04d", num));
            if (i < 3) {
                sb.append("-");
            }
        }
        return sb.toString();
    }
    public static int getCvv() {
        int num = (int) ((Math.random() * 899) + 100);
        return num;
    }
}
