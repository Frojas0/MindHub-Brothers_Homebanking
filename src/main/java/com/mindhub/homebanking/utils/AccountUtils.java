package com.mindhub.homebanking.utils;

public final class AccountUtils {
//    @Autowired
//    private static AccountRepository accountRepository;
    public static String getAccountNumber() {
        String accountNumber;
        int randomNumber = (int) (Math.random() * 100000000);
        accountNumber = "VIN-" + String.format("%08d", randomNumber);
        return accountNumber;
    }
}
