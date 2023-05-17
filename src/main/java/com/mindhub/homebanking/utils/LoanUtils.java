package com.mindhub.homebanking.utils;

public final class LoanUtils {
    public static double getLoanInterest(Double initialAmount) {
        double finalAmount = initialAmount*1.2;
        return finalAmount;
    }
}
