package com.mindhub.homebanking.utils;

import java.util.List;

public final class LoanUtils {
//    public static double getLoanInterest(Double initialAmount) {
//        double finalAmount = initialAmount*1.2;
//        return finalAmount;
//    }
    public static double getLoanInterest(List<Integer> lista, int valor) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) == valor) {
                return (i + 1) * 0.05;
            }
        }
        return 0.0;
    }
}
